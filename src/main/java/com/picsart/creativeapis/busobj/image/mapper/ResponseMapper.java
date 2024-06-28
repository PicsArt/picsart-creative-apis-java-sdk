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

import com.picsart.creativeapis.busobj.image.response.*;
import com.picsart.creativeapis.busobj.image.result.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.picsart.creativeapis.busobj.mapper.MetadataMapper;
import reactor.netty.http.client.HttpClientResponse;

/**
 * This interface provides methods for mapping image api responses to results.
 * It uses the MapStruct library to generate the implementation of these methods.
 */
@Mapper(uses = {MetadataMapper.class})
public interface ResponseMapper {
    ResponseMapper INSTANCE = Mappers.getMapper(ResponseMapper.class);

    @Mapping(target = "metadata", source = "httpClientResponse")
    RemoveBackgroundResult toResult(RemoveBackgroundResponse response, HttpClientResponse httpClientResponse);

    @Mapping(target = "metadata", source = "httpClientResponse")
    EffectResult toResult(EffectResponse response, HttpClientResponse httpClientResponse);

    @Mapping(target = "metadata", source = "httpClientResponse")
    ListEffectsResult toResult(ListEffectsResponse response, HttpClientResponse httpClientResponse);

    @Mapping(target = "metadata", source = "httpClientResponse")
    UltraUpscaleResult toResult(UltraUpscaleResponse response, HttpClientResponse httpClientResponse);

    @Mapping(target = "metadata", source = "httpClientResponse")
    UpscaleResult toResult(UpscaleResponse response, HttpClientResponse httpClientResponse);

    @Mapping(target = "metadata", source = "httpClientResponse")
    UltraEnhanceResult toResult(UltraEnhanceResponse response, HttpClientResponse httpClientResponse);

    @Mapping(target = "metadata", source = "httpClientResponse")
    EnhanceFaceResult toResult(EnhanceFaceResponse response, HttpClientResponse httpClientResponse);

    @Mapping(target = "metadata", source = "httpClientResponse")
    EffectsPreviewsResult toResult(EffectsPreviewsResponse response, HttpClientResponse httpClientResponse);

    @Mapping(target = "metadata", source = "httpClientResponse")
    AdjustResult toResult(AdjustResponse response, HttpClientResponse httpClientResponse);

    @Mapping(target = "metadata", source = "httpClientResponse")
    BackgroundTextureResult toResult(BackgroundTextureResponse response, HttpClientResponse httpClientResponse);

    @Mapping(target = "metadata", source = "httpClientResponse")
    SurfaceMapResult toResult(SurfaceMapResponse response, HttpClientResponse httpClientResponse);

    @Mapping(target = "metadata", source = "httpClientResponse")
    UploadResult toResult(UploadResponse response, HttpClientResponse httpClientResponse);

    @Mapping(target = "metadata", source = "httpClientResponse")
    BalanceResult toResult(BalanceResponse response, HttpClientResponse httpClientResponse);
}
