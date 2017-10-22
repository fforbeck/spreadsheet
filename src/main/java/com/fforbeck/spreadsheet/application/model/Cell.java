package com.fforbeck.spreadsheet.application.model;

import com.fforbeck.spreadsheet.application.model.exception.CyclicDependencyException;
import com.fforbeck.spreadsheet.application.util.ExpEvaluator;
import com.fforbeck.spreadsheet.application.util.PostfixEvaluator;
import com.fforbeck.spreadsheet.application.util.SpreadsheetCache;

import java.util.Collection;
import java.util.Optional;

public class Cell {

    private final String row;
    private final Integer column;
    private final Collection<Value> cellElements;
    private final ExpEvaluator<Value, Double> expEvaluator;
    private Optional<String> value;

    public Cell(String row, Integer column, Collection<Value> cellElements) {
        this.row = row;
        this.column = column;
        this.cellElements = cellElements;
        this.expEvaluator = new PostfixEvaluator();
    }

    public String getRow() {
        return row;
    }

    public Optional<String> getValue() {
        if (value == null) {
            this.value = this.eval();
        }
        return value;
    }

    public void setValue(String value) {
        this.value = Optional.of(value);
    }

    private Optional<String> eval() {
        if (SpreadsheetCache.INSTANCE.hasDependencies(row + column)) {
            return Optional.empty();
        }
        try {
            Optional<Double> optValue = expEvaluator.evaluate(cellElements);
            String newValue = optValue.get().toString();
            SpreadsheetCache.INSTANCE.update(row, column, newValue);
            return Optional.of(newValue);
        } catch (CyclicDependencyException e) {
            return Optional.empty();
        } finally {
            SpreadsheetCache.INSTANCE.removeDependency(row + column);
        }
    }

    @Override
    public String toString() {
        return row + "," + column + "=" + cellElements.toString();
    }
}