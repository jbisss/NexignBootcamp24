package ru.jbisss.cdrservice.cdrGenerator.subGenerators;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.jbisss.cdrservice.entity.Abonent;
import ru.jbisss.cdrservice.repository.AbonentRepository;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PhoneNumberGenerator implements Generator<String> {

    @Value("${generator.phoneNumberLength}")
    private int PHONE_NUMBER_LENGTH;
    private static final String NUMBER_STARTS_WITH = "7";
    private static final int BOUND_FOR_NUMBER_CHAR = 10;

    private final AbonentRepository abonentRepository;

    @Override
    public String generate() {
        List<String> existingPhoneNumbers = abonentRepository.findAll().stream()
                .map(Abonent::getPhoneNumber)
                .toList();
        String generatedPhoneNumber = existingPhoneNumbers.get(0);
        while (existingPhoneNumbers.contains(generatedPhoneNumber)) {
            generatedPhoneNumber = getRandomNumber();
        }
        return generatedPhoneNumber;
    }

    private String getRandomNumber() {
        StringBuilder number = new StringBuilder(NUMBER_STARTS_WITH);
        for (int i = 0; i < PHONE_NUMBER_LENGTH - number.length(); i++) {
            number.append(new Random().nextInt(BOUND_FOR_NUMBER_CHAR));
        }
        return number.toString();
    }
}
