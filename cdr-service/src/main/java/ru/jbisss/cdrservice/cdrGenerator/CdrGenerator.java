package ru.jbisss.cdrservice.cdrGenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jbisss.cdrservice.ApplicationConstants;
import ru.jbisss.cdrservice.cdrGenerator.subGenerators.CallPeriodGenerator;
import ru.jbisss.cdrservice.filewriter.FilesWriter;
import ru.jbisss.cdrservice.entity.Abonent;
import ru.jbisss.cdrservice.entity.Transaction;
import ru.jbisss.cdrservice.repository.AbonentRepository;
import ru.jbisss.cdrservice.repository.TransactionRepository;
import ru.jbisss.cdrservice.cdrGenerator.domain.Cdr;
import ru.jbisss.cdrservice.cdrGenerator.subGenerators.CallTypeGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Generator of CDR-files
 */
@Service
@RequiredArgsConstructor
public class CdrGenerator implements ICdrGenerator {

    private static final int PERIODS_TO_GENERATE = 1;
    private static final int FILES_IN_SINGLE_PERIOD = 12;
    private static final int ROWS_IN_SINGLE_FILE = 50;

    private final CallPeriodGenerator callPeriodGenerator;
    private final CallTypeGenerator callTypeGenerator;
    private final FilesWriter<Cdr> cdrFilesWriter;

    private final AbonentRepository abonentRepository;
    private final TransactionRepository transactionRepository;

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
                    String[] callPeriodTokens = callPeriodGenerator.generateRandomCallPeriod(j + 1).split(ApplicationConstants.COMMA_DELIMITER);
                    Cdr.CdrRow.CallType randomCallType = callTypeGenerator.generateRandomCallType();
                    String chosenNumber = chooseNumber();
                    long startCall = Long.parseLong(callPeriodTokens[0]);
                    long endCall = Long.parseLong(callPeriodTokens[1]);
                    currentCdr.addRow(randomCallType, chosenNumber, startCall, endCall);
                    saveTransaction(randomCallType.getCode(), chosenNumber, startCall, endCall);
                }
                cdrs.add(currentCdr);
            }
        }
        cdrs.forEach(cdrFilesWriter::write);
        System.out.println(transactionRepository.findAll());
    }

    private String chooseNumber() {
        int nextInt = new Random().nextInt(2);
        if (nextInt == 0) {
            return getExistingNumber();
        }
        return getRandomNumber();
    }

    private String getExistingNumber() {
        Iterator<Abonent> abonentIterator = abonentRepository.findAll().iterator();
        List<String> phoneNumbers = new ArrayList<>();
        while (abonentIterator.hasNext()) {
            phoneNumbers.add(abonentIterator.next().getPhoneNumber());
        }
        return phoneNumbers.get(new Random().nextInt(phoneNumbers.size()));
    }

    private String getRandomNumber() {
        return "12";
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
