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

package com.picsart.creativeapis.busobj.genai.config;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.Duration;

import static com.picsart.creativeapis.utils.Constants.*;

/**
 * This class represents the configuration for the GenAI API client.
 * It includes properties for the text2Image polling repeat count, text2Image polling repeat delay, and text2Image
 * polling first delay.
 */
@Builder
@Data
@Accessors(fluent = true)
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class GenAIApiClientConfig {
    /**
     * The default configuration for the GenAI API client.
     */
    public static final GenAIApiClientConfig DEFAULT = GenAIApiClientConfig.builder().build();

    /**
     * The number of times to repeat the text2Image polling. Defaults to TEXT_2_IMAGE_POLLING_REPEAT_COUNT.
     */
    @Builder.Default
    int text2ImagePollingRepeatCount = TEXT_2_IMAGE_POLLING_REPEAT_COUNT;

    /**
     * The delay between each text2Image polling repeat. Defaults to TEXT_2_IMAGE_POLLING_REPEAT_DELAY.
     */
    @Builder.Default
    Duration text2ImagePollingRepeatDelay = TEXT_2_IMAGE_POLLING_REPEAT_DELAY;

    /**
     * The initial delay before starting the text2Image polling. Defaults to TEXT_2_IMAGE_POLLING_FIRST_DELAY.
     */
    @Builder.Default
    Duration text2ImagePollingFirstDelay = TEXT_2_IMAGE_POLLING_FIRST_DELAY;
}
