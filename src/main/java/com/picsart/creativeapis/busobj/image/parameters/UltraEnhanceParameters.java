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
import org.checkerframework.common.value.qual.IntRange;

import javax.annotation.Nullable;

/**
 * This class represents the parameters for enhancing an image with ultra quality.
 * It includes properties for the image source, the upscale factor, and the desired format of the image.
 * The upscale factor and the format can be null, which means that the api default values will be used for the 
 * corresponding parameter.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UltraEnhanceParameters {
    /**
     * The source of the image to be enhanced.
     */
    ImageSource image;

    /**
     * The upscale factor for the enhancement. This is optional.
     * Must be between 2 and 16, inclusive.
     */
    @IntRange(from = 2, to = 16)
    @Nullable
    Integer upscaleFactor;

    /**
     * The desired format of the image after enhancement. This is optional.
     */
    @Nullable
    ImageFormat format;

    /**
     * Returns a new builder for UltraEnhanceParameters with the specified image source.
     *
     * @param image The source of the image to be enhanced.
     * @return A new builder for UltraEnhanceParameters.
     */
    public static UltraEnhanceParametersBuilder builder(ImageSource image) {
        return builder().image(image);
    }

    private static UltraEnhanceParametersBuilder builder() {
        return new UltraEnhanceParametersBuilder();
    }

    /**
     * This class provides a builder for UltraEnhanceParameters.
     */
    public static class UltraEnhanceParametersBuilder {
        private ImageSource image;

        private UltraEnhanceParametersBuilder image(@NonNull ImageSource image) {
            this.image = image;
            return this;
        }
    }
}
