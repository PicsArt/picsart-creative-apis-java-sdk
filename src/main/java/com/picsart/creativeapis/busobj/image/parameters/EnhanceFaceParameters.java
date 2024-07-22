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

/**
 * This class represents the parameters for enhancing a face in an image. It includes properties for
 * the image source and the desired format of the image. The format can be null, which means that
 * the api default values will be used for the corresponding parameter.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnhanceFaceParameters {
  /** The source of the image in which the face will be enhanced. */
  ImageSource image;

  /** The desired format of the image after the face is enhanced. This is optional. */
  @Nullable ImageFormat format;

  /**
   * Returns a new builder for EnhanceFaceParameters with the specified image source.
   *
   * @param image The source of the image in which the face will be enhanced.
   * @return A new builder for EnhanceFaceParameters.
   */
  public static EnhanceFaceParametersBuilder builder(ImageSource image) {
    return builder().image(image);
  }

  private static EnhanceFaceParametersBuilder builder() {
    return new EnhanceFaceParametersBuilder();
  }

  /** This class provides a builder for EnhanceFaceParameters. */
  public static class EnhanceFaceParametersBuilder {
    private ImageSource image;

    private EnhanceFaceParametersBuilder image(@NonNull ImageSource image) {
      this.image = image;
      return this;
    }
  }
}
