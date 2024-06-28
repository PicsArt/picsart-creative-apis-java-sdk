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

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import com.picsart.creativeapis.busobj.image.ImageFormat;
import com.picsart.creativeapis.busobj.image.ImageSource;

import javax.annotation.Nullable;

/**
 * This class represents the parameters for upscaling an image.
 * It includes properties for the image source, the upscale factor, and the desired format of the image.
 * Each property can be null except the image, which means that the api default values will be used for the
 * corresponding parameter.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UpscaleParameters {
    /**
     * The source of the image to be upscaled.
     */
    ImageSource image;

    /**
     * The upscale factor for the upscaling. This is optional.
     * Allowed values: 2, 4, 8, 16.
     */
    @Nullable
    Integer upscaleFactor;

    /**
     * The desired format of the image after upscaling. This is optional.
     */
    @Nullable
    ImageFormat format;

    /**
     * Returns a new builder for UpscaleParameters with the specified image source.
     * @param image The source of the image to be upscaled.
     * @return A new builder for UpscaleParameters.
     */
    public static UpscaleParametersBuilder builder(ImageSource image) {
        return builder().image(image);
    }

    private static UpscaleParametersBuilder builder() {
        return new UpscaleParametersBuilder();
    }

    /**
     * This class provides a builder for UpscaleParameters.
     */
    public static class UpscaleParametersBuilder {
        private ImageSource image;

        private UpscaleParametersBuilder image(@NonNull ImageSource image) {
            this.image = image;
            return this;
        }
    }
}
