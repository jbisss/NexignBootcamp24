package ru.jbisss.cdrservice.generators;

import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

/**
 * Generator of call period
 */
@Service
@Setter
public class CallPeriodSubGenerator implements SubGenerator<String> {

    private int hoursBeforeNow = 3;

    private int yearToSet = 2024;
    private int month = 10;
    private int dayOfMonthToSet = 10;
    private int hoursToSet = 10;
    private int minutesToSet = 10;

    @Override
    public String generate() {
        LocalDateTime localDateTimeEnd = LocalDateTime.of(yearToSet, month, dayOfMonthToSet, hoursToSet, minutesToSet);
        LocalDateTime localDateTimeStart = localDateTimeEnd.minusHours(hoursBeforeNow);

        long endMillis = localDateTimeEnd.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
        long startMillis = localDateTimeStart.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();

        long startCallDateInMillis = generateRandomBetweenTwoLong(startMillis, endMillis);
        long endCallDateInMillis = generateRandomBetweenTwoLong(startCallDateInMillis, endMillis);

        return String.format("%s,%s", startCallDateInMillis, endCallDateInMillis);
    }

    private long generateRandomBetweenTwoLong(long startMillis, long endMillis) {
        return new Random().nextLong(endMillis - startMillis + 1L) + startMillis;
    }
}
