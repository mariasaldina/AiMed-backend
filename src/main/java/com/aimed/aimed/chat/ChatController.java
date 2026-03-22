package com.aimed.aimed.chat;

import com.aimed.aimed.chat.dto.ChatDto;
import com.aimed.aimed.chat.dto.MessageResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.aimed.aimed.message.entity.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    public record CreateChatDto (
            String title
    ) {}

    @PostMapping("")
    public ChatDto create(
            @RequestBody CreateChatDto reqBody,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = Long.valueOf(jwt.getSubject());
        return this.chatService.create(userId, reqBody.title());
    }

    @GetMapping("")
    public List<ChatDto> getChats(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        return this.chatService.getChats(userId);
    }

    public record UserMessageDto(
            String content
    ) {}

    @PostMapping("/{chatId}")
    public ResponseEntity<?> processMessage(
            @PathVariable Long chatId,
            @RequestBody() UserMessageDto reqBody
    ) {
        try {
            MessageResponseDto res = this.chatService.processMessage(
                    chatId,
                    reqBody.content()
            );
            return ResponseEntity.ok(res);
        } catch (JsonProcessingException e) {
            ProblemDetail err = ProblemDetail.forStatus(HttpStatus.SERVICE_UNAVAILABLE);
            err.setTitle("Ollama service couldn't produce an answer");
            err.setDetail(e.getMessage());
            return ResponseEntity.status(err.getStatus()).body(err);
        }
    }

    public record ChatMessagesDto (
            List<Message> messages
    ) {}

    @GetMapping("/{chatId}")
    public ChatMessagesDto getMessages(@PathVariable Long chatId) {
        return new ChatMessagesDto(this.chatService.getMessages(chatId));
    }

    @DeleteMapping("/{chatId}")
    public void deleteChat(@PathVariable Long chatId) {
        this.chatService.deleteChat(chatId);
    }

    @PostMapping("/{chatId}/doctors")
    public ResponseEntity<?> findDoctors(@PathVariable Long chatId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.chatService.findDoctors(chatId));
        } catch (JsonProcessingException e) {
            ProblemDetail err = ProblemDetail.forStatus(HttpStatus.SERVICE_UNAVAILABLE);
            err.setTitle("The service is temporarily unavailable");
            err.setDetail(e.getMessage());
            return ResponseEntity.status(err.getStatus()).body(err);
        }
    }

    @PostMapping("/{chatId}/doctors/{doctorId}/contacts")
    public Message getDoctorsContacts(
            @PathVariable Long chatId,
            @PathVariable Long doctorId
    ) {
        return this.chatService.getDoctorsContacts(chatId, doctorId);
    }

}
