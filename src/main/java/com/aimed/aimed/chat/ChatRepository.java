package com.aimed.aimed.chat;

import com.aimed.aimed.chat.entity.Chat;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    <T> List<T> findAllByUserIdOrderByLastMessageAtDesc(Long userId, Class<T> type);

    @Modifying
    @Query("UPDATE Chat c SET c.context = :context WHERE c.id = :chatId")
    int updateContextById(@Param("chatId") Long chatId, @Param("context") String context);
}
