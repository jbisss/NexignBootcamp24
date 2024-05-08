package ru.jbisss.hrsservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tariffs")
@AllArgsConstructor
@NoArgsConstructor
public class TariffEntity {

    @Id
    @Column(name = "tariff_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tariffId;

    @Column(name = "tariff_name")
    private String tariffName;

    @Column(name = "tariff_description")
    private String tariffDescription;

    @Column(name = "abonent_payment")
    private BigDecimal abonentPayment;

    @Column(name = "tariff_extra")
    private Integer tariffExtra;

    @OneToMany(mappedBy = "tariff", fetch = FetchType.EAGER)
    private Set<ServiceInTariffEntity> servicesInTariffSet = new HashSet<>();
}
