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

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import com.picsart.creativeapis.busobj.image.ImageFormat;
import com.picsart.creativeapis.busobj.image.OutputType;
import com.picsart.creativeapis.busobj.image.Scale;
import com.picsart.creativeapis.utils.ValidationUtils;

import javax.annotation.Nullable;
import java.io.File;


@Getter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RemoveBackgroundRequest extends RequestWithImageAndFormat {

    @Nullable
    OutputType outputType;

    @Nullable
    String bgImageId;

    @Nullable
    String bgImageUrl;

    @Nullable
    File bgImage;

    @Nullable
    String bgColor;

    @Min(value = 0, message = "Blur must be in range [0, 100]")
    @Max(value = 100, message = "Blur must be in range [0, 100]")
    @Nullable
    Integer bgBlur;

    @Nullable
    Integer bgWidth;

    @Nullable
    Integer bgHeight;

    @Nullable
    Scale scale;

    @Nullable
    Boolean autoCenter;

    @Min(value = 0, message = "Stroke size must be in range [0, 100]")
    @Max(value = 100, message = "Stroke size must be in range [0, 100]")
    @Nullable
    Integer strokeSize;

    @Nullable
    String strokeColor;

    @Min(value = 0, message = "Stroke opacity must be in range [0, 100]")
    @Max(value = 100, message = "Stroke opacity must be in range [0, 100]")
    @Nullable
    Integer strokeOpacity;

    public RemoveBackgroundRequest(@Nullable String imageId,
                                   @Nullable String imageUrl,
                                   @Nullable File image,
                                   @Nullable ImageFormat format,
                                   @Nullable OutputType outputType,
                                   @Nullable String bgImageId,
                                   @Nullable String bgImageUrl,
                                   @Nullable File bgImage,
                                   @Nullable String bgColor,
                                   @Nullable Integer bgBlur,
                                   @Nullable Integer bgWidth,
                                   @Nullable Integer bgHeight,
                                   @Nullable Scale scale,
                                   @Nullable Boolean autoCenter,
                                   @Nullable Integer strokeSize,
                                   @Nullable String strokeColor,
                                   @Nullable Integer strokeOpacity
    ) {
        super(imageId, imageUrl, image, format);
        this.outputType = outputType;
        this.bgImageId = bgImageId;
        this.bgImageUrl = bgImageUrl;
        this.bgImage = bgImage;
        this.bgColor = bgColor;
        this.bgBlur = bgBlur;
        this.bgWidth = bgWidth;
        this.bgHeight = bgHeight;
        this.scale = scale;
        this.autoCenter = autoCenter;
        this.strokeSize = strokeSize;
        this.strokeColor = strokeColor;
        this.strokeOpacity = strokeOpacity;
    }

    @AssertTrue(message = "Only one bg image source can be set")
    private boolean isBgImageSetCorrectly() {
        return ValidationUtils.atMostOneNotEmpty(bgImageId, bgImageUrl, bgImage);
    }

    @Override
    public String toString() {
        return "RemoveBackgroundRequest{" +
                "outputType=" + outputType +
                ", bgImageId='" + bgImageId + '\'' +
                ", bgImageUrl='" + bgImageUrl + '\'' +
                ", bgImage=" + bgImage +
                ", bgColor='" + bgColor + '\'' +
                ", bgBlur=" + bgBlur +
                ", bgWidth=" + bgWidth +
                ", bgHeight=" + bgHeight +
                ", scale=" + scale +
                ", autoCenter=" + autoCenter +
                ", strokeSize=" + strokeSize +
                ", strokeColor='" + strokeColor + '\'' +
                ", strokeOpacity=" + strokeOpacity +
                ", imageId='" + imageId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", image=" + image +
                ", format=" + format +
                '}';
    }
}
