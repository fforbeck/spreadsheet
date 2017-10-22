package com.fforbeck.spreadsheet.application.util;

import java.util.concurrent.atomic.AtomicInteger;

public class ColumnGen implements IdGenerator<Integer> {

    private final Integer maxColumns;
    private AtomicInteger currentColumn;

    public ColumnGen(Integer maxColumns) {
        this.maxColumns = maxColumns;
        this.currentColumn = new AtomicInteger(0);
    }

    @Override
    public Integer next() {
        if (maxColumns == 1) {
            return 1;
        }
        Integer current = currentColumn.incrementAndGet();
        if (current > maxColumns) {
            this.currentColumn = new AtomicInteger(0);
            return this.currentColumn.incrementAndGet();
        }
        return current;
    }
}