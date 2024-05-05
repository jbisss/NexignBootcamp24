package ru.jbisss.cdrservice.domain;

import lombok.Builder;
import lombok.Getter;
import ru.jbisss.cdrservice.ApplicationConstants;

import java.util.ArrayList;
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

            public CallType getOppositeType() {
                if (this.code.equals(IN.getCode())) {
                    return OUT;
                }
                return IN;
            }
        }

        @Getter
        private final CallType callType;
        @Getter
        private final String callerPhoneNumber;
        @Getter
        private final String callingPhoneNumber;
        @Getter
        private final long startCallDate;
        @Getter
        private final long endCallDate;

        @Builder
        public CdrRow(CallType callType, String callerPhoneNumber, String callingPhoneNumber, long startCallDate, long endCallDate) {
            this.callType = callType;
            this.callerPhoneNumber = callerPhoneNumber;
            this.callingPhoneNumber = callingPhoneNumber;
            this.startCallDate = startCallDate;
            this.endCallDate = endCallDate;
        }

        public CdrRow getOppositeRow() {
            return new CdrRow(this.callType.getOppositeType(),
                    this.callingPhoneNumber,
                    this.callerPhoneNumber,
                    this.startCallDate,
                    this.endCallDate);
        }

        @Override
        public String toString() {
            return  callType.code + ApplicationConstants.COMMA_DELIMITER +
                    callerPhoneNumber + ApplicationConstants.COMMA_DELIMITER +
                    callingPhoneNumber + ApplicationConstants.COMMA_DELIMITER +
                    startCallDate + ApplicationConstants.COMMA_DELIMITER +
                    endCallDate;
        }
    }

    private final List<CdrRow> cdrRows = new ArrayList<>();

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
