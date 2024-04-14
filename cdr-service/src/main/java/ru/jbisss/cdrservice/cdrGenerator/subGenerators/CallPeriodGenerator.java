package ru.jbisss.cdrservice.cdrGenerator.subGenerators;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

/**
 * Generator of call period
 */
@Service
public class CallPeriodGenerator {

    private static final int HOURS_BEFORE_NOW = 3;

    private static final int YEAR_TO_SET = 2024;
    private static final int DAY_OF_MONTH_TO_SET = 10;
    private static final int HOURS_TO_SET = 10;
    private static final int MINUTES_TO_SET = 10;

    public String generateRandomCallPeriod(int monthNumber) {
        LocalDateTime localDateTimeEnd = LocalDateTime.of(YEAR_TO_SET, monthNumber, DAY_OF_MONTH_TO_SET, HOURS_TO_SET, MINUTES_TO_SET);
        LocalDateTime localDateTimeStart = localDateTimeEnd.minusHours(HOURS_BEFORE_NOW);

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
