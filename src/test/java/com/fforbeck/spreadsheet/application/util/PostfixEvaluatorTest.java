package com.fforbeck.spreadsheet.application.util;

import com.fforbeck.spreadsheet.application.model.CellRef;
import com.fforbeck.spreadsheet.application.model.Number;
import com.fforbeck.spreadsheet.application.model.Operator;
import com.fforbeck.spreadsheet.application.model.Value;
import com.fforbeck.spreadsheet.application.model.exception.CyclicDependencyException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Optional;

@RunWith(JUnit4.class)
public class PostfixEvaluatorTest {

    private ExpEvaluator<Value, Double> expEvaluator;

    @Before
    public void setup() {
        this.expEvaluator = new PostfixEvaluator();
    }

    @Test
    public void shouldEvaluateExpressionWithSuccessAndReturn5() {
        Optional<Double> evaluated = expEvaluator.evaluate(Arrays.asList(new Number(10d), new Number(2d), new Operator("/")));
        Assert.assertTrue(evaluated.isPresent());
        Assert.assertEquals(Double.valueOf(5d), evaluated.get());
    }

    @Test
    public void shouldEvaluateExpressionWithSuccessAndReturn12() {
        Optional<Double> evaluated = expEvaluator.evaluate(Arrays.asList(new Number(10d), new Number(2d), new Operator("+")));
        Assert.assertTrue(evaluated.isPresent());
        Assert.assertEquals(Double.valueOf(12d), evaluated.get());
    }

    @Test
    public void shouldEvaluateExpressionWithSuccessAndReturn8() {
        Optional<Double> evaluated = expEvaluator.evaluate(Arrays.asList(new Number(10d), new Number(2d), new Operator("-")));
        Assert.assertTrue(evaluated.isPresent());
        Assert.assertEquals(Double.valueOf(8d), evaluated.get());
    }

    @Test
    public void shouldEvaluateExpressionWithSuccessAndReturn20() {
        Optional<Double> evaluated = expEvaluator.evaluate(Arrays.asList(new Number(10d), new Number(2d), new Operator("*")));
        Assert.assertTrue(evaluated.isPresent());
        Assert.assertEquals(Double.valueOf(20d), evaluated.get());
    }

    @Test(expected = CyclicDependencyException.class)
    public void shouldFailExpressionEvaluationAndThrowRuntimeException() {
       expEvaluator.evaluate(Arrays.asList(new Number(10d), new CellRef("A1"), new Operator("*")));
    }
}