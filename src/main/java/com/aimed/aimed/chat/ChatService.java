package com.aimed.aimed.chat;

import com.aimed.aimed.chat.dto.ChatDto;
import com.aimed.aimed.chat.dto.MessagePageDto;
import com.aimed.aimed.chat.dto.MessageResponseDto;
import com.aimed.aimed.chat.entity.Chat;
import com.aimed.aimed.contact.ContactService;
import com.aimed.aimed.contact.ContactsDto;
import com.aimed.aimed.message.MessageService;
import com.aimed.aimed.message.dto.*;
import com.aimed.aimed.message.entity.*;
import com.aimed.aimed.message.enums.MessageType;
import com.aimed.aimed.message.mapper.MessageMapper;
import com.aimed.aimed.specialization.Specialization;
import com.aimed.aimed.message.MessageRepository;
import com.aimed.aimed.specialization.SpecializationRepository;
import com.aimed.aimed.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.aimed.aimed.chat_prompt_manager.ChatPromptManager;
import com.aimed.aimed.user.entity.DoctorProfile;
import com.aimed.aimed.user.repository.DoctorProfileRepository;
import com.aimed.aimed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ChatPromptManager chatPromptManager;
    private final DoctorProfileRepository doctorProfileRepository;

    private final MessageMapper messageMapper;

    private final MessageService messageService;

    public Chat getChat(Long chatId) {
        return this.chatRepository.findById(chatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such chat"));
    }

    @Transactional
    public MessageResponseDto processMessage(Long chatId, String content) throws JsonProcessingException {
        Chat chat = getChat(chatId);

        Message userMessage = this.messageService.saveUserMessage(chat, content);
        List<Message> messages = this.messageService.getNLastMessages(chatId, 8);

        Message assistantMessage = this.messageService.saveAssistantMessage(
                chat,
                this.chatPromptManager.getAssistantResponse(chat.getContext(), messages)
        );
        messages.add(assistantMessage);

        String newContext = chatPromptManager.updateContext(chat.getContext(), messages);
        chatRepository.updateContextById(chatId, newContext);

        return new MessageResponseDto(
                messageMapper.toDto(userMessage),
                messageMapper.toDto(assistantMessage)
        );
    }

    @Transactional
    public ChatDto create(Long userId, String title) {
        Chat chat = this.chatRepository.save(new Chat(userId, title));
        return new ChatDto(chat.getId(), chat.getTitle(), chat.getLastMessageAt());
    }

    public MessagePageDto getMessages(Long chatId, OffsetDateTime before, Integer limit) {
        getChat(chatId);
        Slice<Message> messages = this.messageService.getNMessagesBefore(chatId, before, limit);

        return new MessagePageDto(
                messages.stream()
                        .map(messageMapper::toDto)
                        .toList()
                        .reversed(),
                messages.hasNext()
        );
    }

    public List<ChatDto> getChats(Long userId) {
        return this.chatRepository.findAllByUserIdOrderByLastMessageAtDesc(userId, ChatDto.class)
                .stream().sorted(Comparator.comparing(
                        ChatDto::lastMessageAt,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .toList();
    }

    @Transactional
    public void deleteChat(Long chatId) {
        this.chatRepository.findById(chatId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found")
        );
        this.messageRepository.deleteByChatId(chatId);
        this.chatRepository.deleteById(chatId);
    }

    @Transactional
    public MessageDto findDoctors(Long chatId) throws JsonProcessingException {
        Chat chat = getChat(chatId);

        List<Long> specializationIds = this.chatPromptManager.analyzeContext(chat.getContext());
        List<DoctorProfile> doctors = doctorProfileRepository.findAllBySpecializationIds(specializationIds);

        List<User> sortedDoctors = doctors.stream()
                .filter(d -> d.getLicense() != null && !d.getLicenseExpiryDate().isBefore(LocalDate.now()))
                .sorted(Comparator
                        .comparing((DoctorProfile d) ->
                                d.getEducation() == null
                                        || d.getDescription() == null
                                        || d.getEducation().isBlank()
                                        || d.getDescription().isBlank())
                        .thenComparing(DoctorProfile::getPracticeStartDate)
                )
                .limit(5)
                .map(DoctorProfile::getUser)
                .toList();

        return messageMapper.toDto(messageService.saveDoctorSuggestionsMessage(chat, sortedDoctors));
    }
}
