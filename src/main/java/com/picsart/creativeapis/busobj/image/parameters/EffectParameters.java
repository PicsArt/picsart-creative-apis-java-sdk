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

package com.picsart.creativeapis.busobj.image.parameters;

import com.picsart.creativeapis.busobj.image.ImageFormat;
import com.picsart.creativeapis.busobj.image.ImageSource;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import javax.annotation.Nullable;

/**
 * This class represents the parameters for applying an effect to an image.
 * It includes properties for the image source, the name of the effect, and the desired format of the image.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EffectParameters {
    /**
     * The source of the image to which the effect will be applied.
     */
    ImageSource image;

    /**
     * The name of the effect to be applied.
     */
    String effectName;

    /**
     * The desired format of the image after the effect is applied. This is optional.
     */
    @Nullable
    ImageFormat format;

    private static EffectParametersBuilder builder() {
        return new EffectParametersBuilder();
    }

    /**
     * Returns a new builder for EffectParameters with the specified image source and effect name.
     *
     * @param image      The source of the image to which the effect will be applied.
     * @param effectName The name of the effect to be applied.
     * @return A new builder for EffectParameters.
     */
    public static EffectParametersBuilder builder(ImageSource image, String effectName) {
        return builder().image(image).effectName(effectName);
    }

    /**
     * This class provides a builder for EffectParameters.
     */
    public static class EffectParametersBuilder {
        private ImageSource image;
        private String effectName;

        private EffectParametersBuilder image(@NonNull ImageSource image) {
            this.image = image;
            return this;
        }

        private EffectParametersBuilder effectName(@NonNull String effectName) {
            this.effectName = effectName;
            return this;
        }
    }

}
