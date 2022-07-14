package ru.wpz.kafka.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.wpz.entity.Organization;
import ru.wpz.kafka.KafkaTopic;
import ru.wpz.service.OrganizationService;


@Component
@Slf4j
@Setter
public class KafkaHelper {

    @Autowired
    OrganizationService organizationService;

    @Autowired
    private KafkaProducer<String, String> producer;

    @Autowired
    private KafkaConsumerFactory consumerFactory;
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

    @KafkaListener(topics = "test", groupId = "wpz")
    public void consume(String message){
        Organization organization = null;
        try {
            organization = notFailedOnIgnoredObjectMapper.readValue(message, Organization.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        organizationService.save(organization);
    }
}
