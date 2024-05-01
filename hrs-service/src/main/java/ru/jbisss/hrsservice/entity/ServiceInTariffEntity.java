package ru.jbisss.hrsservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "service_in_tariff")
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInTariffEntity {

    @Id
    @Column(name = "link_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceInTariffId;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    private TariffEntity tariff;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "count")
    private int count;

    @OneToOne(mappedBy = "serviceInTariff")
    private RemainsEntity remains;
}
