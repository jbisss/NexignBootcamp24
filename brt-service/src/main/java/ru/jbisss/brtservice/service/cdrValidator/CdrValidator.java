package ru.jbisss.brtservice.service.cdrValidator;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jbisss.brtservice.ApplicationConstants;
import ru.jbisss.brtservice.domain.CdrWithTariff;
import ru.jbisss.brtservice.entity.AbonentEntity;
import ru.jbisss.brtservice.repository.AbonentRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CdrValidator implements ICdrValidator {

    private final AbonentRepository abonentRepository;

    private final Map<String, AbonentEntity> abonentEntityMap = new HashMap<>();

    @PostConstruct
    public void postConstruct() {
        for (AbonentEntity currentAbonent : abonentRepository.findAll()) {
            abonentEntityMap.put(currentAbonent.getPhoneNumber(), currentAbonent);
        }
    }

    @Override
    public CdrWithTariff validateCdrAndAddTariff(String cdrAsString) {
        List<String> cdrRows = Arrays.stream(cdrAsString.split(ApplicationConstants.LINE_BREAK)).toList();
        CdrWithTariff cdrWithTariff = new CdrWithTariff();
        getFilteredCdrRows(cdrRows).forEach(filteredCdrRow -> {
            String[] filteredCdrRowTokens = filteredCdrRow.split(ApplicationConstants.COMMA_DELIMITER);
            CdrWithTariff.CdrRow.CallType callType = CdrWithTariff.CdrRow.CallType.getCallTypeByCode(filteredCdrRowTokens[0]);
            String callerPhoneNumber = filteredCdrRowTokens[1];
            String callingPhoneNumber = filteredCdrRowTokens[2];
            long startCallDate = Long.parseLong(filteredCdrRowTokens[3]);
            long endCallDate = Long.parseLong(filteredCdrRowTokens[4]);

            AbonentEntity abonentEntity = abonentRepository.findByPhoneNumber(callerPhoneNumber);

            int tariff = abonentEntity.getTariffId();

            cdrWithTariff.addRow(callType,
                    callerPhoneNumber,
                    callingPhoneNumber,
                    startCallDate,
                    endCallDate,
                    tariff);
        });
        return cdrWithTariff;
    }

    public List<String> getFilteredCdrRows(List<String> cdrRows) {
        return cdrRows.stream()
                .filter(cdrRow -> {
                    String abonentPhoneNumber = cdrRow.split(ApplicationConstants.COMMA_DELIMITER)[1];
                    return abonentEntityMap.containsKey(abonentPhoneNumber);
                })
                .collect(Collectors.toList());
    }
}
