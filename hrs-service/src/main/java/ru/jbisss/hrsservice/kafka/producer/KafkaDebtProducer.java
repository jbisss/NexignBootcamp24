package ru.jbisss.hrsservice.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.jbisss.hrsservice.domain.AbonentDebt;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaDebtProducer {

    @Value("${kafka.producer.topic.name}")
    private String kafkaTopicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(AbonentDebt abonentDebt) {
        log.info("==> sending message: {}", abonentDebt);
        kafkaTemplate.send(kafkaTopicName, abonentDebt.toString());
    }
}
