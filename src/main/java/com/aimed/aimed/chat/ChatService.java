package com.aimed.aimed.chat;

import com.aimed.aimed.chat.dto.ChatDto;
import com.aimed.aimed.chat.dto.MessageResponseDto;
import com.aimed.aimed.chat.entity.Chat;
import com.aimed.aimed.contact.ContactService;
import com.aimed.aimed.contact.ContactsDto;
import com.aimed.aimed.message.dto.*;
import com.aimed.aimed.message.entity.*;
import com.aimed.aimed.message.enums.MessageType;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ChatPromptManager chatPromptManager;
    private final DoctorProfileRepository doctorProfileRepository;

    @Transactional
    public MessageResponseDto processMessage(Long chatId, String content) throws JsonProcessingException {
        Chat chat = this.chatRepository.findById(chatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such chat"));

        Message userMessage = new Message(chat, MessageType.USER);
        userMessage.setUserPayload(new UserMessagePayload(userMessage, content));
        Message savedUserMessage = this.messageRepository.save(userMessage);

        Pageable pageable = PageRequest.of(0, 8, Sort.by("createdAt").descending());
        List<Message> messages = this.messageRepository.findByChatIdAndTypeIn(
                        chatId,
                        List.of(MessageType.USER, MessageType.ASSISTANT),
                        pageable
                )
                .reversed();

        AssistantMessageDto dto = this.chatPromptManager
                .getAssistantResponse(chat.getContext(), messages);

        Message assistantMessage = new Message(chat, MessageType.ASSISTANT);
        assistantMessage.setAssistantPayload(new AssistantMessagePayload(
                assistantMessage,
                dto.possibleCauses(),
                dto.recommendations(),
                dto.urgency(),
                dto.doctors()
        ));
        Message savedAssistantMessage = this.messageRepository.save(assistantMessage);
        messages.add(assistantMessage);

        String newContext = chatPromptManager.updateContext(chat.getContext(), messages);
        chatRepository.updateContextById(chatId, newContext);

        return new MessageResponseDto(savedUserMessage.toDto(), savedAssistantMessage.toDto());
    }

    @Transactional
    public ChatDto create(Long userId, String title) {
        Chat chat = this.chatRepository.save(new Chat(userId, title));
        return new ChatDto(chat.getId(), chat.getTitle());
    }

    public List<MessageDto> getMessages(Long chatId) {
        this.chatRepository.findById(chatId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found")
        );
        Pageable pageable = PageRequest.of(0, 100, Sort.by("createdAt").ascending());

        return this.messageRepository.findByChatId(chatId, pageable).stream()
                .map(Message::toDto)
                .toList();
    }

    public List<ChatDto> getChats(Long userId) {
        return this.chatRepository.findAllByUserIdOrderByLastMessageAtDesc(userId, ChatDto.class);
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
        Chat chat = this.chatRepository.findById(chatId)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found"));

        List<Long> specializationIds = this.chatPromptManager.analyzeContext(chat.getContext());
        List<DoctorProfile> doctors = doctorProfileRepository.findAllBySpecializationIds(specializationIds);

        List<DoctorSuggestionDto> sortedDoctors = doctors.stream()
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
                .map(d -> new DoctorSuggestionDto(
                        d.getUserId(),
                        d.getUser().getFullName(),
                        d.getSpecializations().stream().map(Specialization::getName).toList(),
                        d.getAddress(),
                        d.getEducation(),
                        d.getDescription(),
                        d.getPracticeStartDate()
                ))
                .toList();

        Message message = new Message(chat, MessageType.DOCTOR_SUGGESTIONS);
        message.setDoctorSuggestionsPayload(new DoctorSuggestionsMessagePayload(message, sortedDoctors));

        return messageRepository.save(message).toDto();
    }
}
