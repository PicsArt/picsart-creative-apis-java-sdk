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

import com.picsart.creativeapis.utils.JacksonUtils;
import reactor.netty.http.client.HttpClientResponse;

/**
 * This class represents an HTTP response with a body of type String.
 * It extends the HttpResponseWithBody class and includes methods for creating a new HttpResponseWithStringBody and
 * parsing the body of the response.
 */
public class HttpResponseWithStringBody extends HttpResponseWithBody<String> {
    /**
     * Constructs a new HttpResponseWithStringBody with the specified HttpClientResponse and body.
     *
     * @param httpClientResponse The HttpClientResponse of the HTTP response.
     * @param body               The body of the HTTP response.
     */
    protected HttpResponseWithStringBody(HttpClientResponse httpClientResponse, String body) {
        super(httpClientResponse, body);
    }

    /**
     * Returns a new HttpResponseWithStringBody with the specified HttpClientResponse and body.
     *
     * @param httpClientResponse The HttpClientResponse of the HTTP response.
     * @param body               The body of the HTTP response.
     * @return A new HttpResponseWithStringBody.
     */
    public static HttpResponseWithStringBody of(HttpClientResponse httpClientResponse, String body) {
        return new HttpResponseWithStringBody(httpClientResponse, body);
    }

    /**
     * Parses the body of the HTTP response and returns a new HttpResponseWithBody with the parsed body.
     * The body is parsed from a JSON string to an object of the specified class.
     *
     * @param clazz The class of the object to parse the body to.
     * @return A new HttpResponseWithBody with the parsed body.
     */
    public <T> HttpResponseWithBody<T> parseBody(Class<T> clazz) {
        return of(getHttpClientResponse(), JacksonUtils.fromJson(getBody(), clazz));
    }
}