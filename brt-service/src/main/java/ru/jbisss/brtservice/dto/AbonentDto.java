package ru.jbisss.brtservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.jbisss.brtservice.ApplicationConstants;
import ru.jbisss.brtservice.entity.AbonentEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
public class AbonentDto {

    private int abonentId;
    private String msisdn;
    private int tariffId;
    private String connectionDate;
    private Double money;

    public static LocalDateTime getLocalDateTimeConnectionDate(AbonentDto abonentDto) {
        String[] splitConnectionDate = abonentDto.getConnectionDate().split(ApplicationConstants.DASH);
        return LocalDateTime.of(Integer.parseInt(splitConnectionDate[0]),
                Integer.parseInt(splitConnectionDate[1]),
                Integer.parseInt(splitConnectionDate[2]), 0, 0);
    }

    public static AbonentDto buildDtoByEntity(AbonentEntity abonentEntity) {
        return AbonentDto.builder()
                .abonentId(abonentEntity.getAbonentId())
                .tariffId(abonentEntity.getTariffId())
                .msisdn(abonentEntity.getPhoneNumber())
                .money(abonentEntity.getBalance())
                .connectionDate(abonentEntity.getConnectionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }
}
