package com.aimed.aimed.message;

import com.aimed.aimed.message.entity.Message;
import com.aimed.aimed.message.enums.MessageType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@Repository
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
    Slice<Message> findByChatIdOrderByCreatedAtDesc(
            Long chatId,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {
            "userPayload",
            "assistantPayload",
            "doctorSuggestionsPayload",
            "invitationPayload"
    })
    @Query("""
        SELECT m
        FROM Message m
        WHERE m.chat.id = :chatId
            AND m.createdAt <= :before
        ORDER BY m.createdAt DESC
    """)
    Slice<Message> findByChatIdBefore(@Param("chatId") Long chatId, OffsetDateTime before, Pageable pageable);

    void deleteByChatId(Long chatId);
}
