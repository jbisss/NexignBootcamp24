package ru.jbisss.hrsservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.jbisss.hrsservice.ApplicationConstants;

@Getter
@Setter
@AllArgsConstructor
public class AbonentDebt {

    private String callerPhoneNumber;
    private Double debtAmount;

    @Override
    public String toString() {
        return callerPhoneNumber + ApplicationConstants.COMMA_DELIMITER + debtAmount;
    }
}
