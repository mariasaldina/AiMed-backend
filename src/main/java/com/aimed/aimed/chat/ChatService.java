package com.aimed.aimed.chat;

import com.aimed.aimed.chat.dto.ChatDto;
import com.aimed.aimed.chat.dto.MessagePageDto;
import com.aimed.aimed.chat.dto.MessageResponseDto;
import com.aimed.aimed.chat.entity.Chat;
import com.aimed.aimed.chat.mapper.ChatMapper;
import com.aimed.aimed.doctorsearch.DoctorSearchService;
import com.aimed.aimed.message.MessageService;
import com.aimed.aimed.message.dto.*;
import com.aimed.aimed.message.entity.*;
import com.aimed.aimed.message.enums.MessageType;
import com.aimed.aimed.message.mapper.MessageMapper;
import com.aimed.aimed.message.MessageRepository;
import com.aimed.aimed.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ChatAiService chatAiService;

    private final MessageMapper messageMapper;
    private final ChatMapper chatMapper;

    private final MessageService messageService;
    private final DoctorSearchService doctorSearchService;

    public Chat getChat(Long chatId) {
        return this.chatRepository.findById(chatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such chat"));
    }

    @Transactional
    public MessageResponseDto processMessage(Long chatId, String content) throws JsonProcessingException {
        Chat chat = getChat(chatId);

        Message userMessage = this.messageService.saveUserMessage(chat, content);
        List<Message> messages = this.messageService.getNLastMessages(chatId, 10);

        Message assistantMessage = this.messageService.saveAssistantMessage(
                chat,
                this.chatAiService.getAssistantResponse(chat.getContext(), messages)
        );
        messages.add(assistantMessage);

        String newContext = chatAiService.updateContext(
                chat.getContext(),
                messages.stream()
                        .filter(m -> m.getType() == MessageType.USER)
                        .map(m -> m.getUserPayload().getContent())
                        .toList()
        );
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
        return this.chatRepository.findAllByUserId(userId)
                .stream()
                .map(chatMapper::toDto)
                .toList();
    }

    @Transactional
    public void deleteChat(Long chatId) {
        getChat(chatId);
        this.chatRepository.detachInvitations(chatId);
        this.messageRepository.deleteByChatId(chatId);
        this.chatRepository.deleteById(chatId);
    }

    @Transactional
    public void renameChat(Long chatId, String title) {
        Chat chat = getChat(chatId);
        chat.setTitle(title);
    }

    @Transactional
    public MessageDto findDoctors(Long chatId) throws JsonProcessingException {
        Chat chat = getChat(chatId);
        List<User> doctors = doctorSearchService.recommendDoctors(chat);
        return messageMapper.toDto(messageService.saveDoctorSuggestionsMessage(chat, doctors));
    }
}
