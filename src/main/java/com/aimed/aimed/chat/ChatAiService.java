package com.aimed.aimed.chat;

import com.aimed.aimed.ollama.enums.Prompt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.aimed.aimed.ollama.OllamaService;
import com.aimed.aimed.ollama.dto.OllamaChatMessage;
import com.aimed.aimed.ollama.enums.OllamaChatRole;
import com.aimed.aimed.message.dto.AssistantMessageDto;
import com.aimed.aimed.message.entity.AssistantMessagePayload;
import com.aimed.aimed.message.entity.Message;
import com.aimed.aimed.message.enums.MessageType;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.core.JacksonException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatAiService {
    private final OllamaService ollamaService;

    private String formAssistantMessage(AssistantMessagePayload payload) {
        return "Возможные причины:\n"
                + payload.getPossibleCauses()
                + "\nРекомендации:\n"
                + payload.getRecommendations()
                + "\nУровень риска:\n"
                + payload.getUrgency()
                + "\nПодходящие специалисты:\n"
                + payload.getDoctors();
    }

    @Retry(name = "symptomAnalyzerRetry")
    public AssistantMessageDto getAssistantResponse(
            String context,
            List<Message> messages) throws JsonProcessingException
    {
        List<OllamaChatMessage> reqMessages = new ArrayList<>(messages.stream()
                .map(m -> new OllamaChatMessage(
                        m.getType() == MessageType.ASSISTANT
                                ? OllamaChatRole.assistant
                                : OllamaChatRole.user,
                        m.getType() == MessageType.ASSISTANT
                                ? formAssistantMessage(m.getAssistantPayload())
                                : m.getUserPayload().getContent()
                        )
                )
                .toList()
        );

        OllamaChatMessage contextMessage = new OllamaChatMessage(
                OllamaChatRole.system,
                ollamaService.getPrompt(Prompt.USE_CONTEXT) + context
        );
        reqMessages.addFirst(contextMessage);

        OllamaChatMessage promptMessage = new OllamaChatMessage(
                OllamaChatRole.system,
                ollamaService.getPrompt(Prompt.ASSISTANT_RESPONSE)
        );
        reqMessages.addLast(promptMessage);

        OllamaChatMessage rawResponse = ollamaService.generateChatMessage(reqMessages);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return mapper.readValue(rawResponse.getContent(), AssistantMessageDto.class);
    }

    public String updateContext(
            String oldContext,
            List<String> messages
    ) {
        ObjectMapper mapper = new ObjectMapper();
        String messagesJson;
        try {
            messagesJson = mapper.writeValueAsString(messages);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not serialize messages");
        }
        String prompt = ollamaService.getPrompt(Prompt.UPDATE_CONTEXT)
                + "\nКонтекст:\n"
                + oldContext
                + String.format("\nПоследние %s сообщений:\n", messages.size())
                + messagesJson;

        return ollamaService.generatePromptAnswer(prompt);
    }
}
