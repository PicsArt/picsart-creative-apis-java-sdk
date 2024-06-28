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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.picsart.creativeapis.busobj.genai.parameters.Text2ImageParameters;
import com.picsart.creativeapis.PicsartEnterprise;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
public class GenAIApiIntegrationTest {

    private static final String API_KEY = System.getProperty("API_KEY");

    private GenAIApi genAIApi;

    @BeforeEach
    public void setup() {
        Hooks.onOperatorDebug();
        genAIApi = PicsartEnterprise.createGenAIApi(API_KEY);
    }

    @DisplayName("Should return Text2ImageResult when text2Image is called with valid parameters")
    @Test
    public void text2ImageSmokeTest() {
        var text2ImageResultMono = genAIApi.text2Image(Text2ImageParameters.builder("cats vs dogs", "fighting")
                .count(1)
                .height(64)
                .width(64)
                .build());

        // Then
        StepVerifier.create(text2ImageResultMono)
                .expectNextMatches(response -> response.images().size() == 1)
                .verifyComplete();
    }
}
