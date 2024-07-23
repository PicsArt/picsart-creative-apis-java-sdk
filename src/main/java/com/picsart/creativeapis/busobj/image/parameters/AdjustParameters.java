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
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.checkerframework.common.value.qual.IntRange;

/**
 * This class represents the parameters for adjusting an image. It includes properties for
 * brightness, contrast, clarity, saturation, hue, shadows, highlights, temperature, sharpen, noise,
 * and vignette. Each property except image can be null, which means that the api default values
 * will be used for the corresponding parameter.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdjustParameters {
  /** The source of the image to be adjusted. */
  ImageSource image;

  /** The desired format of the adjusted image. This is optional. */
  @Nullable ImageFormat format;

  /**
   * The desired brightness of the adjusted image. This is optional. Must be in the range [-100,
   * 100].
   */
  @Nullable
  @IntRange(from = -100, to = 100)
  Integer brightness;

  /**
   * The desired contrast of the adjusted image. This is optional. Must be in the range [-100, 100].
   */
  @Nullable
  @IntRange(from = -100, to = 100)
  Integer contrast;

  /**
   * The desired clarity of the adjusted image. This is optional. Must be in the range [-100, 100].
   */
  @Nullable
  @IntRange(from = -100, to = 100)
  Integer clarity;

  /**
   * The desired saturation of the adjusted image. This is optional. Must be in the range [-100,
   * 100].
   */
  @Nullable
  @IntRange(from = -100, to = 100)
  Integer saturation;

  /** The desired hue of the adjusted image. This is optional. Must be in the range [-100, 100]. */
  @Nullable
  @IntRange(from = -100, to = 100)
  Integer hue;

  /**
   * The desired shadows of the adjusted image. This is optional. Must be in the range [-100, 100].
   */
  @Nullable
  @IntRange(from = -100, to = 100)
  Integer shadows;

  /**
   * The desired highlights of the adjusted image. This is optional. Must be in the range [-100,
   * 100].
   */
  @Nullable
  @IntRange(from = -100, to = 100)
  Integer highlights;

  /**
   * The desired temperature of the adjusted image. This is optional. Must be in the range [-100,
   * 100].
   */
  @Nullable
  @IntRange(from = -100, to = 100)
  Integer temperature;

  /** The desired sharpen of the adjusted image. This is optional. Must be in the range [0, 100]. */
  @Nullable
  @IntRange(from = 0, to = 100)
  Integer sharpen;

  /** The desired noise of the adjusted image. This is optional. Must be in the range [0, 100]. */
  @Nullable
  @IntRange(from = 0, to = 100)
  Integer noise;

  /**
   * The desired vignette of the adjusted image. This is optional. Must be in the range [0, 100].
   */
  @Nullable
  @IntRange(from = 0, to = 100)
  Integer vignette;

  /**
   * Returns a new builder for AdjustParameters with the specified image source.
   *
   * @param image The source of the image to be adjusted.
   * @return A new builder for AdjustParameters.
   */
  public static AdjustParametersBuilder builder(ImageSource image) {
    return builder().image(image);
  }

  private static AdjustParametersBuilder builder() {
    return new AdjustParametersBuilder();
  }

  /** This class provides a builder for AdjustParameters. */
  public static class AdjustParametersBuilder {
    private ImageSource image;

    private AdjustParametersBuilder image(@NonNull ImageSource image) {
      this.image = image;
      return this;
    }
  }
}
