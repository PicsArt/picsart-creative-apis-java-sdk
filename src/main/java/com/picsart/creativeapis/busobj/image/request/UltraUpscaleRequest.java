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
import com.picsart.creativeapis.busobj.image.UpscaleMode;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.io.File;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UltraUpscaleRequest extends RequestWithImageAndFormat {

  @Min(value = 2, message = "Upscale factor must be in range [2, 16]")
  @Max(value = 16, message = "Upscale factor must be in range [2, 16]")
  @Nullable
  Integer upscaleFactor;

  @Nullable UpscaleMode mode;

  public UltraUpscaleRequest(
      @Nullable String imageId,
      @Nullable String imageUrl,
      @Nullable File image,
      @Nullable ImageFormat format,
      @Nullable Integer upscaleFactor,
      @Nullable UpscaleMode mode) {
    super(imageId, imageUrl, image, format);
    this.upscaleFactor = upscaleFactor;
    this.mode = mode;
  }

  @Override
  public String toString() {
    return "UltraUpscaleRequest{"
        + "upscaleFactor="
        + upscaleFactor
        + ", mode="
        + mode
        + ", format="
        + format
        + ", image="
        + image
        + ", imageUrl='"
        + imageUrl
        + '\''
        + ", imageId='"
        + imageId
        + '\''
        + '}';
  }
}
