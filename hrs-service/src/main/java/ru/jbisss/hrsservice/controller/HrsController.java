package ru.jbisss.hrsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.jbisss.hrsservice.entity.TariffEntity;
import ru.jbisss.hrsservice.repository.TariffRepository;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class HrsController {

    private final TariffRepository tariffRepository;

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "/checkTariff")
    public String checkTariffExistence(@RequestParam("tariffId") int tariffId) {
        Optional<TariffEntity> byId = tariffRepository.findById(tariffId);
        if (byId.isPresent()) {
            return "YES";
        }
        return "NO";
    }
}
