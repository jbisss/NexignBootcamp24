package ru.jbisss.cdrservice.cdrGenerator.subGenerators;

import org.springframework.stereotype.Service;
import ru.jbisss.cdrservice.cdrGenerator.domain.Cdr;

import java.util.Random;

/**
 * Generator of call type
 */
@Service
public class CallTypeGenerator implements Generator<Cdr.CdrRow.CallType> {

    private static final int CALL_TYPE_BOUND = 2;

    @Override
    public Cdr.CdrRow.CallType generate() {
        int nextInt = new Random().nextInt(CALL_TYPE_BOUND) + 1;
        if (nextInt == 1) {
            return Cdr.CdrRow.CallType.OUT;
        }
        return Cdr.CdrRow.CallType.IN;
    }
}
