package ru.jbisss.brtservice.service.cdrValidator;

import ru.jbisss.brtservice.domain.CdrWithTariff;

public interface ICdrValidator {

    CdrWithTariff validateCdrAndAddTariff(String cdrAsString);
}
