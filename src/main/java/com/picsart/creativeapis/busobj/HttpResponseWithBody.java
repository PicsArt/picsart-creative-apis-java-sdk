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

package com.picsart.creativeapis.busobj;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import reactor.netty.http.client.HttpClientResponse;

/**
 * This class represents an HTTP response with a body of type T.
 * It includes properties for the HttpClientResponse and the body of the response.
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class HttpResponseWithBody<T> {
    /**
     * The HttpClientResponse of the HTTP response.
     */
    HttpClientResponse httpClientResponse;

    /**
     * The body of the HTTP response.
     */
    T body;

    /**
     * Returns a new HttpResponseWithBody with the specified HttpClientResponse and body.
     * @param httpClientResponse The HttpClientResponse of the HTTP response.
     * @param body The body of the HTTP response.
     * @return A new HttpResponseWithBody.
     */
    public static <T> HttpResponseWithBody<T> of(HttpClientResponse httpClientResponse, T body) {
        return new HttpResponseWithBody<>(httpClientResponse, body);
    }
}