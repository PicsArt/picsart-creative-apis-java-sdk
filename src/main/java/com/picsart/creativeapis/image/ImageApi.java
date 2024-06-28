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

import com.picsart.creativeapis.busobj.image.ImageFile;
import com.picsart.creativeapis.busobj.image.ImageUrl;
import com.picsart.creativeapis.busobj.image.parameters.*;
import com.picsart.creativeapis.busobj.image.result.*;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * This interface defines the operations that can be performed on images.
 */
public sealed interface ImageApi permits ImageApiImpl {

    /**
     * Sets the API key to be used for requests.
     *
     * @param apiKey The API key.
     * @return An instance of ImageApi with the API key set.
     */
    ImageApi withApiKey(String apiKey);

    /**
     * Sets the base URL for the API.
     *
     * @param baseUrl The base URL.
     * @return An instance of ImageApi with the base URL set.
     */
    ImageApi withBaseUrl(String baseUrl);

    /**
     * Sets the response timeout for the API.
     *
     * @param timeout The response timeout.
     * @return An instance of ImageApi with the response timeout set.
     */
    ImageApi withResponseTimeout(Duration timeout);

    /**
     * Removes the background from an image.
     *
     * @param parameters The parameters for the operation.
     * @return A Mono that emits the result of the operation.
     */
    Mono<RemoveBackgroundResult> removeBackground(RemoveBackgroundParameters parameters);

    /**
     * Applies an effect to an image.
     *
     * @param parameters The parameters for the operation.
     * @return A Mono that emits the result of the operation.
     */
    Mono<EffectResult> effect(EffectParameters parameters);

    /**
     * Lists the available effects.
     *
     * @return A Mono that emits the result of the operation.
     */
    Mono<ListEffectsResult> listEffects();

    /**
     * Upscales an image using ultra quality.
     *
     * @param parameters The parameters for the operation.
     * @return A Mono that emits the result of the operation.
     */
    Mono<UltraUpscaleResult> ultraUpscale(UltraUpscaleParameters parameters);

    /**
     * Upscales an image.
     *
     * @param parameters The parameters for the operation.
     * @return A Mono that emits the result of the operation.
     */
    Mono<UpscaleResult> upscale(UpscaleParameters parameters);

    /**
     * Enhances an image using ultra quality.
     *
     * @param parameters The parameters for the operation.
     * @return A Mono that emits the result of the operation.
     */
    Mono<UltraEnhanceResult> ultraEnhance(UltraEnhanceParameters parameters);

    /**
     * Enhances the faces in an image.
     *
     * @param parameters The parameters for the operation.
     * @return A Mono that emits the result of the operation.
     */
    Mono<EnhanceFaceResult> enhanceFace(EnhanceFaceParameters parameters);

    /**
     * Previews the effects on an image.
     *
     * @param parameters The parameters for the operation.
     * @return A Mono that emits the result of the operation.
     */
    Mono<EffectsPreviewsResult> effectsPreviews(EffectsPreviewsParameters parameters);

    /**
     * Adjusts an image.
     *
     * @param parameters The parameters for the operation.
     * @return A Mono that emits the result of the operation.
     */
    Mono<AdjustResult> adjust(AdjustParameters parameters);

    /**
     * Generates a background texture pattern for the input image
     *
     * @param parameters The parameters for the operation.
     * @return A Mono that emits the result of the operation.
     */
    Mono<BackgroundTextureResult> backgroundTexture(BackgroundTextureParameters parameters);

    /**
     * With the surface map tool you can "print" a sticker over an image.
     *
     * @param parameters The parameters for the operation.
     * @return A Mono that emits the result of the operation.
     */
    Mono<SurfaceMapResult> surfaceMap(SurfaceMapParameters parameters);

    /**
     * Uploads an image from a file.
     *
     * @param image The image file to upload.
     * @return A Mono that emits the result of the operation.
     */
    Mono<UploadResult> upload(ImageFile image);

    /**
     * Uploads an image from a URL.
     *
     * @param imageUrl The URL of the image to upload.
     * @return A Mono that emits the result of the operation.
     */
    Mono<UploadResult> upload(ImageUrl imageUrl);

    /**
     * Checks the balance of credits.
     *
     * @return A Mono that emits the result of the operation.
     */
    Mono<BalanceResult> balance();
}