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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import com.picsart.creativeapis.busobj.image.ImageFormat;

import javax.annotation.Nullable;
import java.io.File;

@Getter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AdjustRequest extends RequestWithImageAndFormat {


    @Min(value = -100, message = "Brightness must be in range [-100, 100]")
    @Max(value = 100, message = "Brightness must be in range [-100, 100]")
    @Nullable
    Integer brightness;

    @Min(value = -100, message = "Contrast must be in range [-100, 100]")
    @Max(value = 100, message = "Contrast must be in range [-100, 100]")
    @Nullable
    Integer contrast;

    @Min(value = -100, message = "Clarity must be in range [-100, 100]")
    @Max(value = 100, message = "Clarity must be in range [-100, 100]")
    @Nullable
    Integer clarity;

    @Min(value = -100, message = "Saturation must be in range [-100, 100]")
    @Max(value = 100, message = "Saturation must be in range [-100, 100]")
    @Nullable
    Integer saturation;

    @Min(value = -100, message = "Hue must be in range [-100, 100]")
    @Max(value = 100, message = "Hue must be in range [-100, 100]")
    @Nullable
    Integer hue;

    @Min(value = -100, message = "Shadows must be in range [-100, 100]")
    @Max(value = 100, message = "Shadows must be in range [-100, 100]")
    @Nullable
    Integer shadows;

    @Min(value = -100, message = "Highlights must be in range [-100, 100]")
    @Max(value = 100, message = "Highlights must be in range [-100, 100]")
    @Nullable
    Integer highlights;

    @Min(value = -100, message = "Temperature must be in range [-100, 100]")
    @Max(value = 100, message = "Temperature must be in range [-100, 100]")
    @Nullable
    Integer temperature;

    @Min(value = 0, message = "Sharpen must be in range [0, 100]")
    @Max(value = 100, message = "Sharpen must be in range [0, 100]")
    @Nullable
    Integer sharpen;

    @Min(value = 0, message = "Noise must be in range [0, 100]")
    @Max(value = 100, message = "Noise must be in range [0, 100]")
    @Nullable
    Integer noise;

    @Min(value = 0, message = "Vignette must be in range [0, 100]")
    @Max(value = 100, message = "Vignette must be in range [0, 100]")
    @Nullable
    Integer vignette;


    public AdjustRequest(@Nullable String imageId,
                         @Nullable String imageUrl,
                         @Nullable File image,
                         @Nullable ImageFormat format,
                         @Nullable Integer brightness,
                         @Nullable Integer contrast,
                         @Nullable Integer clarity,
                         @Nullable Integer saturation,
                         @Nullable Integer hue,
                         @Nullable Integer shadows,
                         @Nullable Integer highlights,
                         @Nullable Integer temperature,
                         @Nullable Integer sharpen,
                         @Nullable Integer noise,
                         @Nullable Integer vignette) {
        super(imageId, imageUrl, image, format);
        this.brightness = brightness;
        this.contrast = contrast;
        this.clarity = clarity;
        this.saturation = saturation;
        this.hue = hue;
        this.shadows = shadows;
        this.highlights = highlights;
        this.temperature = temperature;
        this.sharpen = sharpen;
        this.noise = noise;
        this.vignette = vignette;
    }

    @Override
    public String toString() {
        return "AdjustRequest{" +
                "brightness=" + brightness +
                ", contrast=" + contrast +
                ", clarity=" + clarity +
                ", saturation=" + saturation +
                ", hue=" + hue +
                ", shadows=" + shadows +
                ", highlights=" + highlights +
                ", temperature=" + temperature +
                ", sharpen=" + sharpen +
                ", noise=" + noise +
                ", vignette=" + vignette +
                ", imageId='" + imageId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", image=" + image +
                ", format=" + format +
                '}';
    }
}
