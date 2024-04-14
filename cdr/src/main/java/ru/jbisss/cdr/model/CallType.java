package ru.jbisss.cdr.model;

import lombok.Getter;

public enum CallType {

    OUT("01"),
    IN("02");

    @Getter
    private final String code;

    CallType(String code) {
        this.code = code;
    }
}
