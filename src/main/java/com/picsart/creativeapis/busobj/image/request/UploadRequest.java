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

import com.picsart.creativeapis.busobj.MultipartBodyRequest;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import com.picsart.creativeapis.utils.ValidationUtils;

import javax.annotation.Nullable;
import java.io.File;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UploadRequest implements MultipartBodyRequest {
    @Nullable
    File image;

    @Nullable
    String imageUrl;

    // only one of fields should be set
    @AssertTrue(message = "Exactly one image source must be set")
    boolean isImageSetCorrectly() {
        return ValidationUtils.onlyOneNotEmpty(image, imageUrl);
    }
}
