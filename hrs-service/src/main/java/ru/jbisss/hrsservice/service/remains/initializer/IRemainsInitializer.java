package ru.jbisss.hrsservice.service.remains.initializer;

import ru.jbisss.hrsservice.domain.CdrWithTariff;

public interface IRemainsInitializer {

    void initializeRemains(CdrWithTariff cdrWithTariff);
}
