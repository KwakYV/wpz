package ru.wpz.kafka.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.wpz.kafka.KafkaTopic;


@Component
@Slf4j
@Setter
public class KafkaHelper {

    @Autowired
    private KafkaProducer<String, String> producer;

    @Autowired
    private KafkaConsumerFactory consumerFactory;
    private final ObjectMapper failedOnIgnoreObjectMapper = new ObjectMapper();

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
}
