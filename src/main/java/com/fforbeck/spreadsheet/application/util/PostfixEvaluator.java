package com.fforbeck.spreadsheet.application.util;

import com.fforbeck.spreadsheet.application.model.Value;
import com.fforbeck.spreadsheet.application.model.exception.CyclicDependencyException;

import java.util.*;
import java.util.function.BiFunction;

public class PostfixEvaluator implements ExpEvaluator<Value, Double> {

    private final Map<String, BiFunction<Double, Double, Double>> operations;

    public PostfixEvaluator() {
        this.operations = new HashMap<>();
        this.operations.put("*", (x, y) -> y * x );
        this.operations.put("/", (x, y) -> y / x);
        this.operations.put("+", (x, y) -> y + x);
        this.operations.put("-", (x, y) -> y - x);
    }

    @Override
    public Optional<Double> evaluate(Collection<Value> values) {
        return evaluate(values, new Stack<>());
    }

    private Optional<Double> evaluate(Collection<Value> values, Stack<Optional<Double>> stack) {
        values.stream().forEach(element -> stack.push(evaluate(element, stack)));
        return stack.pop();
    }

    private Optional<Double> evaluate(Value value, Stack<Optional<Double>> stack) {
        final Optional<String> optValue = value.eval();
        if (optValue.isPresent()) {
            String evaluated = optValue.get();
            if (operations.containsKey(evaluated)) {
                return Optional.of(operations.get(evaluated).apply(stack.pop().get(), stack.pop().get()));
            }
            return Optional.of(Double.valueOf(evaluated));
        }
        throw new CyclicDependencyException();
    }

}