package ru.jbisss.cdrservice.filewriter;

import org.springframework.stereotype.Service;
import ru.jbisss.cdrservice.ApplicationConstants;
import ru.jbisss.cdrservice.cdrGenerator.domain.Cdr;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CdrFilesWriter implements FilesWriter<Cdr> {

    private static final AtomicInteger FILE_COUNTER = new AtomicInteger();
    private static final String FILE_NAME_PATTERN = "cdr_%s.txt";

    @Override
    public void write(Cdr cdrToWrite) {
        createFile(createFileName(), cdrToWrite.toString());
    }

    private void createFile(String fileName, String source) {
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createFileName() {
        String fileNameTemplate = ApplicationConstants.PATH_TO_CDRS + FILE_NAME_PATTERN;
        return String.format(fileNameTemplate, FILE_COUNTER.incrementAndGet());
    }
}
