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
import com.picsart.creativeapis.busobj.image.OutputType;
import com.picsart.creativeapis.busobj.image.Scale;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.checkerframework.common.value.qual.IntRange;

import javax.annotation.Nullable;


/**
 * This class represents the parameters for removing the background from an image.
 * It includes properties for the image source, the output type, the background image, the background color,
 * the background blur, the background width, the background height, the scale, the auto center, the stroke size, the
 * stroke color, the stroke opacity, and the format.
 * Each property can be null except the image, which means that the api default values will be used for the 
 * corresponding parameter.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RemoveBackgroundParameters {
    /**
     * The source of the image from which the background will be removed.
     */
    ImageSource image;

    /**
     * The output type of the image after the background is removed. This is optional.
     */
    @Nullable
    OutputType outputType;

    /**
     * The background image to be used after the original background is removed. This is optional.
     */
    @Nullable
    ImageSource bgImage;

    /**
     * The background color to be used after the original background is removed. This is optional.
     */
    @Nullable
    String bgColor;

    /**
     * The blur level of the background. This is optional.
     */
    @IntRange(from = 0, to = 100)
    @Nullable
    Integer bgBlur;

    /**
     * The width of the background. This is optional.
     */
    @Nullable
    Integer bgWidth;

    /**
     * The height of the background. This is optional.
     */
    @Nullable
    Integer bgHeight;

    /**
     * The scale of the image. This is optional.
     */
    @Nullable
    Scale scale;

    /**
     * Whether to auto center the image. This is optional.
     */
    @Nullable
    Boolean autoCenter;

    /**
     * The size of the stroke. This is optional.
     */
    @IntRange(from = 0, to = 100)
    @Nullable
    Integer strokeSize;

    /**
     * The color of the stroke. This is optional.
     */
    @Nullable
    String strokeColor;

    /**
     * The opacity of the stroke. This is optional.
     */
    @IntRange(from = 0, to = 100)
    @Nullable
    Integer strokeOpacity;

    /**
     * The format of the image. This is optional.
     */
    @Nullable
    ImageFormat format;

    /**
     * Returns a new builder for RemoveBackgroundParameters with the specified image source.
     *
     * @param image The source of the image from which the background will be removed.
     * @return A new builder for RemoveBackgroundParameters.
     */
    public static RemoveBackgroundParametersBuilder builder(ImageSource image) {
        return builder().image(image);
    }

    private static RemoveBackgroundParametersBuilder builder() {
        return new RemoveBackgroundParametersBuilder();
    }

    /**
     * This class provides a builder for RemoveBackgroundParameters.
     */
    public static class RemoveBackgroundParametersBuilder {
        private ImageSource image;

        private RemoveBackgroundParametersBuilder image(@NonNull ImageSource image) {
            this.image = image;
            return this;
        }
    }

}
