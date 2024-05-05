package ru.jbisss.hrsservice.service.callDuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class CallDurationService implements ICallDurationService {

    @Getter
    @RequiredArgsConstructor
    static class CallDuration {

        private final int hours;
        private final int minutes;
        private final int seconds;

        @Override
        public String toString() {
            return String.format("%s:%s:%s", hours, minutes, seconds);
        }
    }

    @Override
    public CallDuration countCallDuration(long startCallTime, long endCallTime) {
        return new CallDuration(0, 0, 0);
    }
}
