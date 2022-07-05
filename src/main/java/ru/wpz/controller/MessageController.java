package ru.wpz.controller;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.wpz.dto.MessageDto;
import ru.wpz.mapper.MessageMapper;
import ru.wpz.service.MessageService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1//message")
@AllArgsConstructor
public class MessageController {

    private final MessageMapper messageMapper;
    private final MessageService messageService;

    @GetMapping()
    public List<MessageDto> showAllOrganization(){
        return messageService.getAllMessage().stream()
                .map(messageMapper::mapMessageDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MessageDto getMessage(@PathVariable long id){
        return messageMapper.mapMessageDto(messageService.getMessage(id).orElse(null));
    }

    @PostMapping("/add")
    @Transactional
    public MessageDto saveNewMessage(@RequestBody MessageDto message){
        messageService.saveMessage(messageMapper.mapMessage(message));
        return message;
    }

    @PostMapping("/delete/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }

}
