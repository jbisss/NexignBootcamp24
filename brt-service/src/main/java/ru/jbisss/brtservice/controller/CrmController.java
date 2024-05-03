package ru.jbisss.brtservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.jbisss.brtservice.dto.TariffDto;
import ru.jbisss.brtservice.dto.AbonentDto;
import ru.jbisss.brtservice.service.abonent.IAbonentService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CrmController {

    private final IAbonentService abonentService;

    @RequestMapping(method = RequestMethod.POST, value = "/abonents")
    public AbonentDto addNewAbonent(@RequestBody AbonentDto abonentDto) {
        return abonentService.addNewAbonent(abonentDto);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/abonents/{msisdn}/tariff")
    public AbonentDto changeAbonentTariff(@RequestBody TariffDto tariffDto, @PathVariable String msisdn) {
        return abonentService.changeAbonentTariff(tariffDto, msisdn);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/abonents/{msisdn}/balance")
    public AbonentDto topUpAbonentBalance(@RequestBody int topUp, @PathVariable String msisdn) {
        return abonentService.topUpAbonentBalance(topUp, msisdn);
    }
}
