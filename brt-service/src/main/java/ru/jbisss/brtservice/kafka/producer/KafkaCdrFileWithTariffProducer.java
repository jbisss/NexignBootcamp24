package ru.jbisss.brtservice.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaCdrFileWithTariffProducer {

    @Value("${kafka.producer.topic.name}")
    private String kafkaTopicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        log.info("==> sending message: {}", message);
        kafkaTemplate.send(kafkaTopicName, message);
    }
}
