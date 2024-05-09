package ru.jbisss.brtservice.service.abonent;

import org.springframework.http.ResponseEntity;
import ru.jbisss.brtservice.dto.TariffDto;
import ru.jbisss.brtservice.dto.AbonentDto;

public interface IAbonentService <T> {

    ResponseEntity<T> addNewAbonent(AbonentDto abonentDto);

    ResponseEntity<T> changeAbonentTariff(TariffDto tariffDto, String msisdn);

    ResponseEntity<T> topUpAbonentBalance(int topUp, String msisdn);
}
