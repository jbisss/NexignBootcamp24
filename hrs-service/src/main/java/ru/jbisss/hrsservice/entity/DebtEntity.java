package ru.jbisss.hrsservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "abonent_debts")
@AllArgsConstructor
@NoArgsConstructor
public class DebtEntity {

    @Id
    @Column(name = "debt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int debtId;

    @Column(name = "abonent_phone_number")
    private String abonentPhoneNumber;

    @Column(name = "debt_date")
    private LocalDateTime debtDate;

    @Column(name = "debt_amount")
    private double debtAmount;
}
