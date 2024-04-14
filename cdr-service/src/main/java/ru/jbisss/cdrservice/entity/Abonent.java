package ru.jbisss.cdrservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "ABONENTS")
@AllArgsConstructor
@NoArgsConstructor
public class Abonent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phoneNumber;

    @Override
    public String toString() {
        return "{" + id + "," + phoneNumber + "}";
    }
}

