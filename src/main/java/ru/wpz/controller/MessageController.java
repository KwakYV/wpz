package ru.wpz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "message", produces = "Контроллер для сообщений")
public class MessageController {

    private final MessageMapper messageMapper;
    private final MessageService messageService;

    @GetMapping()
    @ApiOperation("Получение всех сообщений")
    public List<MessageDto> showAll(){
        return messageService.getAll().stream()
                .map(messageMapper::mapMessageDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation("Поиск сообщения по id")
    public MessageDto get(@PathVariable long id){
        return messageMapper.mapMessageDto(messageService.get(id).orElse(null));
    }

    @PostMapping("/add")
    @Transactional
    @ApiOperation("Сохранение нового сообщения")
    public MessageDto save(@RequestBody MessageDto message){
        messageService.save(messageMapper.mapMessage(message));
        return message;
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("Удаление сообщения по id")
    public void delete(@PathVariable Long id) {
        messageService.delete(id);
    }

}
