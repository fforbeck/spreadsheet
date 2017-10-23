package com.fforbeck.spreadsheet.application.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.NoSuchElementException;

@RunWith(JUnit4.class)
public class RowGenTest {

    @Test
    public void shouldReturnRowIdWithSuccessBasedOnMaxRowsAndColumns() {
        RowGen rowGen = new RowGen(2, 2, 2);
        Assert.assertEquals("A", rowGen.next());
        Assert.assertEquals("A", rowGen.next());
        Assert.assertEquals("B", rowGen.next());
        Assert.assertEquals("B", rowGen.next());
    }

    @Test
    public void shouldReturnRowIdWithSuccessWhenTheSmallestDimensionIsProvided() {
        RowGen rowGen = new RowGen(1, 1, 1);
        Assert.assertEquals("A", rowGen.next());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldReturnRowIdWithSuccessWhenInvalidDimensionIsProvided() {
        new RowGen(0, 0, 0).next();
    }

}