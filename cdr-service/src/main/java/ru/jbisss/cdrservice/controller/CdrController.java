package ru.jbisss.cdrservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.jbisss.cdrservice.ApplicationConstants;
import ru.jbisss.cdrservice.generators.ICdrGenerator;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CdrController {

    private final ICdrGenerator cdrGenerator;

    @RequestMapping(method = RequestMethod.POST, value = "/generateCdrs")
    public String generateCdr() {
        cdrGenerator.generateCdrs();
        return ApplicationConstants.SUCCESS;
    }
}
