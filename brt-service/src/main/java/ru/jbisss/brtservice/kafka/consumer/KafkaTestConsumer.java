package ru.jbisss.brtservice.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.jbisss.brtservice.kafka.producer.KafkaTestProducer;
import ru.jbisss.brtservice.service.brt.IBrtService;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaTestConsumer {

    private final IBrtService brtService;
    private final KafkaTestProducer kafkaTestProducer;

    @KafkaListener(topics = "testBrtTopic", groupId = "testGroup")
    public void consume(String message){
        log.info("<== message consumed: {}", message);
        brtService.setKafkaProducer(kafkaTestProducer);
        brtService.addTariffToCdrAndSend(message);
    }
}
