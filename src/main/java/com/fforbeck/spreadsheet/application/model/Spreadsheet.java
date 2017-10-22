package com.fforbeck.spreadsheet.application.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Spreadsheet {

    private final Map<String, List<Cell>> content;

    public Spreadsheet(Map<String, List<Cell>> content) {
        this.content = content;
    }

    public List<String> eval() {
        return content.values()
                .stream()
                .flatMap(List::stream)
                .map(cell -> cell.getValue())
                .map(cellValue -> cellValue.isPresent() ?
                        String.format("%.5f", Double.valueOf(cellValue.get())) : "Cyclic dependency - N/A")
                .collect(Collectors.toList());
    }

    public void update(String row, Integer column, String value) {
        this.content.get(row).get(column - 1).setValue(value);
    }

    public Optional<String> read(String row, Integer column) {
        return content.get(row).get(column - 1).getValue();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        content.forEach((key, value) -> sb.append(key + " => " + value).append("\n"));
        return sb.toString();
    }
}