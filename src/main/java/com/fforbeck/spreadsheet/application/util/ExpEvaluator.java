package com.fforbeck.spreadsheet.application.util;

import java.util.Collection;
import java.util.Optional;

public interface ExpEvaluator<T, U> {
    Optional<U> evaluate(Collection<T> values);
}