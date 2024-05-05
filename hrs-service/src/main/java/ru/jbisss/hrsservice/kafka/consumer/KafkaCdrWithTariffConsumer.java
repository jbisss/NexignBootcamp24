package ru.jbisss.hrsservice.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.jbisss.hrsservice.service.IHrsService;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaCdrWithTariffConsumer {

    private final IHrsService hrsService;

    @KafkaListener(topics = "cdrWithTariffFiles", groupId = "cdrWithTariffConsumerGroup")
    public void consume(String message) {
        log.info("<== message consumed: {}", message);
        hrsService.handleCdrWithTariff(message);
    }
}
