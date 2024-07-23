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

package com.picsart.creativeapis.image;

import com.picsart.creativeapis.busobj.ApiConfig;
import com.picsart.creativeapis.busobj.image.ImageFile;
import com.picsart.creativeapis.busobj.image.ImageUrl;
import com.picsart.creativeapis.busobj.image.mapper.ParametersMapper;
import com.picsart.creativeapis.busobj.image.mapper.ResponseMapper;
import com.picsart.creativeapis.busobj.image.parameters.*;
import com.picsart.creativeapis.busobj.image.request.UploadRequest;
import com.picsart.creativeapis.busobj.image.result.*;
import com.picsart.creativeapis.image.client.ImageApiClient;
import java.time.Duration;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.With;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public final class ImageApiImpl implements ImageApi {
  @With(AccessLevel.PRIVATE)
  ApiConfig config;

  ImageApiClient client;

  @Override
  public ImageApi withApiKey(String apiKey) {
    return withConfig(config.withApiKey(apiKey));
  }

  @Override
  public ImageApi withBaseUrl(String baseUrl) {
    return withConfig(config.withBaseUrl(baseUrl));
  }

  @Override
  public ImageApi withResponseTimeout(Duration timeout) {
    return withConfig(config.withTimeout(timeout));
  }

  @Override
  public Mono<RemoveBackgroundResult> removeBackground(RemoveBackgroundParameters parameters) {
    return client
        .removeBackground(config, ParametersMapper.INSTANCE.toRequest(parameters))
        .map(
            response ->
                ResponseMapper.INSTANCE.toResult(
                    response.getBody(), response.getHttpClientResponse()));
  }

  @Override
  public Mono<EffectResult> effect(EffectParameters parameters) {
    return client
        .effect(config, ParametersMapper.INSTANCE.toRequest(parameters))
        .map(
            response ->
                ResponseMapper.INSTANCE.toResult(
                    response.getBody(), response.getHttpClientResponse()));
  }

  @Override
  public Mono<ListEffectsResult> listEffects() {
    return client
        .listEffects(config)
        .map(
            response ->
                ResponseMapper.INSTANCE.toResult(
                    response.getBody(), response.getHttpClientResponse()));
  }

  @Override
  public Mono<UltraUpscaleResult> ultraUpscale(UltraUpscaleParameters parameters) {
    return client
        .ultraUpscale(config, ParametersMapper.INSTANCE.toRequest(parameters))
        .map(
            response ->
                ResponseMapper.INSTANCE.toResult(
                    response.getBody(), response.getHttpClientResponse()));
  }

  @Override
  public Mono<UpscaleResult> upscale(UpscaleParameters parameters) {
    return client
        .upscale(config, ParametersMapper.INSTANCE.toRequest(parameters))
        .map(
            response ->
                ResponseMapper.INSTANCE.toResult(
                    response.getBody(), response.getHttpClientResponse()));
  }

  @Override
  public Mono<UltraEnhanceResult> ultraEnhance(UltraEnhanceParameters parameters) {
    return client
        .ultraEnhance(config, ParametersMapper.INSTANCE.toRequest(parameters))
        .map(
            response ->
                ResponseMapper.INSTANCE.toResult(
                    response.getBody(), response.getHttpClientResponse()));
  }

  @Override
  public Mono<EnhanceFaceResult> enhanceFace(EnhanceFaceParameters parameters) {
    return client
        .enhanceFace(config, ParametersMapper.INSTANCE.toRequest(parameters))
        .map(
            response ->
                ResponseMapper.INSTANCE.toResult(
                    response.getBody(), response.getHttpClientResponse()));
  }

  @Override
  public Mono<EffectsPreviewsResult> effectsPreviews(EffectsPreviewsParameters parameters) {
    return client
        .effectsPreviews(config, ParametersMapper.INSTANCE.toRequest(parameters))
        .map(
            response ->
                ResponseMapper.INSTANCE.toResult(
                    response.getBody(), response.getHttpClientResponse()));
  }

  @Override
  public Mono<AdjustResult> adjust(AdjustParameters parameters) {
    return client
        .adjust(config, ParametersMapper.INSTANCE.toRequest(parameters))
        .map(
            response ->
                ResponseMapper.INSTANCE.toResult(
                    response.getBody(), response.getHttpClientResponse()));
  }

  @Override
  public Mono<BackgroundTextureResult> backgroundTexture(BackgroundTextureParameters parameters) {
    return client
        .backgroundTexture(config, ParametersMapper.INSTANCE.toRequest(parameters))
        .map(
            response ->
                ResponseMapper.INSTANCE.toResult(
                    response.getBody(), response.getHttpClientResponse()));
  }

  @Override
  public Mono<SurfaceMapResult> surfaceMap(SurfaceMapParameters parameters) {
    return client
        .surfaceMap(config, ParametersMapper.INSTANCE.toRequest(parameters))
        .map(
            response ->
                ResponseMapper.INSTANCE.toResult(
                    response.getBody(), response.getHttpClientResponse()));
  }

  @Override
  public Mono<UploadResult> upload(ImageFile image) {
    return upload(ParametersMapper.INSTANCE.toRequest(image));
  }

  @Override
  public Mono<UploadResult> upload(ImageUrl imageUrl) {
    return upload(ParametersMapper.INSTANCE.toRequest(imageUrl));
  }

  @Override
  public Mono<BalanceResult> balance() {
    return client
        .balance(config)
        .map(
            response ->
                ResponseMapper.INSTANCE.toResult(
                    response.getBody(), response.getHttpClientResponse()));
  }

  private Mono<UploadResult> upload(UploadRequest request) {
    return client
        .upload(config, request)
        .map(
            response ->
                ResponseMapper.INSTANCE.toResult(
                    response.getBody(), response.getHttpClientResponse()));
  }
}
