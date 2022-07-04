package ru.wpz.lessons.lesson1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.wpz.lessons.lesson1.mapper.MapperView;
import ru.wpz.lessons.lesson1.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MapperView mapperView;
    private final MessageService messageService;

    public MessageController(MapperView mapperView, MessageService messageService) {
        this.mapperView = mapperView;
        this.messageService = messageService;
    }
}
