package ru.jbisss.cdrservice.generators;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.jbisss.cdrservice.ApplicationConstants;
import ru.jbisss.cdrservice.entity.Abonent;
import ru.jbisss.cdrservice.repository.AbonentRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PhoneNumberSubGenerator implements SubGenerator<String> {

    @Value("${generator.phoneNumberLength}")
    private int PHONE_NUMBER_LENGTH;
    private static final String NUMBER_STARTS_WITH = "7";
    private static final int BOUND_FOR_NUMBER_CHAR = 10;

    private final AbonentRepository abonentRepository;

    @Override
    public String generate() {
        String callerNumber = chooseNumber();
        return callerNumber + ApplicationConstants.COMMA_DELIMITER + chooseNumberExcept(callerNumber);
    }

    private String chooseNumber() {
        int nextInt = new Random().nextInt(2);
        if (nextInt == 0) {
            return getRandomNumber();
        }
        return getAnyExistingNumber();
    }

    private String chooseNumberExcept(String phoneNumberToExcept) {
        String result = phoneNumberToExcept;
        while (phoneNumberToExcept.equals(result)) {
            result = chooseNumber();
        }
        return result;
    }

    private String getAnyExistingNumber() {
        Iterator<Abonent> abonentIterator = abonentRepository.findAll().iterator();
        List<String> phoneNumbers = new ArrayList<>();
        while (abonentIterator.hasNext()) {
            phoneNumbers.add(abonentIterator.next().getPhoneNumber());
        }
        return phoneNumbers.get(new Random().nextInt(phoneNumbers.size()));
    }

    private String getRandomNumber() {
        StringBuilder number = new StringBuilder(NUMBER_STARTS_WITH);
        final int startNumberLength = number.length();
        for (int i = 0; i < PHONE_NUMBER_LENGTH - startNumberLength; i++) {
            number.append(new Random().nextInt(BOUND_FOR_NUMBER_CHAR));
        }
        return number.toString();
    }
}
