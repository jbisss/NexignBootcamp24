package ru.jbisss.hrsservice.service.tariff.validator;

import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;

public interface TariffValidator {

    int getTariffValidatorCode();
    int countCallCost(CdrRow cdrRow);


}
