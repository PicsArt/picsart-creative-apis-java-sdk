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

package com.picsart.creativeapis.image.client;

import com.picsart.creativeapis.busobj.ApiConfig;
import com.picsart.creativeapis.busobj.HttpResponseWithBody;
import com.picsart.creativeapis.busobj.image.request.*;
import com.picsart.creativeapis.busobj.image.response.*;
import reactor.core.publisher.Mono;

/**
 * This interface represents the client for the Image API. It includes methods for performing
 * operations on the API, such as removing background, applying effects, and enhancing images.
 */
public interface ImageApiClient {
  /**
   * Removes the background of an image using the Image API.
   *
   * @param config The configuration for the API.
   * @param request The request for the RemoveBackground operation.
   * @return A Mono that emits the HTTP response from the API. The body of the response is a
   *     RemoveBackgroundResponse.
   */
  Mono<HttpResponseWithBody<RemoveBackgroundResponse>> removeBackground(
      ApiConfig config, RemoveBackgroundRequest request);

  /**
   * Applies an effect to an image using the Image API.
   *
   * @param config The configuration for the API.
   * @param request The request for the Effect operation.
   * @return A Mono that emits the HTTP response from the API. The body of the response is an
   *     EffectResponse.
   */
  Mono<HttpResponseWithBody<EffectResponse>> effect(ApiConfig config, EffectRequest request);

  /**
   * Lists the available effects using the Image API.
   *
   * @param config The configuration for the API.
   * @return A Mono that emits the HTTP response from the API. The body of the response is a
   *     ListEffectsResponse.
   */
  Mono<HttpResponseWithBody<ListEffectsResponse>> listEffects(ApiConfig config);

  /**
   * Upscales an image using ultra quality with the Image API.
   *
   * @param config The configuration for the API.
   * @param request The request for the UltraUpscale operation.
   * @return A Mono that emits the HTTP response from the API. The body of the response is an
   *     UltraUpscaleResponse.
   */
  Mono<HttpResponseWithBody<UltraUpscaleResponse>> ultraUpscale(
      ApiConfig config, UltraUpscaleRequest request);

  /**
   * Upscales an image using the Image API.
   *
   * @param config The configuration for the API.
   * @param request The request for the Upscale operation.
   * @return A Mono that emits the HTTP response from the API. The body of the response is an
   *     UpscaleResponse.
   */
  Mono<HttpResponseWithBody<UpscaleResponse>> upscale(ApiConfig config, UpscaleRequest request);

  /**
   * Enhances an image using ultra quality with the Image API.
   *
   * @param config The configuration for the API.
   * @param request The request for the UltraEnhance operation.
   * @return A Mono that emits the HTTP response from the API. The body of the response is an
   *     UltraEnhanceResponse.
   */
  Mono<HttpResponseWithBody<UltraEnhanceResponse>> ultraEnhance(
      ApiConfig config, UltraEnhanceRequest request);

  /**
   * Enhances the faces in an image using the Image API.
   *
   * @param config The configuration for the API.
   * @param request The request for the EnhanceFace operation.
   * @return A Mono that emits the HTTP response from the API. The body of the response is an
   *     EnhanceFaceResponse.
   */
  Mono<HttpResponseWithBody<EnhanceFaceResponse>> enhanceFace(
      ApiConfig config, EnhanceFaceRequest request);

  /**
   * Previews the effects on an image using the Image API.
   *
   * @param config The configuration for the API.
   * @param request The request for the EffectsPreviews operation.
   * @return A Mono that emits the HTTP response from the API. The body of the response is an
   *     EffectsPreviewsResponse.
   */
  Mono<HttpResponseWithBody<EffectsPreviewsResponse>> effectsPreviews(
      ApiConfig config, EffectsPreviewsRequest request);

  /**
   * Adjusts the image using the Image API.
   *
   * @param config The configuration for the API.
   * @param request The request for the Adjust operation.
   * @return A Mono that emits the HTTP response from the API. The body of the response is an
   *     AdjustResponse.
   */
  Mono<HttpResponseWithBody<AdjustResponse>> adjust(ApiConfig config, AdjustRequest request);

  /**
   * Generates a background texture using the Image API.
   *
   * @param config The configuration for the API.
   * @param request The request for the BackgroundTexture operation.
   * @return A Mono that emits the HTTP response from the API. The body of the response is a
   *     BackgroundTextureResponse.
   */
  Mono<HttpResponseWithBody<BackgroundTextureResponse>> backgroundTexture(
      ApiConfig config, BackgroundTextureRequest request);

  /**
   * Generates a surface map using the Image API.
   *
   * @param config The configuration for the API.
   * @param request The request for the SurfaceMap operation.
   * @return A Mono that emits the HTTP response from the API. The body of the response is a
   *     SurfaceMapResponse.
   */
  Mono<HttpResponseWithBody<SurfaceMapResponse>> surfaceMap(
      ApiConfig config, SurfaceMapRequest request);

  /**
   * Uploads an image to the Image API.
   *
   * @param config The configuration for the API.
   * @param request The request for the Upload operation.
   * @return A Mono that emits the HTTP response from the API. The body of the response is an
   *     UploadResponse.
   */
  Mono<HttpResponseWithBody<UploadResponse>> upload(ApiConfig config, UploadRequest request);

  /**
   * Gets the balance of the account using the Image API.
   *
   * @param config The configuration for the API.
   * @return A Mono that emits the HTTP response from the API. The body of the response is a
   *     BalanceResponse.
   */
  Mono<HttpResponseWithBody<BalanceResponse>> balance(ApiConfig config);
}
