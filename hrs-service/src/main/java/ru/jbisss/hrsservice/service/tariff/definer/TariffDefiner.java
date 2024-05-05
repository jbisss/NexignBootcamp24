package ru.jbisss.hrsservice.service.tariff.definer;

import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;

public interface TariffDefiner {

    int defineTariffAndCountCallCost(CdrRow cdrRow);
}
