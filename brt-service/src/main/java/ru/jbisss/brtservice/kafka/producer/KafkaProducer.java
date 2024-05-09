package ru.jbisss.brtservice.kafka.producer;

public interface KafkaProducer {

    void sendMessage(String message);
}
