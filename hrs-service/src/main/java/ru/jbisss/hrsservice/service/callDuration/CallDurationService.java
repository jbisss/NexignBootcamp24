package ru.jbisss.hrsservice.service.callDuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class CallDurationService implements ICallDurationService {

    @Getter
    @RequiredArgsConstructor
    public static class CallDuration {

        private final long hours;
        private final long minutes;
        private final long seconds;

        public long getMinutes() {
            if (seconds == 0) {
                return minutes;
            }
            return minutes + 1;
        }

        @Override
        public String toString() {
            return String.format("%s:%s:%s", hours, minutes, seconds);
        }
    }

    @Override
    public CallDuration countCallDuration(long startCallTime, long endCallTime) {
        // long entireSeconds = (endCallTime - startCallTime) / 1000;
        long entireSeconds = (endCallTime - startCallTime);
        long hours = (entireSeconds / 3600);
        entireSeconds -= hours * 3600;
        long minutes = (entireSeconds / 60);
        entireSeconds -= minutes * 60;
        long seconds = entireSeconds;
        return new CallDuration(hours, minutes, seconds);
    }
}
