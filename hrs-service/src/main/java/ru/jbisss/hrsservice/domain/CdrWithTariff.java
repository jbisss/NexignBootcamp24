package ru.jbisss.hrsservice.domain;

import lombok.Builder;
import lombok.Getter;
import ru.jbisss.hrsservice.ApplicationConstants;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CdrWithTariff {

    @Getter
    public static class CdrRow {

        public enum CallType {

            UNDEFINED("00"),
            IN("02"),
            OUT("01");

            @Getter
            private final String code;
            private static final Map<String, CallType> callTypeMap;

            static {
                callTypeMap = Arrays.stream(CallType.values())
                        .collect(Collectors.toMap(CallType::getCode, callType -> callType));
            }

            CallType(String code) {
                this.code = code;
            }

            public static CallType getCallTypeByCode(String code) {
                return callTypeMap.getOrDefault(code, UNDEFINED);
            }
        }

        private final CallType callType;
        private final String callerPhoneNumber;
        private final String callingPhoneNumber;
        private final long startCallDate;
        private final long endCallDate;
        private final int tariff;

        @Builder
        public CdrRow(CallType callType, String callerPhoneNumber, String callingPhoneNumber, long startCallDate, long endCallDate, int tariff) {
            this.callType = callType;
            this.callerPhoneNumber = callerPhoneNumber;
            this.callingPhoneNumber = callingPhoneNumber;
            this.startCallDate = startCallDate;
            this.endCallDate = endCallDate;
            this.tariff = tariff;
        }

        public String getCallMonthWithYearAsString() {
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(startCallDate, 0, ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            return dateTime.format(formatter);
        }

        public LocalDateTime getCallMonthWithYear() {
            return LocalDateTime.ofEpochSecond(startCallDate, 0, ZoneOffset.UTC);
        }

        public LocalDateTime getPreviousCallMonthWithYear() {
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(startCallDate, 0, ZoneOffset.UTC);
            return dateTime.minusMonths(1);
        }

        @Override
        public String toString() {
            return  callType.code + ApplicationConstants.COMMA_DELIMITER +
                    callerPhoneNumber + ApplicationConstants.COMMA_DELIMITER +
                    callingPhoneNumber + ApplicationConstants.COMMA_DELIMITER +
                    startCallDate + ApplicationConstants.COMMA_DELIMITER +
                    endCallDate + ApplicationConstants.COMMA_DELIMITER +
                    tariff;
        }
    }

    private final List<CdrRow> cdrRows = new ArrayList<>();

    public Iterator<CdrRow> getCdrRows() {
        return cdrRows.iterator();
    }

    public void addRow(CdrRow.CallType callType, String callerPhoneNumber, String callingPhoneNumber, long startCallDate, long endCallDate, int tariff) {
        cdrRows.add(new CdrRow(callType, callerPhoneNumber, callingPhoneNumber, startCallDate, endCallDate, tariff));
    }

    public void addRow(CdrRow cdrRow) {
        cdrRows.add(cdrRow);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        cdrRows.forEach(cdrRow -> stringBuilder.append(cdrRow).append(ApplicationConstants.LINE_BREAK));
        return stringBuilder.toString();
    }
}
