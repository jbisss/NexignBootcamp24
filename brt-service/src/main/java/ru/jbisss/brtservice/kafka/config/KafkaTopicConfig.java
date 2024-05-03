package ru.jbisss.brtservice.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.producer.topic.name}")
    private String kafkaTopicName;

    @Bean
    public NewTopic cdrWithTariffTopic() {
        return TopicBuilder.name(kafkaTopicName)
                .build();
    }
}
