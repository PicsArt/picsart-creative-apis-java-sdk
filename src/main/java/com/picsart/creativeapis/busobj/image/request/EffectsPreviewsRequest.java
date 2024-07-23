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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.File;
import java.util.List;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EffectsPreviewsRequest extends RequestWithImageAndFormat {

  @NotEmpty(message = "At least one effect name must be set")
  @Size(max = 10, message = "Maximum 10 effect names are allowed")
  List<String> effectNames;

  @Min(value = 1, message = "Preview size must be greater than 0")
  @Nullable
  Integer previewSize;

  public EffectsPreviewsRequest(
      @Nullable String imageId,
      @Nullable String imageUrl,
      @Nullable File image,
      @Nullable ImageFormat format,
      List<String> effectNames,
      @Nullable Integer previewSize) {
    super(imageId, imageUrl, image, format);
    this.effectNames = effectNames;
    this.previewSize = previewSize;
  }

  @Override
  public String toString() {
    return "EffectsPreviewsRequest{"
        + "effectNames="
        + effectNames
        + ", previewSize="
        + previewSize
        + ", imageId='"
        + imageId
        + '\''
        + ", imageUrl='"
        + imageUrl
        + '\''
        + ", image="
        + image
        + ", format="
        + format
        + '}';
  }
}
