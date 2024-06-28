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

import com.google.common.base.CaseFormat;
import com.google.common.base.Function;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.tika.Tika;
import reactor.netty.http.client.HttpClientForm;

import javax.annotation.Nullable;
import java.beans.Introspector;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class MultipartRequestBodyUtils {

    private final Tika TIKA = new Tika();

    public void addRequestToClientForm(HttpClientForm httpClientForm,
                                              Object request) {
        addRequestToClientForm(httpClientForm, request, Collections.emptyMap());

    }

    public void addRequestToClientForm(HttpClientForm httpClientForm,
                                       Object request,
                                       Map<String, Function<Object, String>> customProcessors) {
        httpClientForm.multipart(true);
        var map = introspect(request);
        map.forEach((key, value) -> {
            if (customProcessors.containsKey(key)) {
                httpClientForm.attr(key, customProcessors.get(key).apply(value));
                return;
            }
            addKeyValue(httpClientForm, key, value);
        });

    }

    public void addKeyValue(HttpClientForm httpClientForm, String key, @Nullable Object value) {
        if (value == null) {
            return;
        }
        if (value instanceof File file) {
            httpClientForm.file(key, file.getName(), file, getMimeType(file));
        } else if (value instanceof Collection<?> collection) {
            var joinedValue = collection.stream()
                    .map(MultipartRequestBodyUtils::valueToString)
                    .collect(Collectors.joining(","));
            httpClientForm.attr(key, joinedValue);
        } else if (value.getClass().isArray()) {
            addArray(httpClientForm, key, value);
        } else {
            httpClientForm.attr(key, valueToString(value));
        }
    }

    public String valueToString(Object value) {
        if (value instanceof Enum<?> enumValue) {
            return enumValue.name().toLowerCase(Locale.ROOT);
        } else {
            return value.toString();
        }
    }

    public void addArray(HttpClientForm httpClientForm, String key, @Nullable Object value) {
        if (value == null) {
            return;
        }
        List<String> strings = new ArrayList<>();
        if (value instanceof int[] intArray) {
            for (var v : intArray) {
                strings.add(String.valueOf(v));
            }
        } else if (value instanceof long[] longArray) {
            for (var v : longArray) {
                strings.add(String.valueOf(v));
            }
        } else if (value instanceof double[] doubleArray) {
            for (var v : doubleArray) {
                strings.add(String.valueOf(v));
            }
        } else if (value instanceof float[] floatArray) {
            for (var v : floatArray) {
                strings.add(String.valueOf(v));
            }
        } else if (value instanceof boolean[] booleanArray) {
            for (var v : booleanArray) {
                strings.add(String.valueOf(v));
            }
        } else if (value instanceof char[] charArray) {
            for (var v : charArray) {
                strings.add(String.valueOf(v));
            }
        } else {
            for (var v : (Object[]) value) {
                strings.add(valueToString(v));
            }
        }
        httpClientForm.attr(key, String.join(",", strings));
    }

    @SneakyThrows
    private Map<String, Object> introspect(Object obj) {
        var result = new HashMap<String, Object>();
        var info = Introspector.getBeanInfo(obj.getClass());
        for (var pd : info.getPropertyDescriptors()) {
            if ("class".equals(pd.getName())) {
                continue;
            }
            var reader = pd.getReadMethod();
            if (reader != null) {
                var value = reader.invoke(obj);
                if (value != null) {
                    result.put(camelToSnake(pd.getName()), value);
                }
            }
        }
        return result;
    }

    private String camelToSnake(String camelCase) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCase);
    }

    private String getMimeType(File file) {
        return TIKA.detect(file.getName());
    }
}
