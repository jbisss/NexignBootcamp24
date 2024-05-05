package ru.jbisss.hrsservice.service.tariff.definer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;
import ru.jbisss.hrsservice.service.tariff.validator.TariffValidator;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CodeTariffDefiner implements TariffDefiner {

    private final Set<TariffValidator> tariffValidatorSet;

    @Override
    public int defineTariffAndCountCallCost(CdrRow cdrRow) {
        TariffValidator rightTariffValidator = tariffValidatorSet.stream()
                .filter(validator -> validator.getTariffValidatorCode() == cdrRow.getTariff())
                .findFirst()
                .orElseThrow(() -> new RuntimeException(""));
        return rightTariffValidator.countCallCost(cdrRow);
    }
}
