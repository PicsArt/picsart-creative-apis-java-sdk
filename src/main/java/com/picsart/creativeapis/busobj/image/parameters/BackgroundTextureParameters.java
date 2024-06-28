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
import org.checkerframework.common.value.qual.IntRange;
import com.picsart.creativeapis.busobj.image.BackgroundTexturePattern;
import com.picsart.creativeapis.busobj.image.ImageFormat;
import com.picsart.creativeapis.busobj.image.ImageSource;

import javax.annotation.Nullable;

/**
 * This class represents the parameters for generating a background texture for an image.
 * It includes properties for the image source, the desired format of the image, the dimensions of the texture,
 * the offset of the texture, the pattern of the texture, the rotation of the texture, and the scale of the texture.
 * Each property can be null except the image, which means that the api default values will be used for the 
 * corresponding parameter.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BackgroundTextureParameters {
    /**
     * The source of the image for which the background texture will be generated.
     */
    ImageSource image;

    /**
     * The desired format of the image after the background texture is generated. This is optional.
     */
    @Nullable
    ImageFormat format;

    /**
     * The desired width of the texture. This is optional.
     * Must be in the range [1, 8000].
     */
    @Nullable
    @IntRange(from = 1, to = 8000)
    Integer width;

    /**
     * The desired height of the texture. This is optional.
     * Must be in the range [1, 8000].
     */
    @Nullable
    @IntRange(from = 1, to = 8000)
    Integer height;

    /**
     * The desired horizontal offset of the texture. This is optional.
     */
    @Nullable
    Integer offsetX;

    /**
     * The desired vertical offset of the texture. This is optional.
     */
    @Nullable
    Integer offsetY;

    /**
     * The desired pattern of the texture. This is optional.
     */
    @Nullable
    BackgroundTexturePattern pattern;

    /**
     * The desired rotation of the texture. This is optional.
     * Must be in the range [-180, 180].
     */
    @Nullable
    @IntRange(from = -180, to = 180)
    Integer rotate;

    /**
     * The desired scale of the texture. This is optional.
     */
    @Nullable
    Float scale;

    /**
     * Returns a new builder for BackgroundTextureParameters with the specified image source.
     * @param image The source of the image for which the background texture will be generated.
     * @return A new builder for BackgroundTextureParameters.
     */
    public static BackgroundTextureParametersBuilder builder(ImageSource image) {
        return builder().image(image);
    }

    private static BackgroundTextureParametersBuilder builder() {
        return new BackgroundTextureParametersBuilder();
    }

    public static class BackgroundTextureParametersBuilder {
        private ImageSource image;

        private BackgroundTextureParametersBuilder image(@NonNull ImageSource image) {
            this.image = image;
            return this;
        }
    }
}
