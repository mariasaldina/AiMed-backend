package com.aimed.aimed.chat;

import com.aimed.aimed.chat.entity.Chat;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Modifying
    @Transactional
    @Query(
            value = """
                    INSERT INTO chats_symptoms (chat_id, symptom_id)
                    SELECT :chatId, s.id
                    FROM symptoms s
                    WHERE s.id IN (:symptomIds)
                    ON CONFLICT DO NOTHING
                    """,
            nativeQuery = true
    )
    void addSymptomsToChat(
            @Param("chatId") Long chatId,
            @Param("symptomIds") List<Long> symptomIds
    );

    <T> List<T> findAllByUserIdOrderByIdDesc(Long userId, Class<T> type);

    @Modifying
    @Query("UPDATE Chat c SET c.context = :context WHERE c.id = :chatId")
    int updateContextById(@Param("chatId") Long chatId, @Param("context") String context);
}
