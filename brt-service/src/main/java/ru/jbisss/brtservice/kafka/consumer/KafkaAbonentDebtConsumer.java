package ru.jbisss.brtservice.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.jbisss.brtservice.service.debt.IDebtService;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaAbonentDebtConsumer {

    private final IDebtService debtService;

    @KafkaListener(topics = "abonentsDebts", groupId = "abonentsDebtsConsumerGroup")
    public void consume(String message){
        log.info("<== message consumed: {}", message);
        debtService.getDebtAndTariffAbonent(message);
    }
}
