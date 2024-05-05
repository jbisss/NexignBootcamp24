package ru.jbisss.cdrservice.generators;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.jbisss.cdrservice.ApplicationConstants;
import ru.jbisss.cdrservice.entity.Transaction;
import ru.jbisss.cdrservice.kafka.producer.KafkaCdrProducer;
import ru.jbisss.cdrservice.repository.AbonentRepository;
import ru.jbisss.cdrservice.repository.TransactionRepository;
import ru.jbisss.cdrservice.domain.Cdr;

import java.util.ArrayList;
import java.util.List;

/**
 * Generator of CDR-files
 */
@Service
@RequiredArgsConstructor
public class CdrGenerator implements ICdrGenerator {

    @Value("${generator.periodsToGenerate}")
    private int PERIODS_TO_GENERATE;
    @Value("${generator.filesInSinglePeriod}")
    private int FILES_IN_SINGLE_PERIOD;
    @Value("${generator.rowsInSingleFile}")
    private int ROWS_IN_SINGLE_FILE;

    private final CallPeriodSubGenerator callPeriodGenerator;
    private final CallTypeSubGenerator callTypeGenerator;

    private final AbonentRepository abonentRepository;
    private final TransactionRepository transactionRepository;
    private final PhoneNumberSubGenerator phoneNumberGenerator;

    private final KafkaCdrProducer kafkaCdrProducer;

    /**
     * Method which generates all CDR-files
     */
    @Override
    public void generateCdrs() {
        List<Cdr> cdrs = new ArrayList<>();
        for (int i = 0; i < PERIODS_TO_GENERATE; i++) {
            for (int j = 0; j < FILES_IN_SINGLE_PERIOD; j++) {
                Cdr currentCdr = new Cdr();
                for (int q = 0; q < ROWS_IN_SINGLE_FILE; q++) {
                    constructCdrRow(currentCdr, j + 1);
                }
                cdrs.add(currentCdr);
            }
        }
        cdrs.forEach(cdr -> kafkaCdrProducer.sendMessage(cdr.toString()));
        System.out.println(transactionRepository.findAll());
    }

    private void constructCdrRow(Cdr currentCdr, int monthNumber) {
        Cdr.CdrRow.CdrRowBuilder cdrRowBuilder = Cdr.CdrRow.builder();
        cdrRowBuilder.callType(callTypeGenerator.generate());
        addCallerAndCalling(cdrRowBuilder);
        addStartAndEndCall(cdrRowBuilder, monthNumber);
        addRowToCdr(currentCdr, cdrRowBuilder.build());
    }

    private void addCallerAndCalling(Cdr.CdrRow.CdrRowBuilder cdrRowBuilder) {
        String[] numbersTokens = phoneNumberGenerator.generate().split(ApplicationConstants.COMMA_DELIMITER);
        cdrRowBuilder
                .callerPhoneNumber(numbersTokens[0])
                .callingPhoneNumber(numbersTokens[1]);
    }

    private void addStartAndEndCall(Cdr.CdrRow.CdrRowBuilder cdrRowBuilder, int monthNumber) {
        callPeriodGenerator.setMonth(monthNumber);
        String[] callPeriodTokens = callPeriodGenerator.generate().split(ApplicationConstants.COMMA_DELIMITER);
        cdrRowBuilder
                .startCallDate(Long.parseLong(callPeriodTokens[0]))
                .endCallDate(Long.parseLong(callPeriodTokens[1]));
    }

    private void addRowToCdr(Cdr currentCdr, Cdr.CdrRow cdrRow) {
        currentCdr.addRow(cdrRow);
        currentCdr.addRow(cdrRow.getOppositeRow());
        saveTransaction(cdrRow.getCallType().getCode(),
                cdrRow.getCallerPhoneNumber(),
                cdrRow.getStartCallDate(),
                cdrRow.getEndCallDate());
        saveTransaction(cdrRow.getCallType().getOppositeType().getCode(),
                cdrRow.getCallingPhoneNumber(),
                cdrRow.getStartCallDate(),
                cdrRow.getEndCallDate());
    }

    private void saveTransaction(String callType, String chosenNumber, long startCall, long endCall) {
        try {
            int existingPhoneNumber = abonentRepository.findByPhoneNumber(chosenNumber);
            transactionRepository.save(new Transaction(callType, startCall, endCall, existingPhoneNumber));
        } catch (Exception e) {
            transactionRepository.save(new Transaction(callType, startCall, endCall, 999));
        }
    }
}
