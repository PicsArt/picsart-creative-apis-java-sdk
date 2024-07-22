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
import java.util.List;
import javax.annotation.Nullable;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * This class represents the parameters for generating previews of effects on an image. It includes
 * properties for the image source, a list of effect names, the desired size of the previews, and
 * the desired format of the previews. The size and format can be null, which means that the api
 * default values will be used for the corresponding parameters.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EffectsPreviewsParameters {
  /** The source of the image for which the previews will be generated. */
  ImageSource image;

  /**
   * The names of the effects for which previews will be generated. The list must contain at least
   * one effect name and at most 10 effect names.
   */
  @Singular("addEffectName")
  List<String> effectNames;

  /** The desired size of the previews. This is optional. */
  @Nullable Integer previewSize;

  /** The desired format of the previews. This is optional. */
  @Nullable ImageFormat format;

  private static EffectsPreviewsParametersBuilder builder() {
    return new EffectsPreviewsParametersBuilder();
  }

  /**
   * Returns a new builder for EffectsPreviewsParameters with the specified image source.
   *
   * @param image The source of the image for which the previews will be generated.
   * @return A new builder for EffectsPreviewsParameters.
   */
  public static EffectsPreviewsParametersBuilder builder(ImageSource image) {
    return builder().image(image);
  }

  /** This class provides a builder for EffectsPreviewsParameters. */
  public static class EffectsPreviewsParametersBuilder {
    private ImageSource image;

    private EffectsPreviewsParametersBuilder image(@NonNull ImageSource image) {
      this.image = image;
      return this;
    }
  }
}
