package ru.jbisss.brtservice.service.brt;

import ru.jbisss.brtservice.kafka.producer.KafkaProducer;

public interface IBrtService {

    void addTariffToCdrAndSend(String cdrAsString);
    void setKafkaProducer(KafkaProducer kafkaProducer);
}
