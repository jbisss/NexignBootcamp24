package ru.jbisss.cdrservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "TRANSACTIONS")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String callType;
    private long startCallTime;
    private long endCallTime;
    private int abonentId;

    public Transaction(String callType, long startCall, long endCall, int abonentId) {
        this.callType = callType;
        this.startCallTime = startCall;
        this.endCallTime = endCall;
        this.abonentId = abonentId;
    }
}
