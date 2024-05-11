package ru.jbisss.brtservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jbisss.brtservice.dto.TariffDto;
import ru.jbisss.brtservice.dto.AbonentDto;
import ru.jbisss.brtservice.entity.AbonentEntity;
import ru.jbisss.brtservice.repository.AbonentRepository;
import ru.jbisss.brtservice.service.abonent.IAbonentService;

import java.util.Optional;

@RestController
@RequestMapping("/abonents")
@RequiredArgsConstructor
public class CrmController {

    private final IAbonentService<AbonentDto> abonentService;
    private final AbonentRepository abonentRepository;

    @RequestMapping(method = RequestMethod.GET, value = "checkAbonent")
    public String checkAbonent(@RequestParam("phoneNumber") String phoneNumber) {
        Optional<AbonentEntity> optionalAbonentEntity = abonentRepository.findByPhoneNumber(phoneNumber);
        if (optionalAbonentEntity.isPresent()) {
            return "YES";
        }
        return "NO";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AbonentDto> addNewAbonent(@RequestBody AbonentDto abonentDto) {
        return abonentService.addNewAbonent(abonentDto);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{msisdn}/tariff")
    public ResponseEntity<AbonentDto> changeAbonentTariff(@RequestBody TariffDto tariffDto, @PathVariable String msisdn) {
        return abonentService.changeAbonentTariff(tariffDto, msisdn);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{msisdn}/money")
    public ResponseEntity<AbonentDto> topUpAbonentBalance(@RequestBody int topUp, @PathVariable String msisdn) {
        return abonentService.topUpAbonentBalance(topUp, msisdn);
    }
}
