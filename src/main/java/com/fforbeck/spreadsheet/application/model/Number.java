package com.fforbeck.spreadsheet.application.model;

import java.util.Optional;

public class Number extends Value {

    private final Double value;

    public Number(Double value) {
        this.value = value;
    }

    @Override
    public Optional<String> eval() {
        return Optional.of(value.toString());
    }

    @Override
    public String toString() {
        return String.format("%.5f", value);
    }
}