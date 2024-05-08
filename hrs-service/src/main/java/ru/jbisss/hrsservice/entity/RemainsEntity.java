package ru.jbisss.hrsservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "remains")
@AllArgsConstructor
@NoArgsConstructor
public class RemainsEntity {

    @Id
    @Column(name = "remains_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer remainsId;

    @Column(name = "abonent_phone_number")
    private String abonentPhoneNumber;

    @OneToOne
    @JoinColumn(name = "service_in_tariff_id")
    private ServiceInTariffEntity serviceInTariff;

    @Column(name = "remains")
    private Integer remains;
}
