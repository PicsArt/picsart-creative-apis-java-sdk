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

package com.picsart.creativeapis.busobj.genai.result;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import com.picsart.creativeapis.busobj.image.Image;
import com.picsart.creativeapis.busobj.result.Metadata;
import com.picsart.creativeapis.busobj.result.ResultWithMetadata;

import java.util.List;

/**
 * This class represents the result of a text2Image operation in the GenAI API.
 * It includes the metadata and the generated images.
 */
@Getter
@Accessors(fluent = true)
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class Text2ImageResult extends ResultWithMetadata {
    List<Image> images;

    public Text2ImageResult(List<Image> images, Metadata metadata) {
        super(metadata);
        this.images = images;
    }

    @Override
    public String toString() {
        return "Text2ImageResult{" +
                "images=" + images +
                ", metadata=" + metadata() +
                '}';
    }

}
