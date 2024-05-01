package ru.jbisss.hrsservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "services")
@AllArgsConstructor
@NoArgsConstructor
public class ServiceEntity {

    @Id
    @Column(name = "service_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceId;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_description")
    private String serviceDescription;

    @Column(name = "measure_unit")
    private String measureUnit;

    @OneToMany(mappedBy = "service")
    private Set<ServiceInTariffEntity> servicesInTariffSet = new HashSet<>();
}
