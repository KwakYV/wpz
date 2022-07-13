package ru.wpz.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.wpz.kafka.core.KafkaConsumerFactory;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "ru.wpz.kafka.core")
public class KafkaWTZConfiguration {

    @Bean("kafka-wtz-producer")
    public KafkaProducer kafkaProducer(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:29092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("security.protocol", "Plaintext");
        return new KafkaProducer(properties);
    }

    @Bean("kafka-wtz-consumer")
    public KafkaConsumerFactory kafkaConsumerFactory(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:29092");
        properties.put("key.serializer", StringDeserializer.class.getName());
        properties.put("value.serializer", StringDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put("security.protocol", "Plaintext");
        return new KafkaConsumerFactory(properties, "wpz");
    }
}
