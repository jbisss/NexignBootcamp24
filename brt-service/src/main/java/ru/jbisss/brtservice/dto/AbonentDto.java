package ru.jbisss.brtservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class AbonentDto {

    private int abonentId;
    private String phoneNumber;
    private int tariffId;
    private LocalDateTime connectionDate;
    private Double balance;
}
