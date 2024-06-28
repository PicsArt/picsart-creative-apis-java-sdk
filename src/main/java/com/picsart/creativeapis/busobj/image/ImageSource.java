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

package com.picsart.creativeapis.busobj.image;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.File;

/**
 * This class represents an image source.
 */
@Getter
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public sealed class ImageSource permits ImageUrl, ImageId, ImageFile {
    ImageSourceType type;


    public static ImageUrl fromUrl(@NonNull String url) {
        return new ImageUrl(url);
    }

    public static ImageId fromImageId(@NonNull String id) {
        return new ImageId(id);
    }

    public static ImageFile fromFile(@NonNull File file) {
        return new ImageFile(file);
    }

    public enum ImageSourceType {
        URL,
        ID,
        FILE
    }
}
