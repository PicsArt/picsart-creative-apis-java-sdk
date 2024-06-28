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

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import com.picsart.creativeapis.busobj.image.ImageFormat;

import javax.annotation.Nullable;
import java.io.File;

@Getter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UltraEnhanceRequest extends RequestWithImageAndFormat {

    @Min(value = 2, message = "Upscale factor must be be in range [2, 16]")
    @Max(value = 16, message = "Upscale factor must be be in range [2, 16]")
    @Nullable
    Integer upscaleFactor;


    public UltraEnhanceRequest(@Nullable String imageId,
                               @Nullable String imageUrl,
                               @Nullable File image,
                               @Nullable ImageFormat format,
                               @Nullable Integer upscaleFactor) {
        super(imageId, imageUrl, image, format);
        this.upscaleFactor = upscaleFactor;
    }

    @Override
    public String toString() {
        return "UltraEnhanceRequest{" +
                "upscaleFactor=" + upscaleFactor +
                ", format=" + format +
                ", image=" + image +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }
}
