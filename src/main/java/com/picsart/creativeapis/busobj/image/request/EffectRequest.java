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

import jakarta.validation.constraints.NotBlank;
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
public class EffectRequest extends RequestWithImageAndFormat {

    @NotBlank(message = "Effect name must be set")
    String effectName;


    public EffectRequest(@Nullable String imageId,
                         @Nullable String imageUrl,
                         @Nullable File image,
                         @Nullable ImageFormat format,
                         String effectName) {
        super(imageId, imageUrl, image, format);
        this.effectName = effectName;
    }

    @Override
    public String toString() {
        return "EffectRequest{" +
                "effectName='" + effectName + '\'' +
                ", imageId='" + imageId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", image=" + image +
                ", format=" + format +
                '}';
    }
}
