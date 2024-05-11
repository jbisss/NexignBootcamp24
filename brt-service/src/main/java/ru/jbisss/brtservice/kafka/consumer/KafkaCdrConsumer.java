package ru.jbisss.brtservice.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.jbisss.brtservice.kafka.producer.KafkaCdrWithTariffProducer;
import ru.jbisss.brtservice.service.brt.IBrtService;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaCdrConsumer {

    private final IBrtService brtService;

    @KafkaListener(topics = "cdrFiles", groupId = "cdrConsumerGroup")
    public void consume(String message){
        log.info("<== message consumed: {}", message);
        brtService.addTariffToCdrAndSend(message);
    }
}
