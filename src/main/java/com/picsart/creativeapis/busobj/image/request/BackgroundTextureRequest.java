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

package com.picsart.creativeapis.busobj.image.request;

import com.picsart.creativeapis.busobj.image.ImageFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import com.picsart.creativeapis.busobj.image.BackgroundTexturePattern;

import javax.annotation.Nullable;
import java.io.File;

@Getter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BackgroundTextureRequest extends RequestWithImageAndFormat {

    @Nullable
    @Min(value = 1, message = "Width must be greater than 0")
    @Max(value = 8000, message = "Width must be less than 8000")
    Integer width;

    @Nullable
    @Min(value = 1, message = "Height must be greater than 0")
    @Max(value = 8000, message = "Height must be less than 8000")
    Integer height;

    @Nullable
    Integer offsetX;

    @Nullable
    Integer offsetY;

    @Nullable
    BackgroundTexturePattern pattern;

    @Nullable
    @Min(value = -180, message = "Rotate must be in range [-180, 180]")
    @Max(value = 180, message = "Rotate must be in range [-180, 180]")
    Integer rotate;

    @Nullable
    Float scale;

    public BackgroundTextureRequest(@Nullable String imageId,
                                    @Nullable String imageUrl,
                                    @Nullable File image,
                                    @Nullable ImageFormat format,
                                    @Nullable Integer width,
                                    @Nullable Integer height,
                                    @Nullable Integer offsetX,
                                    @Nullable Integer offsetY,
                                    @Nullable BackgroundTexturePattern pattern,
                                    @Nullable Integer rotate,
                                    @Nullable Float scale) {
        super(imageId, imageUrl, image, format);
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.pattern = pattern;
        this.rotate = rotate;
        this.scale = scale;
    }

    @AssertTrue(message = "Scale must be in range [0.5, 10]")
    private boolean isScaleValid() {
        return scale == null || (scale >= 0.5 && scale <= 10);
    }

    @Override
    public String toString() {
        return "BackgroundTextureRequest{" +
                "width=" + width +
                ", height=" + height +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                ", pattern=" + pattern +
                ", rotate=" + rotate +
                ", scale=" + scale +
                ", imageId='" + imageId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", image=" + image +
                ", format=" + format +
                '}';
    }
}
