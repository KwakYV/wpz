package ru.wpz.kafka.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import ru.wpz.dto.MessageDto;
import ru.wpz.entity.Organization;
import ru.wpz.kafka.KafkaTopic;
import ru.wpz.mapper.MessageMapper;
import ru.wpz.service.MessageService;
import ru.wpz.service.OrganizationService;


@Component
@Slf4j
@Setter
@AllArgsConstructor
public class KafkaHelper {

    private final MessageService messageService;
    private KafkaProducer<String, String> producer;

    private final ObjectMapper notFailedOnIgnoredObjectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

    public void sendTo(KafkaTopic topic, String msg){
        send(producer, topic, msg);
    }

    @SneakyThrows
    private void send(KafkaProducer<String, String> producer, KafkaTopic topic, String json) {
        String topicName = topic.getTopicName();
        log.info("В топик {} отправляется сообщение {}", topic, json);
        producer.send(new ProducerRecord<>(topicName, json));
        producer.flush();
    }


    //метод для слушателя будет далее переделан в зависимости от логики
    @KafkaListener(topics = "test", groupId = "wpz")
    public void consume(String message){
        MessageDto messageDto = null;
        try {
            messageDto = notFailedOnIgnoredObjectMapper.readValue(message, MessageDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        messageService.saveFromKafka(messageDto);
    }
}
