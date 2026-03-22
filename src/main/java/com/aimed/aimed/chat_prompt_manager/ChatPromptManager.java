package com.aimed.aimed.chat_prompt_manager;

import com.aimed.aimed.specialization.SpecializationsDictionaryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatPromptManager {
    private final OllamaService ollamaService;
    private final String updateContextPrompt;
    private final String assistantResponsePrompt;
    private final String analyzeContextPrompt;
    private final SpecializationsDictionaryService specializationsDictionaryService;

    public ChatPromptManager(
            OllamaService ollamaService,
            SpecializationsDictionaryService specializationsDictionaryService) {
        this.ollamaService = ollamaService;
        this.updateContextPrompt = this.readPrompt("update_context.txt");
        this.assistantResponsePrompt = this.readPrompt("assistant_response.txt");
        this.analyzeContextPrompt = this.readPrompt("analyze_context.txt");
        this.specializationsDictionaryService = specializationsDictionaryService;
    }

    public String readPrompt(String promptFileName) {
        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("prompts/" + promptFileName)) {
            if (is == null) {
                throw new IllegalStateException("Prompt not found");
            }

            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(promptFileName + " loading failed");
        }
    }

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
                "Используй следующие данные для генерации ответа." +
                        "Не повторяй их как сообщение пользователя," +
                        "просто учитывай при формировании рекомендаций и ответов.\n"
                + context
        );
        reqMessages.addFirst(contextMessage);

        OllamaChatMessage promptMessage = new OllamaChatMessage(
                OllamaChatRole.system,
                assistantResponsePrompt
        );
        reqMessages.addLast(promptMessage);

        OllamaChatMessage rawResponse = ollamaService.generateChatMessage(reqMessages);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return mapper.readValue(rawResponse.getContent(), AssistantMessageDto.class);
    }

    private String formatMessages(List<Message> messages) {
        StringBuilder lastMessagesText = new StringBuilder();
        for (Message m : messages) {
            if (m.getType() == MessageType.USER) {
                lastMessagesText.append("Пользователь: ")
                        .append(m.getUserPayload().getContent())
                        .append("\n");
            } else {
                lastMessagesText.append("Ассистент: ")
                        .append(formAssistantMessage(m.getAssistantPayload()))
                        .append("\n");
            }
        }
        return lastMessagesText.toString();
    }

    public String updateContext(
            String oldContext,
            List<Message> messages
    ) {
        String prompt = updateContextPrompt
                + "\nКонтекст:\n"
                + oldContext
                + String.format("\nПоследние %s сообщений:\n", messages.size())
                + formatMessages(messages);

        return ollamaService.generatePromptAnswer(prompt);
    }

    @Retry(name = "symptomAnalysisRetry")
    public List<Long> analyzeContext(String context) throws JsonProcessingException {
        String prompt = analyzeContextPrompt
                + "\nspecializations:\n"
                + specializationsDictionaryService.getSpecializationsString()
                + "\nКонтекст диалога:\n"
                + context;
        String rawResponse = ollamaService.generatePromptAnswer(prompt);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(rawResponse, new TypeReference<List<Long>>() {});
    }
}
