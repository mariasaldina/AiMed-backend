package com.aimed.aimed.message;

import com.aimed.aimed.message.entity.Message;
import com.aimed.aimed.message.enums.MessageType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @EntityGraph(attributePaths = {
            "userPayload",
            "assistantPayload"
    })
    List<Message> findByChatIdAndTypeIn(
            @Param("chatId") Long chatId,
            @Param("types") Collection<MessageType> types,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {
            "userPayload",
            "assistantPayload",
            "doctorSuggestionsPayload",
            "invitationPayload"
    })
    List<Message> findByChatId(@Param("chatId") Long chatId, Pageable pageable);

    void deleteByChatId(Long chatId);
}
