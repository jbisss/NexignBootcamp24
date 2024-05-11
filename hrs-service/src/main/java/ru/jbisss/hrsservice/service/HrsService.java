package ru.jbisss.hrsservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jbisss.hrsservice.ApplicationConstants;
import ru.jbisss.hrsservice.domain.AbonentDebt;
import ru.jbisss.hrsservice.domain.CdrWithTariff;
import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;
import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow.CallType;
import ru.jbisss.hrsservice.kafka.producer.KafkaDebtProducer;
import ru.jbisss.hrsservice.repository.DebtRepository;
import ru.jbisss.hrsservice.repository.RemainsRepository;
import ru.jbisss.hrsservice.service.remains.initializer.IRemainsInitializer;
import ru.jbisss.hrsservice.service.tariff.definer.TariffDefiner;

import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class HrsService implements IHrsService {

    private final KafkaDebtProducer kafkaDebtProducer;

    private final TariffDefiner tariffDefiner;

    private final RemainsRepository remainsRepository;
    private final DebtRepository debtRepository;

    private final IRemainsInitializer remainsInitializer;

    @Override
    public void handleCdrWithTariff(String cdrWithTariffAsString) {
        CdrWithTariff cdrWithTariff = constructCdrWithTariff(cdrWithTariffAsString);
        remainsInitializer.initializeRemains(cdrWithTariff);

        for (Iterator<CdrRow> it = cdrWithTariff.getCdrRows(); it.hasNext();) {
            tariffDefiner.defineTariffAndCountDebt(it.next());
        }
        sendDebtsToBrt();
        if (cdrWithTariff.getCdrRows().next().getCallMonthWithYear().getMonthValue() == 12) {
            clearDebtsPerCdr();
        }
    }

    private CdrWithTariff constructCdrWithTariff(String cdrWithTariffAsString) {
        CdrWithTariff cdrWithTariff = new CdrWithTariff();
        for (String cdrRow : cdrWithTariffAsString.split(ApplicationConstants.LINE_BREAK)) {
            String[] cdrWithTariffTokens = cdrRow.split(ApplicationConstants.COMMA_DELIMITER);
            CallType callType = CallType.getCallTypeByCode(cdrWithTariffTokens[0]);
            String callerPhoneNumber = cdrWithTariffTokens[1];
            String callingPhoneNumber = cdrWithTariffTokens[2];
            long startCallDate = Long.parseLong(cdrWithTariffTokens[3]);
            long endCallDate = Long.parseLong(cdrWithTariffTokens[4]);
            int tariffCode = Integer.parseInt(cdrWithTariffTokens[5]);

            cdrWithTariff.addRow(CdrRow.builder()
                    .callType(callType)
                    .callerPhoneNumber(callerPhoneNumber)
                    .callingPhoneNumber(callingPhoneNumber)
                    .startCallDate(startCallDate)
                    .endCallDate(endCallDate)
                    .tariff(tariffCode)
                    .build());
        }
        return cdrWithTariff;
    }

    private void sendDebtsToBrt() {
        debtRepository.findAll().stream()
                .map(debtEntity -> new AbonentDebt(debtEntity.getAbonentPhoneNumber(), debtEntity.getDebtAmount()))
                .forEach(kafkaDebtProducer::sendMessage);
    }

    private void clearDebtsPerCdr() {
        debtRepository.deleteAll();
    }
}
