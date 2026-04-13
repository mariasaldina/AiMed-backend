package com.aimed.aimed.chat;

import com.aimed.aimed.chat.entity.Chat;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(value = """
        SELECT *
        FROM chats c
        WHERE c.user_id = :userId
        ORDER BY COALESCE(c.last_message_at, c.created_at) DESC
    """, nativeQuery = true)
    List<Chat> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE Chat c SET c.context = :context WHERE c.id = :chatId")
    int updateContextById(@Param("chatId") Long chatId, @Param("context") String context);

    @Modifying
    @Query("""
        UPDATE Invitation i
        SET i.message = NULL
        WHERE i.message.chat.id = :chatId
    """)
    void detachInvitations(Long chatId);
}
