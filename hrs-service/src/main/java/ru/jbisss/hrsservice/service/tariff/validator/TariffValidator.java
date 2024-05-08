package ru.jbisss.hrsservice.service.tariff.validator;

import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;
import ru.jbisss.hrsservice.entity.TariffEntity;

public interface TariffValidator {

    TariffEntity getCorrespondingTariff();
    double countDebt(CdrRow cdrRow, long minutes);
}
