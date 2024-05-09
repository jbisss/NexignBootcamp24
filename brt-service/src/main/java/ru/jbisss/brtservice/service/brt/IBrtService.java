package ru.jbisss.brtservice.service.brt;

public interface IBrtService {

    void addTariffToCdrAndSend(String cdrAsString);
}
