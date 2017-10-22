package com.fforbeck.spreadsheet.application.model;

import java.util.Optional;

public class Operator extends Value {

    private final String value;

    public Operator(String value) {
        this.value = value;
    }

    @Override
    public Optional<String> eval() {
        return Optional.of(value);
    }

    @Override
    public String toString() {
        return value;
    }

    public static boolean isOperator(String value) {
       return "+".equals(value) || "-".equals(value) || "*".equals(value) || "/".equals(value);
    }
}