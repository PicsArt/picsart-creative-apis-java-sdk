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

package com.picsart.creativeapis.genai;

import com.picsart.creativeapis.busobj.genai.parameters.Text2ImageParameters;
import com.picsart.creativeapis.busobj.genai.result.Text2ImageResult;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * This interface defines the operations that can be performed with the GenAI API.
 */
public sealed interface GenAIApi permits GenAIApiImpl {

    /**
     * Sets the API key to be used for requests.
     * @param apiKey The API key.
     * @return An instance of GenAIApi with the API key set.
     */
    GenAIApi withApiKey(String apiKey);

    /**
     * Sets the base URL for the API.
     * @param baseUrl The base URL.
     * @return An instance of GenAIApi with the base URL set.
     */
    GenAIApi withBaseUrl(String baseUrl);

    /**
     * Sets the response timeout for the API.
     * @param timeout The response timeout.
     * @return An instance of GenAIApi with the response timeout set.
     */
    GenAIApi withResponseTimeout(Duration timeout);

    /**
     * Generate an image based on the text provided.
     * @param request The parameters for the operation.
     * @return A Mono that emits the result of the operation.
     */
    Mono<Text2ImageResult> text2Image(Text2ImageParameters request);
}