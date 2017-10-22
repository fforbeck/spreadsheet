package com.fforbeck.spreadsheet.application.model.exception;

public class CyclicDependencyException extends RuntimeException {

    public CyclicDependencyException() {
        super("Cyclic dependency");
    }
}
