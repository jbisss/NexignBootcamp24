package ru.jbisss.cdr.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "call_type")
    private String callType;

    @Column(name = "start_call_time")
    private long startCallTime;

    @Column(name = "end_call_time")
    private long endCallTime;

    @Column(name = "abonent_id")
    private int abonentId;
}
