/*
 * MIT License
 *
 * Copyright (c) 2024 PicsArt, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.picsart.creativeapis.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ValidationUtils {
    private final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
    private final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

    private Set<ConstraintViolation<Object>> validate(Object object) {
        return VALIDATOR.validate(object);
    }

    public void validOrThrow(Object object, String actionName) {
        var validate = validate(object);
        if (!validate.isEmpty()) {
            throw new IllegalArgumentException(
                    actionName + " failed with errors: " +
                    validate.stream()
                            .map(ConstraintViolation::getMessage)
                            .sorted()
                            .collect(Collectors.joining(", "))
            );
        }
    }

    public Mono<Void> validateRequestMono(Object object, String actionName) {
        return Mono.fromRunnable(() -> validOrThrow(object, actionName));
    }

    public boolean onlyOneNotEmpty(Object... objects) {
        return getNonEmptyObjectsCount(objects) == 1;
    }


    public boolean atMostOneNotEmpty(Object... objects) {
        return getNonEmptyObjectsCount(objects) <= 1;
    }

    private int getNonEmptyObjectsCount(Object[] objects) {
        var count = 0;
        for (var object : objects) {
            if (object != null) {
                if (object instanceof String str) {
                    if (!str.isBlank()) {
                        count++;
                    }
                } else {
                    count++;
                }
            }
        }
        return count;
    }
}
