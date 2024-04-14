package ru.jbisss.cdr.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class Cdr {

    private CallType callType;
    private String mainNumber;
    private String dependentNumber;
    private long startCallTime;
    private long endCallTime;

    @Override
    public String toString() {
        return callType.getCode() + "," + mainNumber + "," + dependentNumber + "," + startCallTime + "," + endCallTime;
    }
}
