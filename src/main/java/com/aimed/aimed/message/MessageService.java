package com.aimed.aimed.message;

import com.aimed.aimed.chat.entity.Chat;
import com.aimed.aimed.invitation.entity.Invitation;
import com.aimed.aimed.message.dto.AssistantMessageDto;
import com.aimed.aimed.message.entity.Message;
import com.aimed.aimed.message.entity.UserMessagePayload;
import com.aimed.aimed.message.enums.MessageType;
import com.aimed.aimed.message.mapper.MessageMapper;
import com.aimed.aimed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    public Message saveUserMessage(Chat chat, String content) {
        Message message = new Message(chat, MessageType.USER);
        message.setUserPayload(new UserMessagePayload(message, content));
        return this.messageRepository.save(message);
    }

    public Message saveAssistantMessage(Chat chat, AssistantMessageDto payloadDto) {
        Message message = new Message(chat, MessageType.ASSISTANT);
        message.setAssistantPayload(messageMapper.toPayload(payloadDto, message));
        return this.messageRepository.save(message);
    }

    public Message saveDoctorSuggestionsMessage(Chat chat, List<User> doctors) {
        Message message = new Message(chat, MessageType.DOCTOR_SUGGESTIONS);
        message.setDoctorSuggestionsPayload(doctors);
        return this.messageRepository.save(message);
    }

    public Message saveInvitationMessage(Chat chat, Invitation invitation) {
        Message message = new Message(chat, MessageType.INVITATION);
        message.setInvitationPayload(invitation);
        return this.messageRepository.save(message);
    }

    public List<Message> getNLastMessages(Long chatId, Integer messageNumber) {
        Pageable pageable = PageRequest.of(0, messageNumber, Sort.by("createdAt").descending());
        return this.messageRepository
                .findByChatIdAndTypeIn(
                        chatId,
                        List.of(MessageType.USER, MessageType.ASSISTANT),
                        pageable
                )
                .reversed();
    }

    public Slice<Message> getNMessagesBefore(Long chatId, OffsetDateTime before, Integer limit) {
        Pageable pageable = PageRequest.of(0, limit);
        Slice<Message> messages;
        if (before == null) {
            messages = this.messageRepository.findByChatIdOrderByCreatedAtDesc(chatId, pageable);
        } else {
            messages = this.messageRepository.findByChatIdBefore(chatId, before, pageable);
        }
        return messages;
    }
}
