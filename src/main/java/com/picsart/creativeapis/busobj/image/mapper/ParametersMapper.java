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

package com.picsart.creativeapis.busobj.image.mapper;

import com.picsart.creativeapis.busobj.image.parameters.*;
import com.picsart.creativeapis.busobj.image.request.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;
import com.picsart.creativeapis.busobj.image.ImageFile;
import com.picsart.creativeapis.busobj.image.ImageId;
import com.picsart.creativeapis.busobj.image.ImageSource;
import com.picsart.creativeapis.busobj.image.ImageUrl;

import java.io.File;

/**
 * This interface provides methods for mapping image api parameters to requests.
 * It uses the MapStruct library to generate the implementation of these methods.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ParametersMapper {
    ParametersMapper INSTANCE = Mappers.getMapper(ParametersMapper.class);

    @Mapping(target = "image", source = "image", qualifiedByName = "toImageFile")
    @Mapping(target = "imageId", source = "image", qualifiedByName = "toImageId")
    @Mapping(target = "imageUrl", source = "image", qualifiedByName = "toImageUrl")
    EffectRequest toRequest(EffectParameters parameters);

    @Mapping(target = "image", source = "image", qualifiedByName = "toImageFile")
    @Mapping(target = "imageId", source = "image", qualifiedByName = "toImageId")
    @Mapping(target = "imageUrl", source = "image", qualifiedByName = "toImageUrl")
    @Mapping(target = "bgImage", source = "bgImage", qualifiedByName = "toImageFile")
    @Mapping(target = "bgImageId", source = "bgImage", qualifiedByName = "toImageId")
    @Mapping(target = "bgImageUrl", source = "bgImage", qualifiedByName = "toImageUrl")
    RemoveBackgroundRequest toRequest(RemoveBackgroundParameters parameters);

    @Mapping(target = "image", source = "image", qualifiedByName = "toImageFile")
    @Mapping(target = "imageId", source = "image", qualifiedByName = "toImageId")
    @Mapping(target = "imageUrl", source = "image", qualifiedByName = "toImageUrl")
    UltraUpscaleRequest toRequest(UltraUpscaleParameters parameters);

    @Mapping(target = "image", source = "image", qualifiedByName = "toImageFile")
    @Mapping(target = "imageId", source = "image", qualifiedByName = "toImageId")
    @Mapping(target = "imageUrl", source = "image", qualifiedByName = "toImageUrl")
    UpscaleRequest toRequest(UpscaleParameters parameters);

    @Mapping(target = "image", source = "image", qualifiedByName = "toImageFile")
    @Mapping(target = "imageId", source = "image", qualifiedByName = "toImageId")
    @Mapping(target = "imageUrl", source = "image", qualifiedByName = "toImageUrl")
    UltraEnhanceRequest toRequest(UltraEnhanceParameters parameters);

    @Mapping(target = "image", source = "image", qualifiedByName = "toImageFile")
    @Mapping(target = "imageId", source = "image", qualifiedByName = "toImageId")
    @Mapping(target = "imageUrl", source = "image", qualifiedByName = "toImageUrl")
    EnhanceFaceRequest toRequest(EnhanceFaceParameters parameters);

    @Mapping(target = "image", source = "image", qualifiedByName = "toImageFile")
    @Mapping(target = "imageId", source = "image", qualifiedByName = "toImageId")
    @Mapping(target = "imageUrl", source = "image", qualifiedByName = "toImageUrl")
    EffectsPreviewsRequest toRequest(EffectsPreviewsParameters parameters);

    @Mapping(target = "image", source = "image", qualifiedByName = "toImageFile")
    @Mapping(target = "imageId", source = "image", qualifiedByName = "toImageId")
    @Mapping(target = "imageUrl", source = "image", qualifiedByName = "toImageUrl")
    AdjustRequest toRequest(AdjustParameters parameters);

    @Mapping(target = "image", source = "image", qualifiedByName = "toImageFile")
    @Mapping(target = "imageId", source = "image", qualifiedByName = "toImageId")
    @Mapping(target = "imageUrl", source = "image", qualifiedByName = "toImageUrl")
    BackgroundTextureRequest toRequest(BackgroundTextureParameters parameters);

    @Mapping(target = "image", source = "image", qualifiedByName = "toImageFile")
    @Mapping(target = "imageId", source = "image", qualifiedByName = "toImageId")
    @Mapping(target = "imageUrl", source = "image", qualifiedByName = "toImageUrl")
    @Mapping(target = "maskId", source = "mask", qualifiedByName = "toImageId")
    @Mapping(target = "maskUrl", source = "mask", qualifiedByName = "toImageUrl")
    @Mapping(target = "mask", source = "mask", qualifiedByName = "toImageFile")
    @Mapping(target = "sticker", source = "sticker", qualifiedByName = "toImageFile")
    @Mapping(target = "stickerId", source = "sticker",  qualifiedByName = "toImageId")
    @Mapping(target = "stickerUrl", source = "sticker", qualifiedByName = "toImageUrl")
    SurfaceMapRequest toRequest(SurfaceMapParameters parameters);


    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "image", source = "image", qualifiedByName = "toImageFile")
    UploadRequest toRequest(ImageFile image);

    @Mapping(target = "imageUrl", source = "imageUrl", qualifiedByName = "toImageUrl")
    @Mapping(target = "image", ignore = true)
    UploadRequest toRequest(ImageUrl imageUrl);

    @Named("toImageUrl")
    default String toImageUrl(ImageSource imageSource) {
        if (imageSource.getType() == ImageSource.ImageSourceType.URL) {
            return ((ImageUrl) imageSource).getUrl();
        }
        return null;
    }

    @Named("toImageFile")
    default File toImageFile(ImageSource imageSource) {
        if (imageSource.getType() == ImageSource.ImageSourceType.FILE) {
            return ((ImageFile) imageSource).getFile();
        }
        return null;
    }

    @Named("toImageId")
    default String toImageId(ImageSource imageSource) {
        if (imageSource.getType() == ImageSource.ImageSourceType.ID) {
            return ((ImageId) imageSource).getId();
        }
        return null;
    }
}
