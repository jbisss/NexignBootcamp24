package ru.jbisss.hrsservice.service.tariff.definer;

import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;
import ru.jbisss.hrsservice.entity.TariffEntity;

public interface TariffDefiner {

    void defineTariffAndCountDebt(CdrRow cdrRow);

    TariffEntity defineTariff(int cdrTariffCode);
}
