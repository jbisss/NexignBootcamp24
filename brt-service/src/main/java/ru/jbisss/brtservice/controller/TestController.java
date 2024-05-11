package ru.jbisss.brtservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.jbisss.brtservice.service.brt.IBrtService;
import ru.jbisss.brtservice.service.cdrValidator.ICdrValidator;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final ICdrValidator cdrValidator;
    private final IBrtService brtService;

    @RequestMapping(method = RequestMethod.POST, value = "/test")
    public String getCdrWithTariff(@RequestBody String cdr) {
        return cdrValidator.validateCdrAndAddTariff(cdr).toString();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tariffCustomCdr")
    public String tariffCustomCdr(@RequestBody String cdr) {
        brtService.addTariffToCdrAndSend(cdr);
        return "success";
    }
}
