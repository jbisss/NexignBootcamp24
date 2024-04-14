package ru.jbisss.cdrservice.filewriter;

public interface FilesWriter<T> {

    void write(T filesRepresentation);
}
