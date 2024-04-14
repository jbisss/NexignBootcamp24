package ru.jbisss.cdrservice.cdrGenerator.domain;

import lombok.Getter;
import ru.jbisss.cdrservice.ApplicationConstants;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cdr {

    public static class CdrRow {

        public enum CallType {

            IN("02"),
            OUT("01");

            @Getter
            private final String code;

            CallType(String code) {
                this.code = code;
            }
        }

        private final CallType callType;
        private final String phoneNumber;
        private final long startCallDate;
        private final long endCallDate;

        public CdrRow(CallType callType, String phoneNumber, long startCallDate, long endCallDate) {
            this.callType = callType;
            this.phoneNumber = phoneNumber;
            this.startCallDate = startCallDate;
            this.endCallDate = endCallDate;
        }

        public long getCallTime() {
            return endCallDate - startCallDate;
        }

        public String getCallMonth() {
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(startCallDate / 1000, 0, ZoneOffset.UTC);
            return String.valueOf(dateTime.getMonth().getValue());
        }

        public String getCallType() {
            return callType.getCode();
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        @Override
        public String toString() {
            return  callType.code + ApplicationConstants.COMMA_DELIMITER +
                    phoneNumber + ApplicationConstants.COMMA_DELIMITER +
                    startCallDate + ApplicationConstants.COMMA_DELIMITER +
                    endCallDate;
        }
    }

    private final List<CdrRow> cdrRows = new ArrayList<>();

    public void addRow(CdrRow.CallType callType, String phoneNumber, long startCallDate, long endCallDate) {
        cdrRows.add(new CdrRow(callType, phoneNumber, startCallDate, endCallDate));
    }

    public Iterator<CdrRow> getRowsIterator() {
        return this.cdrRows.iterator();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        cdrRows.forEach(cdrRow -> stringBuilder.append(cdrRow).append(ApplicationConstants.LINE_BREAK));
        return stringBuilder.toString();
    }
}
