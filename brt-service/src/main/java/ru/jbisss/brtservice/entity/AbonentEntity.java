package ru.jbisss.brtservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "abonents")
@AllArgsConstructor
@NoArgsConstructor
public class AbonentEntity {

    @Id
    @Column(name = "abonent_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int abonentId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "tariff_id")
    private int tariffId;

    @Column(name = "connection_date")
    private LocalDateTime connectionDate;

    @Column(name = "balance")
    private BigDecimal balance;
}
