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
import com.picsart.creativeapis.utils.MultipartRequestBodyUtils;
import com.picsart.creativeapis.utils.ValidationUtils;
import jakarta.validation.constraints.AssertTrue;
import java.io.File;
import java.util.Map;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import reactor.netty.http.client.HttpClientForm;

@Getter
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class SurfaceMapRequest extends RequestWithImageAndFormat {
  @Nullable File mask;

  @Nullable String maskUrl;

  @Nullable String maskId;

  @Nullable File sticker;

  @Nullable String stickerUrl;

  @Nullable String stickerId;

  public SurfaceMapRequest(
      @Nullable String imageId,
      @Nullable String imageUrl,
      @Nullable File image,
      @Nullable ImageFormat format,
      @Nullable File mask,
      @Nullable String maskUrl,
      @Nullable String maskId,
      @Nullable File sticker,
      @Nullable String stickerUrl,
      @Nullable String stickerId) {
    super(imageId, imageUrl, image, format);
    this.mask = mask;
    this.maskUrl = maskUrl;
    this.maskId = maskId;
    this.sticker = sticker;
    this.stickerUrl = stickerUrl;
    this.stickerId = stickerId;
  }

  @AssertTrue(message = "Exactly one mask source must be set")
  public boolean isMaskValid() {
    return ValidationUtils.onlyOneNotEmpty(maskId, maskUrl, mask);
  }

  @AssertTrue(message = "Exactly one sticker source must be set")
  public boolean isStickerValid() {
    return ValidationUtils.onlyOneNotEmpty(stickerId, stickerUrl, sticker);
  }

  @Override
  public String toString() {
    return "SurfaceMapRequest{"
        + "imageId="
        + imageId
        + ", imageUrl="
        + imageUrl
        + ", image="
        + image
        + ", format="
        + format
        + ", mask="
        + mask
        + ", maskUrl="
        + maskUrl
        + ", maskId="
        + maskId
        + ", sticker="
        + sticker
        + ", stickerUrl="
        + stickerUrl
        + ", stickerId="
        + stickerId
        + '}';
  }

  // TODO: remove when issue with lowercase format will be fixed
  @Override
  public void addBodyToForm(HttpClientForm form) {
    MultipartRequestBodyUtils.addRequestToClientForm(
        form, this, Map.of("format", Object::toString));
  }
}
