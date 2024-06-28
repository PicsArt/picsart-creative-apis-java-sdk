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

package com.picsart.creativeapis.busobj.genai.parameters;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.annotation.Nullable;


/**
 * This class represents the parameters for the Text2Image operation in the GenAI API.
 * It includes properties for the prompt, negative prompt, width, height, and count of images to generate.
 */
@Data
@Builder
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class Text2ImageParameters {
    /**
     * The prompt to generate the image from.
     */
    String prompt;

    /**
     * The negative prompt to generate the image from.
     */
    String negativePrompt;

    /**
     * The width of the image to generate. This is optional.
     */
    @Nullable
    Integer width;

    /**
     * The height of the image to generate. This is optional.
     */
    @Nullable
    Integer height;

    /**
     * The number of images to generate. This is optional.
     */
    @Nullable
    Integer count;

    /**
     * Returns a new builder for Text2ImageParameters with the specified prompt and negative prompt.
     * @param prompt The prompt to generate the image from.
     * @param negativePrompt The negative prompt to generate the image from.
     * @return A new builder for Text2ImageParameters.
     */
    public static Text2ImageParameters.Text2ImageParametersBuilder builder(String prompt, String negativePrompt) {
        return builder().prompt(prompt).negativePrompt(negativePrompt);
    }

    private static Text2ImageParameters.Text2ImageParametersBuilder builder() {
        return new Text2ImageParameters.Text2ImageParametersBuilder();
    }

    public static class Text2ImageParametersBuilder {
        private String prompt;
        private String negativePrompt;

        private Text2ImageParameters.Text2ImageParametersBuilder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        private Text2ImageParameters.Text2ImageParametersBuilder negativePrompt(String negativePrompt) {
            this.negativePrompt = negativePrompt;
            return this;
        }
    }
}
