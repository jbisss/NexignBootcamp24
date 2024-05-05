package ru.jbisss.hrsservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jbisss.hrsservice.ApplicationConstants;
import ru.jbisss.hrsservice.domain.AbonentMoney;
import ru.jbisss.hrsservice.domain.CdrWithTariff;
import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;
import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow.CallType;
import ru.jbisss.hrsservice.entity.TariffEntity;
import ru.jbisss.hrsservice.repository.RemainsRepository;
import ru.jbisss.hrsservice.repository.ServiceInTariffRepository;
import ru.jbisss.hrsservice.repository.ServiceRepository;
import ru.jbisss.hrsservice.repository.TariffRepository;
import ru.jbisss.hrsservice.service.tariff.definer.TariffDefiner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HrsService implements IHrsService {

    private final TariffDefiner tariffDefiner;

    private final TariffRepository tariffRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceInTariffRepository serviceInTariffRepository;
    private final RemainsRepository remainsRepository;

    @Override
    public void handleCdrWithTariff(String cdrWithTariffAsString) {
        CdrWithTariff cdrWithTariff = constructCdrWithTariff(cdrWithTariffAsString);
        System.out.println(cdrWithTariff);

        for (Iterator<CdrRow> it = cdrWithTariff.getCdrRows(); it.hasNext(); ) {
            handleRow(it.next());
        }
    }

    private CdrWithTariff constructCdrWithTariff(String cdrWithTariffAsString) {
        CdrWithTariff cdrWithTariff = new CdrWithTariff();
        for (String cdrRow : cdrWithTariffAsString.split(ApplicationConstants.LINE_BREAK)) {
            String[] cdrWithTariffTokens = cdrRow.split(ApplicationConstants.COMMA_DELIMITER);
            CallType callType = CallType.getCallTypeByCode(cdrWithTariffTokens[0]);
            String callerPhoneNumber = cdrWithTariffTokens[1];
            String callingPhoneNumber = cdrWithTariffTokens[2];
            long startCallDate = Long.getLong(cdrWithTariffTokens[3]);
            long endCallDate = Long.getLong(cdrWithTariffTokens[4]);
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

    private void handleRow(CdrRow cdrRow) {
        Map<String, AbonentMoney> abonentMoneyMap = new HashMap<>();

        TariffEntity tariffEntity = tariffRepository
                .findById(cdrRow.getTariff()).orElseThrow(() -> new RuntimeException(""));
        if (tariffEntity.getAbonentPayment().intValue() == 0) {
            int valueToCount = tariffDefiner.defineTariffAndCountCallCost(cdrRow);
        } else {

        }
    }
}
