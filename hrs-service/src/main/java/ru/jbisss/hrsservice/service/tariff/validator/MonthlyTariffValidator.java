package ru.jbisss.hrsservice.service.tariff.validator;

import org.springframework.stereotype.Service;
import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;

@Service
public class MonthlyTariffValidator implements TariffValidator {

    @Override
    public int getTariffValidatorCode() {
        return 12;
    }

    @Override
    public int countCallCost(CdrRow cdrRow) {
        return 0;
    }
}
