package ru.jbisss.cdrservice.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic cdrFilesTopic() {
        return TopicBuilder.name("cdrFiles")
                .build();
    }
}
