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

package com.picsart.creativeapis.busobj.image.result;

import com.picsart.creativeapis.busobj.image.Image;
import com.picsart.creativeapis.busobj.result.Metadata;
import lombok.EqualsAndHashCode;

/**
 * This class represents the result of a background texture generation operation. It includes
 * properties for metadata, status, and the generated background texture image.
 */
@EqualsAndHashCode(callSuper = true)
public class BackgroundTextureResult extends ResultWithImageAndStatus {
  public BackgroundTextureResult(Metadata metadata, String status, Image image) {
    super(metadata, status, image);
  }

  @Override
  public String toString() {
    return "BackgroundTextureResult{"
        + "status='"
        + status
        + '\''
        + ", image="
        + image
        + ", metadata="
        + metadata
        + '}';
  }
}
