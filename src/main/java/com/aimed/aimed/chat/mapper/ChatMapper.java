package com.aimed.aimed.chat.mapper;

import com.aimed.aimed.chat.dto.ChatDto;
import com.aimed.aimed.chat.entity.Chat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    ChatDto toDto(Chat chat);
}
