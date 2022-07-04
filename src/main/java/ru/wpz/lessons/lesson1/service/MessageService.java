package ru.wpz.lessons.lesson1.service;

import org.springframework.stereotype.Service;
import ru.wpz.lessons.lesson1.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
}
