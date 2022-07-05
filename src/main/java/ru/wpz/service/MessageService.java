package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wpz.entity.Message;
import ru.wpz.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<Message> getAllMessage() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessage(long id) {
        return messageRepository.findById(id);
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}

