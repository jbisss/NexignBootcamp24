package ru.jbisss.hrsservice.service.tariff.validator;

import org.springframework.stereotype.Service;
import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;

@Service
public class ClassicTariffValidator implements TariffValidator {

    @Override
    public int getTariffValidatorCode() {
        return 11;
    }

    @Override
    public int countCallCost(CdrRow cdrRow) {
        return 0;
    }
}
