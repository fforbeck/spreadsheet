package com.fforbeck.spreadsheet.application.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ColumnGenTest {

    @Test
    public void shouldReturnIncrementedColumnIdBasedOnMaxColumns() {
        ColumnGen columnGen = new ColumnGen(3);
        Assert.assertEquals(Integer.valueOf(1), columnGen.next());
        Assert.assertEquals(Integer.valueOf(2), columnGen.next());
        Assert.assertEquals(Integer.valueOf(3), columnGen.next());
    }

    @Test
    public void shouldAlwaysReturnTheSameColumnIdWhenMinimumNumberOfColumnsIsProvided() {
        ColumnGen columnGen = new ColumnGen(1);
        Assert.assertEquals(Integer.valueOf(1), columnGen.next());
        Assert.assertEquals(Integer.valueOf(1), columnGen.next());
        Assert.assertEquals(Integer.valueOf(1), columnGen.next());
    }

    @Test
    public void shouldResetAndReturnIncrementedColumnIdWhenMaxColumnsNumberIsReached() {
        ColumnGen columnGen = new ColumnGen(3);
        Assert.assertEquals(Integer.valueOf(1), columnGen.next());
        Assert.assertEquals(Integer.valueOf(2), columnGen.next());
        Assert.assertEquals(Integer.valueOf(3), columnGen.next());

        Assert.assertEquals(Integer.valueOf(1), columnGen.next());
        Assert.assertEquals(Integer.valueOf(2), columnGen.next());
        Assert.assertEquals(Integer.valueOf(3), columnGen.next());
    }
}