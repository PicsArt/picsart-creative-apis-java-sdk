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

package com.picsart.creativeapis.imageapi;

import com.picsart.creativeapis.PicsartEnterprise;
import com.picsart.creativeapis.busobj.image.*;
import com.picsart.creativeapis.busobj.image.parameters.*;
import com.picsart.creativeapis.image.ImageApi;
import java.io.File;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

public class ImageApiIntegrationTest {
  private static final String API_KEY = System.getProperty("API_KEY");
  private static final ImageFile IMAGE_FILE =
      ImageSource.fromFile(
          new File(
              Objects.requireNonNull(ImageApiIntegrationTest.class.getResource("/images/1.png"))
                  .getPath()));

  private static final ImageFile IMAGE_FILE2 =
      ImageSource.fromFile(
          new File(
              Objects.requireNonNull(ImageApiIntegrationTest.class.getResource("/images/2.png"))
                  .getPath()));

  private ImageApi imageApi;

  @BeforeEach
  public void setup() {
    Hooks.onOperatorDebug();
    imageApi = PicsartEnterprise.createImageApi(API_KEY);
  }

  @DisplayName(
      "Should return RemoveBackgroundResult when removeBackground is called with valid parameters")
  @Test
  public void removeBackgroundSmokeTest() {

    var removeBackgroundParameters =
        RemoveBackgroundParameters.builder(IMAGE_FILE).outputType(OutputType.CUTOUT).build();
    var removeBackgroundResultMono = imageApi.removeBackground(removeBackgroundParameters);

    // Then
    StepVerifier.create(removeBackgroundResultMono)
        .expectNextMatches(
            response -> {
              return response.image() != null
                  && response.image().url() != null
                  && response.image().id() != null;
            })
        .verifyComplete();
  }

  @DisplayName("Should return EffectResult when effect is called with valid parameters")
  @Test
  public void effectSmokeTest() {
    // Given
    var effectParameters =
        EffectParameters.builder(IMAGE_FILE, "apr1").format(ImageFormat.WEBP).build();
    var effectResultMono = imageApi.effect(effectParameters);

    // Then
    StepVerifier.create(effectResultMono)
        .expectNextMatches(
            response -> {
              return response.image() != null
                  && response.image().url() != null
                  && response.image().id() != null;
            })
        .verifyComplete();
  }

  @DisplayName("Should return ListEffectsResult when listEffects is called")
  @Test
  public void listEffectsSmokeTest() {
    // Given
    var listEffectsResultMono = imageApi.listEffects();

    // Then
    StepVerifier.create(listEffectsResultMono)
        .expectNextMatches(
            response -> {
              return response.effects() != null && !response.effects().isEmpty();
            })
        .verifyComplete();
  }

  @DisplayName(
      "Should return UpscaleUltraResult when upscaleUltra is called with valid parameters - Async mode")
  @Test
  public void ultraUpscaleAsyncSmokeTest() {
    // Given
    var upscaleUltraParameters =
        UltraUpscaleParameters.builder(IMAGE_FILE)
            .mode(UpscaleMode.ASYNC)
            .format(ImageFormat.WEBP)
            .upscaleFactor(2)
            .build();
    var upscaleUltraResultMono = imageApi.ultraUpscale(upscaleUltraParameters);

    // Then
    StepVerifier.create(upscaleUltraResultMono)
        .expectNextMatches(
            response -> {
              return response.image() != null
                  && response.image().url() != null
                  && response.image().id() != null;
            })
        .verifyComplete();
  }

  @DisplayName(
      "Should return UpscaleUltraResult when upscaleUltra is called with valid parameters - Sync mode")
  @Test
  public void ultraUpscaleSyncSmokeTest() {
    // Given
    var upscaleUltraParameters =
        UltraUpscaleParameters.builder(IMAGE_FILE)
            .mode(UpscaleMode.SYNC)
            .format(ImageFormat.WEBP)
            .upscaleFactor(2)
            .build();
    var upscaleUltraResultMono = imageApi.ultraUpscale(upscaleUltraParameters);

    // Then
    StepVerifier.create(upscaleUltraResultMono)
        .expectNextMatches(
            response -> {
              return response.image() != null
                  && response.image().url() != null
                  && response.image().id() != null;
            })
        .verifyComplete();
  }

  @DisplayName(
      "Should return UpscaleUltraResult when upscaleUltra is called with valid parameters - Auto mode")
  @Test
  public void ultraUpscaleAutoSmokeTest() {
    // Given
    var upscaleUltraParameters =
        UltraUpscaleParameters.builder(IMAGE_FILE)
            .mode(UpscaleMode.AUTO)
            .format(ImageFormat.WEBP)
            .upscaleFactor(2)
            .build();
    var upscaleUltraResultMono = imageApi.ultraUpscale(upscaleUltraParameters);

    // Then
    StepVerifier.create(upscaleUltraResultMono)
        .expectNextMatches(
            response -> {
              return response.image() != null
                  && response.image().url() != null
                  && response.image().id() != null;
            })
        .verifyComplete();
  }

  @DisplayName("Should return UpscaleResult when upscale is called with valid parameters")
  @Test
  public void upscaleSmokeTest() {
    // Given
    var upscaleParameters =
        UpscaleParameters.builder(IMAGE_FILE).upscaleFactor(2).format(ImageFormat.WEBP).build();
    var upscaleResultMono = imageApi.upscale(upscaleParameters);

    // Then
    StepVerifier.create(upscaleResultMono)
        .expectNextMatches(
            response -> {
              return response.image() != null
                  && response.image().url() != null
                  && response.image().id() != null;
            })
        .verifyComplete();
  }

  @DisplayName("Should return UltraEnhanceResult when ultraEnhance is called with valid parameters")
  @Test
  public void ultraEnhanceSmokeTest() {
    // Given
    var ultraEnhanceParameters =
        UltraEnhanceParameters.builder(IMAGE_FILE)
            .upscaleFactor(2)
            .format(ImageFormat.WEBP)
            .build();
    var ultraEnhance = imageApi.ultraEnhance(ultraEnhanceParameters);

    // Then
    StepVerifier.create(ultraEnhance)
        .expectNextMatches(
            response -> {
              return response.image() != null
                  && response.image().url() != null
                  && response.image().id() != null;
            })
        .verifyComplete();
  }

  @DisplayName(
      "Should return FaceEnhancementResult when faceEnhancement is called with valid parameters")
  @Test
  public void enhanceFaceSmokeTest() {
    // Given
    var faceEnhancementParameters =
        EnhanceFaceParameters.builder(IMAGE_FILE).format(ImageFormat.WEBP).build();
    var faceEnhancementResultMono = imageApi.enhanceFace(faceEnhancementParameters);

    // Then
    StepVerifier.create(faceEnhancementResultMono)
        .expectNextMatches(
            response -> {
              return response.image() != null
                  && response.image().url() != null
                  && response.image().id() != null;
            })
        .verifyComplete();
  }

  @DisplayName(
      "Should return EffectsPreviewsResult when effectsPreviews is called with valid parameters")
  @Test
  public void effectsPreviewsSmokeTest() {
    // Given
    var effectsPreviewsParameters =
        EffectsPreviewsParameters.builder(IMAGE_FILE)
            .addEffectName("apr1")
            .addEffectName("apr2")
            .format(ImageFormat.WEBP)
            .build();
    var effectsPreviewsResultMono = imageApi.effectsPreviews(effectsPreviewsParameters);

    // Then
    StepVerifier.create(effectsPreviewsResultMono)
        .expectNextMatches(
            response -> {
              return response.effectsPreviews() != null
                  && response.effectsPreviews().size() == 2
                  && response.effectsPreviews().stream()
                      .allMatch(
                          effectPreview ->
                              effectPreview.url() != null && effectPreview.id() != null);
            })
        .verifyComplete();
  }

  @DisplayName("Should return AdjustResult when adjust is called with valid parameters")
  @Test
  public void adjustSmokeTest() {
    // Given
    var adjustParameters =
        AdjustParameters.builder(IMAGE_FILE)
            .brightness(-50)
            .contrast(-50)
            .saturation(-50)
            .shadows(50)
            .hue(50)
            .noise(20)
            .sharpen(50)
            .highlights(50)
            .vignette(50)
            .temperature(50)
            .clarity(50)
            .format(ImageFormat.WEBP)
            .build();
    var adjustResultMono = imageApi.adjust(adjustParameters);

    // Then
    StepVerifier.create(adjustResultMono)
        .expectNextMatches(
            response -> {
              return response.image() != null
                  && response.image().url() != null
                  && response.image().id() != null;
            })
        .verifyComplete();
  }

  @DisplayName(
      "Should return TextureGeneratorResult when textureGenerator is called with valid parameters")
  @Test
  public void backgroundTextureSmokeTest() {
    // Given
    var textureGeneratorParameters =
        BackgroundTextureParameters.builder(IMAGE_FILE)
            .rotate(40)
            .scale(1.5F)
            .pattern(BackgroundTexturePattern.HEX)
            .format(ImageFormat.WEBP)
            .build();
    var textureGeneratorResultMono = imageApi.backgroundTexture(textureGeneratorParameters);

    // Then
    StepVerifier.create(textureGeneratorResultMono)
        .expectNextMatches(
            response -> {
              return response.image() != null
                  && response.image().url() != null
                  && response.image().id() != null;
            })
        .verifyComplete();
  }

  @DisplayName("Should return SurfaceMapResult when surfaceMap is called with valid parameters")
  @Test
  public void surfaceMapSmokeTest() {
    // Given
    var surfaceMapParameters =
        SurfaceMapParameters.builder(IMAGE_FILE, IMAGE_FILE, IMAGE_FILE2)
            .format(ImageFormat.JPG)
            .build();
    var surfaceMapResultMono = imageApi.surfaceMap(surfaceMapParameters);

    // Then
    StepVerifier.create(surfaceMapResultMono)
        .expectNextMatches(
            response -> {
              return response.image() != null
                  && response.image().url() != null
                  && response.image().id() != null;
            })
        .verifyComplete();
  }

  @DisplayName("Should return UploadResult when upload is called with valid parameters")
  @Test
  public void uploadSmokeTest() {
    // Given
    var uploadResultMono = imageApi.upload(IMAGE_FILE);

    // Then
    StepVerifier.create(uploadResultMono)
        .expectNextMatches(
            response -> {
              return response.image() != null
                  && response.image().url() != null
                  && response.image().id() != null;
            })
        .verifyComplete();
  }

  @DisplayName("Should return BalanceResult when balance is called")
  @Test
  public void balanceSmokeTest() {
    // Given
    var balanceResultMono = imageApi.balance();

    // Then
    StepVerifier.create(balanceResultMono)
        .expectNextMatches(
            response -> {
              return response.credits() != null && response.credits() == 0;
            })
        .verifyComplete();
  }
}
