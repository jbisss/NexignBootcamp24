package ru.jbisss.brtservice.service.abonent;

import ru.jbisss.brtservice.dto.TariffDto;
import ru.jbisss.brtservice.dto.AbonentDto;

public interface IAbonentService {

    AbonentDto addNewAbonent(AbonentDto abonentDto);

    AbonentDto changeAbonentTariff(TariffDto tariffDto, String msisdn);

    AbonentDto topUpAbonentBalance(int topUp, String msisdn);
}
