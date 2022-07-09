package ru.wpz.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.wpz.dto.MessageDto;
import ru.wpz.entity.Message;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring")
public abstract class MessageMapper {

    public abstract MessageDto mapMessageDto(Message message);

    public abstract Message mapMessage(MessageDto messageDto);
}
