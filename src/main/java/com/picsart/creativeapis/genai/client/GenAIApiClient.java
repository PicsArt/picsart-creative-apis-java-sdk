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

package com.picsart.creativeapis.genai.client;

import com.picsart.creativeapis.busobj.ApiConfig;
import com.picsart.creativeapis.busobj.HttpResponseWithBody;
import com.picsart.creativeapis.busobj.genai.request.Text2ImageRequest;
import com.picsart.creativeapis.busobj.genai.response.Text2ImageResponse;
import reactor.core.publisher.Mono;

/**
 * This interface represents the client for the GenAI API.
 * It includes methods for performing operations on the API, such as generating an image from text.
 */
public interface GenAIApiClient {
    /**
     * Generates an image from text using the GenAI API.
     * @param config The configuration for the API.
     * @param request The request for the Text2Image operation.
     * @return A Mono that emits the HTTP response from the API. The body of the response is a Text2ImageResponse.
     */
    Mono<HttpResponseWithBody<Text2ImageResponse>> text2Image(ApiConfig config,
                                                              Text2ImageRequest request);
}
