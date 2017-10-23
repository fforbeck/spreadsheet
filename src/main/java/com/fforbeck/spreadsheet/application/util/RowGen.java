package com.fforbeck.spreadsheet.application.util;

import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class RowGen implements IdGenerator<String> {

    private final int maxRows;
    private final int maxColumns;
    private final AtomicInteger currentColumn;
    private final Iterator<String> rowsIterator;
    private final Integer asciiCharADec = 65;
    private Optional<String> lastRow;

    public RowGen(int maxRows, int maxColumns, int maxRowsAllowed) {
        this.maxRows = maxRows;
        this.maxColumns = maxColumns;
        this.currentColumn = new AtomicInteger(0);
        this.rowsIterator = Stream
                .iterate(asciiCharADec, i -> i + 1)
                .map(i -> String.valueOf((char) i.intValue()))
                .limit(maxRowsAllowed)
                .iterator();
        this.lastRow = Optional.empty();
    }

    @Override
    public String next() {
        if (!lastRow.isPresent()) {
            this.lastRow = Optional.of(rowsIterator.next());
        } else if (currentColumn.get() % maxColumns == 0 && maxRows > 1) {
            this.lastRow = Optional.of(rowsIterator.next());
        }
        this.currentColumn.incrementAndGet();
        return String.valueOf(lastRow.get());
    }

}