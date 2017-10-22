package com.fforbeck.spreadsheet.application.model;

import com.fforbeck.spreadsheet.application.util.SpreadsheetCache;

import java.util.Optional;

public class CellRef extends Value {

    private final static String MATCH_ALPHABET_REGEX = "[A-Z]";
    private final static String MATCH_NUMBERS_REGEX = "\\d+";
    private final static String MATCH_CELL_REF_REGEX = "[a-zA-Z]{1}\\d{1,}";

    private final String refRow;
    private final Integer refColumn;
    private Optional<String> value;

    public CellRef(String cellRefId) {
        this.refRow = cellRefId.split(MATCH_NUMBERS_REGEX)[0];
        this.refColumn = Integer.parseInt(cellRefId.split(MATCH_ALPHABET_REGEX)[1]);
    }

    @Override
    public Optional<String> eval() {
        if (value == null) {
            Optional<String> result = SpreadsheetCache.INSTANCE.read(refRow, refColumn);
            this.value = result.isPresent() ? result : Optional.empty();
        }
        return value;
    }

    @Override
    public String toString() {
        return refRow + refColumn;
    }

    public static boolean isCellRef(String value) {
        return value.matches(MATCH_CELL_REF_REGEX);
    }
}