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
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * This class represents the parameters for creating a surface map of an image. It includes
 * properties for the image source, a mask, a sticker, and the desired format of the image.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SurfaceMapParameters {
  /** The source of the image for which the surface map will be created. */
  ImageSource image;

  /** The mask to be applied to the image. */
  ImageSource mask;

  /** The sticker to be applied to the image. */
  ImageSource sticker;

  /** The desired format of the image after the surface map is created. This is optional. */
  @Nullable ImageFormat format;

  private static SurfaceMapParametersBuilder builder() {
    return new SurfaceMapParametersBuilder();
  }

  /**
   * Returns a new builder for SurfaceMapParameters with the specified image source, mask, and
   * sticker.
   *
   * @param image The source of the image for which the surface map will be created.
   * @param mask The mask to be applied to the image.
   * @param sticker The sticker to be applied to the image.
   * @return A new builder for SurfaceMapParameters.
   */
  public static SurfaceMapParametersBuilder builder(
      ImageSource image, ImageSource mask, ImageSource sticker) {
    return builder().image(image).mask(mask).sticker(sticker);
  }

  /** This class provides a builder for SurfaceMapParameters. */
  public static class SurfaceMapParametersBuilder {
    private ImageSource image;
    private ImageSource mask;
    private ImageSource sticker;

    private SurfaceMapParametersBuilder image(ImageSource image) {
      this.image = image;
      return this;
    }

    private SurfaceMapParametersBuilder mask(ImageSource mask) {
      this.mask = mask;
      return this;
    }

    private SurfaceMapParametersBuilder sticker(ImageSource sticker) {
      this.sticker = sticker;
      return this;
    }
  }
}
