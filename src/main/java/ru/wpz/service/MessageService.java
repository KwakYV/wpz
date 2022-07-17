package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wpz.dto.MessageDto;
import ru.wpz.entity.Message;
import ru.wpz.mapper.MessageMapper;
import ru.wpz.nio_server.NettyNetwork;
import ru.wpz.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final NettyNetwork nettyNetwork;

    public List<Message> findAll(long devId) {
        return messageRepository.findAll(devId);
    }

    public Optional<Message> get(long id) {
        return messageRepository.findById(id);
    }

    @Transactional
    public void save(Message message) {
        messageRepository.save(message);
    }

    public void delete(Long id) {
        messageRepository.deleteById(id);
    }

    public void saveFromKafka(MessageDto messageDto) {
        Message message = messageMapper.mapMessage(messageDto);
        save(message);
        messageDto = messageMapper.mapMessageDto(messageRepository.getMessageBy(message));
        nettyNetwork.writeMessage(messageDto);
    }
}

