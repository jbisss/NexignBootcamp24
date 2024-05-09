package ru.jbisss.brtservice.service.brt;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.jbisss.brtservice.domain.CdrWithTariff;
import ru.jbisss.brtservice.kafka.producer.KafkaProducer;
import ru.jbisss.brtservice.service.cdrValidator.ICdrValidator;

@Service
@RequiredArgsConstructor
public class BrtService implements IBrtService {

    private final ICdrValidator cdrValidator;

    @Setter
    private KafkaProducer kafkaProducer;

    @Override
    public void addTariffToCdrAndSend(String cdrAsString) {
        CdrWithTariff cdrWithTariff = cdrValidator.validateCdrAndAddTariff(cdrAsString);
        kafkaProducer.sendMessage(cdrWithTariff.toString());
    }
}
