package ru.wpz.kafka.core;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;
import java.util.Random;

@RequiredArgsConstructor
public class KafkaConsumerFactory {
    private final Properties properties;
    private final String staticPartConsumerGroupName;

    public KafkaConsumer<String, String> generateConsumer(){
        String randomString = generateRandomLatinString();
        properties.put("group.id", staticPartConsumerGroupName + " - " + randomString);
        return new KafkaConsumer<>(properties);
    }

    private String generateRandomLatinString() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 20;
        Random random = new Random();
        StringBuilder buffer  = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
