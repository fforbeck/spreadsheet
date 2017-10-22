package com.fforbeck.spreadsheet.application.util;

import com.fforbeck.spreadsheet.application.model.*;
import com.fforbeck.spreadsheet.application.model.Number;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SpreadsheetCache {

    INSTANCE;

    private Spreadsheet spreadsheet;

    private final Set<String> dependencies;

    private SpreadsheetCache() {
        this.dependencies = new HashSet<>();
    }

    public void create(int maxColumns, int maxRows, Stream<String> content) {
        final IdGenerator<String> rowIdGen = new RowGen(maxRows, maxColumns);
        final IdGenerator<Integer> columnIdGen = new ColumnGen(maxColumns);
        this.spreadsheet = new Spreadsheet(content
                        .map(line -> getCellContent(rowIdGen.next(), columnIdGen.next(), line))
                        .collect(Collectors.groupingBy(Cell::getRow)));
    }

    public void update(String row, Integer column, String value) {
        this.spreadsheet.update(row, column, value);
    }

    public Optional<String> read(String row, Integer column) {
        if (spreadsheet != null) {
            return spreadsheet.read(row, column);
        }
        return Optional.empty();
    }

    public String print() {
        return spreadsheet.toString();
    }

    public Collection<String> evaluate() {
        if (spreadsheet != null) {
            return spreadsheet.eval();
        }
        return Collections.emptyList();
    }

    public boolean hasDependencies(String cellId) {
        return !dependencies.add(cellId);
    }

    public boolean removeDependency(String cellId) {
        return dependencies.remove(cellId);
    }

    private static Cell getCellContent(String row, int column, String line) {
        Collection<Value> cellElements = Arrays.asList(line.split(" "))
                .stream()
                .map(val -> {
                    if (Operator.isOperator(val)) return new Operator(val);
                    if (CellRef.isCellRef(val)) return new CellRef(val);
                    return new Number(Double.valueOf(val));
                })
                .collect(Collectors.toList());
        return new Cell(row, column, cellElements);
    }
}
