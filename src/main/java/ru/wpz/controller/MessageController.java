package ru.wpz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.wpz.dto.MessageDto;
import ru.wpz.mapper.MessageMapper;
import ru.wpz.service.MessageService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/message")
@AllArgsConstructor
@Api(value = "message", produces = "Контроллер для сообщений")
public class MessageController {

    private final MessageMapper messageMapper;
    private final MessageService messageService;

    @GetMapping("/{devId}")
    @ApiOperation("Получение всех сообщений для датчика")
    public List<MessageDto> showAll(@PathVariable(name="devId") Long devId){
        return messageService.findAll(devId).stream()
                .map(messageMapper::mapMessageDto).collect(Collectors.toList());
    }



    @PostMapping
    @ApiOperation("Сохранение нового сообщения")
    public void save(@RequestBody MessageDto message){
        messageService.save(messageMapper.mapMessage(message));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удаление сообщения по id")
    public void delete(@PathVariable Long id) {
        messageService.delete(id);
    }

}
