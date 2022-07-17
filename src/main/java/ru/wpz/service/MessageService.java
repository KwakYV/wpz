package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wpz.entity.Message;
import ru.wpz.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

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
}

