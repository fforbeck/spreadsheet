package com.fforbeck.spreadsheet.application.model;

import java.util.Optional;

public abstract class Value {
    public abstract Optional<String> eval();
}