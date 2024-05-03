package ru.jbisss.brtservice.service.brt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jbisss.brtservice.domain.CdrWithTariff;
import ru.jbisss.brtservice.kafka.producer.KafkaCdrFileWithTariffProducer;
import ru.jbisss.brtservice.service.cdrValidator.ICdrValidator;

@Service
@RequiredArgsConstructor
public class BrtService implements IBrtService {

    private final ICdrValidator cdrValidator;

    private final KafkaCdrFileWithTariffProducer kafkaCdrFileWithTariffProducer;


    @Override
    public void addTariffToCdrAndSend(String cdrAsString) {
        CdrWithTariff cdrWithTariff = cdrValidator.validateCdrAndAddTariff(cdrAsString);
        kafkaCdrFileWithTariffProducer.sendMessage(cdrWithTariff.toString());
    }
}
