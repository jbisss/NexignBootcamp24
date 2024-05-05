package ru.jbisss.hrsservice.service.callDuration;

import static ru.jbisss.hrsservice.service.callDuration.CallDurationService.*;

public interface ICallDurationService {

    CallDuration countCallDuration(long startCallTime, long endCallTime);
}
