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

import static com.picsart.creativeapis.utils.Constants.SLASH;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.picsart.creativeapis.AbstractApiClient;
import com.picsart.creativeapis.busobj.ApiActions;
import com.picsart.creativeapis.busobj.ApiConfig;
import com.picsart.creativeapis.busobj.HttpResponseWithStringBody;
import com.picsart.creativeapis.busobj.exception.ServiceUnavailableException;
import com.picsart.creativeapis.busobj.image.Effect;
import com.picsart.creativeapis.busobj.image.Image;
import com.picsart.creativeapis.busobj.image.ImageWithEffect;
import com.picsart.creativeapis.busobj.image.config.ImageApiClientConfig;
import com.picsart.creativeapis.busobj.image.request.*;
import com.picsart.creativeapis.busobj.image.response.*;
import com.picsart.creativeapis.busobj.result.Metadata;
import com.picsart.creativeapis.http.ApiHttpClient;
import com.picsart.creativeapis.image.client.ImageApiClientImpl;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.io.File;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ImageApiClientImplTest {

  private static final String API_KEY = "apiKey";
  private static final String BASE_URL = "baseUrl";
  private static final Duration TIMEOUT = Duration.ofSeconds(60);
  private static final ApiConfig CONFIG = new ApiConfig(API_KEY, BASE_URL, TIMEOUT);
  private static final Metadata METADATA = new Metadata(null, null, null, null, null);

  // remove background
  private static final RemoveBackgroundRequest VALID_RB_REQUEST =
      new RemoveBackgroundRequest(
          "testId", null, null, null, null, null, null, null, null, null, null, null, null, null,
          null, null, null);
  private static final RemoveBackgroundRequest INVALID_RB_REQUEST =
      new RemoveBackgroundRequest(
          null,
          null,
          null,
          null,
          null,
          "bgImageId",
          "bgImageUrl",
          null,
          null,
          -1,
          null,
          null,
          null,
          null,
          101,
          null,
          200);

  // effect
  private static final EffectRequest VALID_EFFECT_REQUEST =
      new EffectRequest("imageId", null, null, null, "testEffect");
  private static final EffectRequest INVALID_EFFECT_REQUEST =
      new EffectRequest("imageId", "imageUrl", null, null, null);

  // upscale ultra
  private static final UltraUpscaleRequest VALID_UPSCALE_ULTRA_REQUEST =
      new UltraUpscaleRequest("testId", null, null, null, null, null);
  private static final UltraUpscaleRequest INVALID_UPSCALE_ULTRA_REQUEST =
      new UltraUpscaleRequest("testId", "testUrl", null, null, 1, null);

  // upscale
  private static final UpscaleRequest VALID_UPSCALE_REQUEST =
      new UpscaleRequest("testId", null, null, null, null);
  private static final UpscaleRequest INVALID_UPSCALE_REQUEST =
      new UpscaleRequest("testId", "testUrl", null, null, 1);

  // ultra enhance
  private static final UltraEnhanceRequest VALID_ULTRA_ENHANCE_REQUEST =
      new UltraEnhanceRequest("testId", null, null, null, null);
  private static final UltraEnhanceRequest INVALID_ULTRA_ENHANCE_REQUEST =
      new UltraEnhanceRequest("testId", "testUrl", null, null, 1);

  // face enhancement
  private static final EnhanceFaceRequest VALID_FACE_ENHANCE_REQUEST =
      new EnhanceFaceRequest("testId", null, null, null);
  private static final EnhanceFaceRequest INVALID_FACE_ENHANCE_REQUEST =
      new EnhanceFaceRequest("testId", "testUrl", null, null);

  // effects previews
  private static final EffectsPreviewsRequest VALID_EFFECTS_PREVIEWS_REQUEST =
      new EffectsPreviewsRequest("testId", null, null, null, List.of("effect1", "effect2"), 1);
  private static final EffectsPreviewsRequest INVALID_EFFECTS_PREVIEWS_REQUEST =
      new EffectsPreviewsRequest("testId", "testUrl", null, null, List.of(), null);

  // adjust
  private static final AdjustRequest VALID_ADJUST_REQUEST =
      new AdjustRequest(
          "testId", null, null, null, null, null, null, null, null, null, null, null, null, null,
          null);
  private static final AdjustRequest INVALID_ADJUST_REQUEST =
      new AdjustRequest(
          "testId", "testUrl", null, null, 101, -101, 101, -101, 101, -101, 101, -101, -1, -2, 101);

  // texture generator
  private static final BackgroundTextureRequest VALID_TEXTURE_GENERATOR_REQUEST =
      new BackgroundTextureRequest(
          "testId", null, null, null, null, null, null, null, null, null, null);
  private static final BackgroundTextureRequest INVALID_TEXTURE_GENERATOR_REQUEST =
      new BackgroundTextureRequest(
          "testId", "testUrl", null, null, 0, 8001, null, null, null, -181, 10.1F);

  // surface map
  private static final SurfaceMapRequest VALID_SURFACE_MAP_REQUEST =
      new SurfaceMapRequest(
          "testId", null, null, null, null, null, "testMaskId", null, null, "testStickerId");
  private static final SurfaceMapRequest INVALID_SURFACE_MAP_REQUEST =
      new SurfaceMapRequest(
          "testId",
          "testUrl",
          null,
          null,
          null,
          null,
          null,
          null,
          "testStickerUrl",
          "testStickerId");

  // upload
  private static final UploadRequest VALID_UPLOAD_REQUEST = new UploadRequest(null, "testUrl");
  private static final UploadRequest INVALID_UPLOAD_REQUEST =
      new UploadRequest(new File("path"), "testUrl");

  @Mock private ApiHttpClient apiHttpClient;

  @Spy
  private ImageApiClientConfig imageApiClientConfig =
      ImageApiClientConfig.builder()
          .upscaleUltraPollingRepeatDelay(Duration.ofMillis(1))
          .upscaleUltraPollingRepeatCount(1)
          .upscaleUltraPollingFirstDelay(Duration.ofMillis(1))
          .build();

  @InjectMocks private ImageApiClientImpl imageApiClient;

  @DisplayName(
      "Should return RemoveBackgroundResponse when removeBackground is called with valid request")
  @Test
  public void shouldReturnRemoveBackgroundResponseWhenRemoveBackgroundIsCalledWithValidRequest() {
    // Given
    var validResponseBody =
        """
                {
                    "status": "DONE",
                    "data": {
                            "id": "testId",
                            "url": "testUrl"
                    }
                }
                """;
    var expectedResponse = new RemoveBackgroundResponse("DONE", new Image("testId", "testUrl"));
    var httpClientResponse200 = mock(HttpClientResponse.class);
    when(httpClientResponse200.status()).thenReturn(new HttpResponseStatus(200, "OK"));
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.REMOVE_BACKGROUND.url()),
            API_KEY,
            VALID_RB_REQUEST,
            TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = imageApiClient.removeBackground(CONFIG, VALID_RB_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectNextMatches(
            response ->
                response.getHttpClientResponse().status().code() == 200
                    && response.getBody().equals(expectedResponse))
        .verifyComplete();
  }

  @DisplayName("Should return error Mono when removeBackground is called with invalid request")
  @Test
  public void shouldReturnErrorMonoWhenRemoveBackgroundIsCalledWithInvalidRequest() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.REMOVE_BACKGROUND.url()),
            API_KEY,
            INVALID_RB_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.empty());

    // When
    var actualResponse = imageApiClient.removeBackground(CONFIG, INVALID_RB_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof IllegalArgumentException
                    && throwable
                        .getMessage()
                        .equals(
                            ApiActions.REMOVE_BACKGROUND.actionName()
                                + " failed with errors: Blur must be in range [0, 100],"
                                + " Exactly one image source must be set,"
                                + " Only one bg image source can be set,"
                                + " Stroke opacity must be in range [0, 100],"
                                + " Stroke size must be in range [0, 100]"))
        .verify();
  }

  @DisplayName(
      "Should return error Mono when removeBackground is called and downstream service is unavailable")
  @Test
  public void shouldReturnErrorMonoWhenRemoveBackgroundIsCalledAndDownstreamServiceIsUnavailable() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.REMOVE_BACKGROUND.url()),
            API_KEY,
            VALID_RB_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.error(new ServiceUnavailableException("Service Unavailable", METADATA)));

    // When
    var actualResponse = imageApiClient.removeBackground(CONFIG, VALID_RB_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof ServiceUnavailableException
                    && throwable.getMessage().equals("Service Unavailable"))
        .verify();
  }

  @DisplayName("Should return EffectResponse when effect is called with valid request")
  @Test
  public void shouldReturnEffectResponseWhenEffectIsCalledWithValidRequest() {
    // Given
    var validResponseBody =
        """
                {
                    "status": "DONE",
                    "data": {
                            "id": "testId",
                            "url": "testUrl"
                    }
                }
                """;
    var expectedResponse = new EffectResponse("DONE", new Image("testId", "testUrl"));
    var httpClientResponse200 = mock(HttpClientResponse.class);
    when(httpClientResponse200.status()).thenReturn(new HttpResponseStatus(200, "OK"));
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.EFFECT.url()),
            API_KEY,
            VALID_EFFECT_REQUEST,
            TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = imageApiClient.effect(CONFIG, VALID_EFFECT_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectNextMatches(
            response ->
                response.getHttpClientResponse().status().code() == 200
                    && response.getBody().equals(expectedResponse))
        .verifyComplete();
  }

  @DisplayName("Should return error Mono when effect is called with invalid request")
  @Test
  public void shouldReturnErrorMonoWhenEffectIsCalledWithInvalidRequest() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.EFFECT.url()),
            API_KEY,
            INVALID_EFFECT_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.empty());

    // When
    var actualResponse = imageApiClient.effect(CONFIG, INVALID_EFFECT_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof IllegalArgumentException
                    && throwable
                        .getMessage()
                        .equals(
                            ApiActions.EFFECT.actionName()
                                + " failed with errors: Effect name must be set,"
                                + " Exactly one image source must be set"))
        .verify();
  }

  @DisplayName(
      "Should return error Mono when effect is called and downstream service is unavailable")
  @Test
  public void shouldReturnErrorMonoWhenEffectIsCalledAndDownstreamServiceIsUnavailable() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.EFFECT.url()),
            API_KEY,
            VALID_EFFECT_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.error(new ServiceUnavailableException("Service Unavailable", METADATA)));

    // When
    var actualResponse = imageApiClient.effect(CONFIG, VALID_EFFECT_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof ServiceUnavailableException
                    && throwable.getMessage().equals("Service Unavailable"))
        .verify();
  }

  @DisplayName("Should return ListEffectResponse when list effect is called")
  @Test
  public void shouldReturnListEffectResponseWhenListEffectsIsCalled() {
    // Given
    var validResponseBody =
        """
                {
                    "status": "DONE",
                    "data": [
                        {
                            "name": "effect1"
                        },
                        {
                            "name": "effect2"
                        },
                        {
                            "name": "effect3"
                        }
                    ]
                }
                """;
    var expectedResponse =
        new ListEffectsResponse(
            List.of(new Effect("effect1"), new Effect("effect2"), new Effect("effect3")));
    var httpClientResponse200 = mock(HttpClientResponse.class);
    when(httpClientResponse200.status()).thenReturn(new HttpResponseStatus(200, "OK"));
    when(apiHttpClient.sendGetRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.EFFECT.url()), API_KEY, TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = imageApiClient.listEffects(CONFIG);

    // Then
    StepVerifier.create(actualResponse)
        .expectNextMatches(
            response ->
                response.getHttpClientResponse().status().code() == 200
                    && response.getBody().equals(expectedResponse))
        .verifyComplete();
  }

  @DisplayName(
      "Should return error Mono when list effects is called and downstream service is unavailable")
  @Test
  public void shouldReturnErrorMonoWhenListEffectsIsCalledAndDownstreamServiceIsUnavailable() {
    // Given
    when(apiHttpClient.sendGetRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.EFFECT.url()), API_KEY, TIMEOUT))
        .thenReturn(Mono.error(new ServiceUnavailableException("Service Unavailable", METADATA)));

    // When
    var actualResponse = imageApiClient.listEffects(CONFIG);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof ServiceUnavailableException
                    && throwable.getMessage().equals("Service Unavailable"))
        .verify();
  }

  @DisplayName(
      "Should return UpscaleUltraResponse when upscaleUltra is called with valid request (async mode)")
  @Test
  public void shouldReturnUpscaleUltraResponseWhenUltraUpscaleIsCalledWithValidRequestAsyncMode() {
    // Given
    var validMiddleResponseBody =
        """
                {
                    "transaction_id": "testTransactionId"
                }
                """;
    var validResponseBody =
        """
                {
                    "status": "DONE",
                    "data": {
                            "id": "testId",
                            "url": "testUrl"
                    }
                }
                """;
    var httpClientResponse200 = mock(HttpClientResponse.class);
    var httpClientResponse202 = mock(HttpClientResponse.class);
    when(httpClientResponse200.status()).thenReturn(new HttpResponseStatus(200, "OK"));
    when(httpClientResponse202.status()).thenReturn(new HttpResponseStatus(202, "Accepted"));
    var expectedResponse = new UltraUpscaleResponse("DONE", new Image("testId", "testUrl"));
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.ULTRA_UPSCALE.url()),
            API_KEY,
            VALID_UPSCALE_ULTRA_REQUEST,
            TIMEOUT))
        .thenReturn(
            Mono.just(
                HttpResponseWithStringBody.of(httpClientResponse202, validMiddleResponseBody)));

    when(apiHttpClient.sendGetRequest(
            AbstractApiClient.appendBaseUrl(
                BASE_URL, ApiActions.ULTRA_UPSCALE.url() + SLASH + "testTransactionId"),
            API_KEY,
            TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = imageApiClient.ultraUpscale(CONFIG, VALID_UPSCALE_ULTRA_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectNextMatches(
            response ->
                response.getHttpClientResponse().status().code() == 200
                    && response.getBody().equals(expectedResponse))
        .verifyComplete();
  }

  @DisplayName("Should return error Mono when upscaleUltra is called with invalid request")
  @Test
  public void shouldReturnErrorMonoWhenUltraUpscaleIsCalledWithInvalidRequest() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.ULTRA_UPSCALE.url()),
            API_KEY,
            INVALID_UPSCALE_ULTRA_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.empty());

    // When
    var actualResponse = imageApiClient.ultraUpscale(CONFIG, INVALID_UPSCALE_ULTRA_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof IllegalArgumentException
                    && throwable
                        .getMessage()
                        .equals(
                            ApiActions.ULTRA_UPSCALE.actionName()
                                + " failed with errors: Exactly one image source must be set,"
                                + " Upscale factor must be in range [2, 16]"))
        .verify();
  }

  @DisplayName(
      "Should return error Mono when upscaleUltra is called and downstream service is unavailable")
  @Test
  public void shouldReturnErrorMonoWhenUpscaleUltrasIsCalledAndDownstreamServiceIsUnavailable() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.ULTRA_UPSCALE.url()),
            API_KEY,
            VALID_UPSCALE_ULTRA_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.error(new ServiceUnavailableException("Service Unavailable", METADATA)));

    // When
    var actualResponse = imageApiClient.ultraUpscale(CONFIG, VALID_UPSCALE_ULTRA_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof ServiceUnavailableException
                    && throwable.getMessage().equals("Service Unavailable"))
        .verify();
  }

  @DisplayName(
      "Should return error response when upscaleUltra fail to finish within the configured repeats")
  @Test
  public void shouldReturnErrorResponseWhenUltraUpscaleFailToFinishWithinConfiguredRepeats() {
    // Given
    var validMiddleResponseBody =
        """
                {
                    "transaction_id": "testTransactionId"
                }
                """;
    var validResponseBody =
        """
                {
                    "status": "DONE",
                    "data": {
                            "id": "testId",
                            "url": "testUrl"
                    }
                }
                """;
    var httpClientResponse202 = mock(HttpClientResponse.class);
    when(httpClientResponse202.status()).thenReturn(new HttpResponseStatus(202, "Accepted"));
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.ULTRA_UPSCALE.url()),
            API_KEY,
            VALID_UPSCALE_ULTRA_REQUEST,
            TIMEOUT))
        .thenReturn(
            Mono.just(
                HttpResponseWithStringBody.of(httpClientResponse202, validMiddleResponseBody)));

    when(apiHttpClient.sendGetRequest(
            AbstractApiClient.appendBaseUrl(
                BASE_URL, ApiActions.ULTRA_UPSCALE.url() + SLASH + "testTransactionId"),
            API_KEY,
            TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse202, validResponseBody)));

    // When
    var actualResponse = imageApiClient.ultraUpscale(CONFIG, VALID_UPSCALE_ULTRA_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            error ->
                error instanceof IllegalStateException
                    && error.getMessage().equals("Exceeded maximum number of repeats"))
        .verify();
  }

  @DisplayName("Should return UpscaleResponse when upscale is called with valid request")
  @Test
  public void shouldReturnUpscaleResponseWhenUpscaleIsCalledWithValidRequest() {
    // Given
    var validResponseBody =
        """
                {
                    "status": "DONE",
                    "data": {
                            "id": "testId",
                            "url": "testUrl"
                    }
                }
                """;
    var expectedResponse = new UpscaleResponse("DONE", new Image("testId", "testUrl"));
    var httpClientResponse200 = mock(HttpClientResponse.class);
    when(httpClientResponse200.status()).thenReturn(new HttpResponseStatus(200, "OK"));
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.UPSCALE.url()),
            API_KEY,
            VALID_UPSCALE_REQUEST,
            TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = imageApiClient.upscale(CONFIG, VALID_UPSCALE_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectNextMatches(
            response ->
                response.getHttpClientResponse().status().code() == 200
                    && response.getBody().equals(expectedResponse))
        .verifyComplete();
  }

  @DisplayName("Should return error Mono when upscale is called with invalid request")
  @Test
  public void shouldReturnErrorMonoWhenUpscaleIsCalledWithInvalidRequest() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.UPSCALE.url()),
            API_KEY,
            INVALID_UPSCALE_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.empty());

    // When
    var actualResponse = imageApiClient.upscale(CONFIG, INVALID_UPSCALE_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof IllegalArgumentException
                    && throwable
                        .getMessage()
                        .equals(
                            ApiActions.UPSCALE.actionName()
                                + " failed with errors: Exactly one image source must be set,"
                                + " Upscale factor can be 2, 4, 6 or 8"))
        .verify();
  }

  @DisplayName(
      "Should return error Mono when upscale is called and downstream service is unavailable")
  @Test
  public void shouldReturnErrorMonoWhenUpscaleIsCalledAndDownstreamServiceIsUnavailable() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.UPSCALE.url()),
            API_KEY,
            VALID_UPSCALE_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.error(new ServiceUnavailableException("Service Unavailable", METADATA)));

    // When
    var actualResponse = imageApiClient.upscale(CONFIG, VALID_UPSCALE_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof ServiceUnavailableException
                    && throwable.getMessage().equals("Service Unavailable"))
        .verify();
  }

  @DisplayName("Should return UltraEnhanceResponse when ultraEnhance is called with valid request")
  @Test
  public void shouldReturnUltraEnhanceResponseWhenUltraEnhanceIsCalledWithValidRequest() {
    // Given
    var validResponseBody =
        """
                {
                    "status": "DONE",
                    "data": {
                            "id": "testId",
                            "url": "testUrl"
                    }
                }
                """;
    var expectedResponse = new UltraEnhanceResponse("DONE", new Image("testId", "testUrl"));
    var httpClientResponse200 = mock(HttpClientResponse.class);
    when(httpClientResponse200.status()).thenReturn(new HttpResponseStatus(200, "OK"));
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.ULTRA_ENHANCE.url()),
            API_KEY,
            VALID_ULTRA_ENHANCE_REQUEST,
            TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = imageApiClient.ultraEnhance(CONFIG, VALID_ULTRA_ENHANCE_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectNextMatches(
            response ->
                response.getHttpClientResponse().status().code() == 200
                    && response.getBody().equals(expectedResponse))
        .verifyComplete();
  }

  @DisplayName("Should return error Mono when ultraEnhance is called with invalid request")
  @Test
  public void shouldReturnErrorMonoWhenUltraEnhanceIsCalledWithInvalidRequest() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.ULTRA_ENHANCE.url()),
            API_KEY,
            INVALID_ULTRA_ENHANCE_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.empty());

    // When
    var actualResponse = imageApiClient.ultraEnhance(CONFIG, INVALID_ULTRA_ENHANCE_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof IllegalArgumentException
                    && throwable
                        .getMessage()
                        .equals(
                            ApiActions.ULTRA_ENHANCE.actionName()
                                + " failed with errors: Exactly one image source must be set,"
                                + " Upscale factor must be be in range [2, 16]"))
        .verify();
  }

  @DisplayName(
      "Should return error Mono when ultraEnhance is called and downstream service is unavailable")
  @Test
  public void shouldReturnErrorMonoWhenUltraEnhanceIsCalledAndDownstreamServiceIsUnavailable() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.ULTRA_ENHANCE.url()),
            API_KEY,
            VALID_ULTRA_ENHANCE_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.error(new ServiceUnavailableException("Service Unavailable", METADATA)));

    // When
    var actualResponse = imageApiClient.ultraEnhance(CONFIG, VALID_ULTRA_ENHANCE_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof ServiceUnavailableException
                    && throwable.getMessage().equals("Service Unavailable"))
        .verify();
  }

  @DisplayName(
      "Should return FaceEnhancementResponse when faceEnhancement is called with valid request")
  @Test
  public void shouldReturnFaceEnhancementResponseWhenEnhanceFaceIsCalledWithValidRequest() {
    // Given
    var validResponseBody =
        """
                {
                    "status": "DONE",
                    "data": {
                            "id": "testId",
                            "url": "testUrl"
                    }
                }
                """;
    var expectedResponse = new EnhanceFaceResponse("DONE", new Image("testId", "testUrl"));
    var httpClientResponse200 = mock(HttpClientResponse.class);
    when(httpClientResponse200.status()).thenReturn(new HttpResponseStatus(200, "OK"));
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.ENHANCE_FACE.url()),
            API_KEY,
            VALID_FACE_ENHANCE_REQUEST,
            TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = imageApiClient.enhanceFace(CONFIG, VALID_FACE_ENHANCE_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectNextMatches(
            response ->
                response.getHttpClientResponse().status().code() == 200
                    && response.getBody().equals(expectedResponse))
        .verifyComplete();
  }

  @DisplayName("Should return error Mono when faceEnhancement is called with invalid request")
  @Test
  public void shouldReturnErrorMonoWhenEnhanceFaceIsCalledWithInvalidRequest() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.ENHANCE_FACE.url()),
            API_KEY,
            INVALID_FACE_ENHANCE_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.empty());

    // When
    var actualResponse = imageApiClient.enhanceFace(CONFIG, INVALID_FACE_ENHANCE_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof IllegalArgumentException
                    && throwable
                        .getMessage()
                        .equals(
                            ApiActions.ENHANCE_FACE.actionName()
                                + " failed with errors: Exactly one image source must be set"))
        .verify();
  }

  @DisplayName(
      "Should return error Mono when faceEnhancement is called and downstream service is unavailable")
  @Test
  public void shouldReturnErrorMonoWhenEnhanceFaceIsCalledAndDownstreamServiceIsUnavailable() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.ENHANCE_FACE.url()),
            API_KEY,
            VALID_FACE_ENHANCE_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.error(new ServiceUnavailableException("Service Unavailable", METADATA)));

    // When
    var actualResponse = imageApiClient.enhanceFace(CONFIG, VALID_FACE_ENHANCE_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof ServiceUnavailableException
                    && throwable.getMessage().equals("Service Unavailable"))
        .verify();
  }

  @DisplayName(
      "Should return EffectsPreviewsResponse when effectsPreviews is called with valid request")
  @Test
  public void shouldReturnEffectsPreviewsResponseWhenEffectsPreviewsIsCalledWithValidRequest() {
    // Given
    var validResponseBody =
        """
                {
                    "status": "DONE",
                    "data": [
                        {
                            "id": "testId1",
                            "url": "testUrl1",
                            "effect_name": "effect1"
                        },
                        {
                            "id": "testId2",
                            "url": "testUrl2",
                            "effect_name": "effect2"
                        }
                    ]
                }
                """;
    var expectedResponse =
        new EffectsPreviewsResponse(
            "DONE",
            List.of(
                new ImageWithEffect("testId1", "testUrl1", "effect1"),
                new ImageWithEffect("testId2", "testUrl2", "effect2")));
    var httpClientResponse200 = mock(HttpClientResponse.class);
    when(httpClientResponse200.status()).thenReturn(new HttpResponseStatus(200, "OK"));
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.EFFECTS_PREVIEWS.url()),
            API_KEY,
            VALID_EFFECTS_PREVIEWS_REQUEST,
            TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = imageApiClient.effectsPreviews(CONFIG, VALID_EFFECTS_PREVIEWS_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectNextMatches(
            response ->
                response.getHttpClientResponse().status().code() == 200
                    && response.getBody().equals(expectedResponse))
        .verifyComplete();
  }

  @DisplayName("Should return error Mono when effectsPreviews is called with invalid request")
  @Test
  public void shouldReturnErrorMonoWhenEffectsPreviewsIsCalledWithInvalidRequest() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.EFFECTS_PREVIEWS.url()),
            API_KEY,
            INVALID_EFFECTS_PREVIEWS_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.empty());

    // When
    var actualResponse = imageApiClient.effectsPreviews(CONFIG, INVALID_EFFECTS_PREVIEWS_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof IllegalArgumentException
                    && throwable
                        .getMessage()
                        .equals(
                            ApiActions.EFFECTS_PREVIEWS.actionName()
                                + " failed with errors: At least one effect name must be set,"
                                + " Exactly one image source must be set"))
        .verify();
  }

  @DisplayName(
      "Should return error Mono when effectsPreviews is called and downstream service is unavailable")
  @Test
  public void shouldReturnErrorMonoWhenEffectsPreviewsIsCalledAndDownstreamServiceIsUnavailable() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.EFFECTS_PREVIEWS.url()),
            API_KEY,
            VALID_EFFECTS_PREVIEWS_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.error(new ServiceUnavailableException("Service Unavailable", METADATA)));

    // When
    var actualResponse = imageApiClient.effectsPreviews(CONFIG, VALID_EFFECTS_PREVIEWS_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof ServiceUnavailableException
                    && throwable.getMessage().equals("Service Unavailable"))
        .verify();
  }

  @DisplayName("Should return AdjustResponse when adjust is called with valid request")
  @Test
  public void shouldReturnAdjustResponseWhenAdjustIsCalledWithValidRequest() {
    // Given
    var validResponseBody =
        """
                {
                    "status": "DONE",
                    "data": {
                            "id": "testId",
                            "url": "testUrl"
                    }
                }
                """;
    var expectedResponse = new AdjustResponse("DONE", new Image("testId", "testUrl"));
    var httpClientResponse200 = mock(HttpClientResponse.class);
    when(httpClientResponse200.status()).thenReturn(new HttpResponseStatus(200, "OK"));
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.ADJUST.url()),
            API_KEY,
            VALID_ADJUST_REQUEST,
            TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = imageApiClient.adjust(CONFIG, VALID_ADJUST_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectNextMatches(
            response ->
                response.getHttpClientResponse().status().code() == 200
                    && response.getBody().equals(expectedResponse))
        .verifyComplete();
  }

  @DisplayName("Should return error Mono when adjust is called with invalid request")
  @Test
  public void shouldReturnErrorMonoWhenAdjustIsCalledWithInvalidRequest() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.ADJUST.url()),
            API_KEY,
            INVALID_ADJUST_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.empty());

    // When
    var actualResponse = imageApiClient.adjust(CONFIG, INVALID_ADJUST_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof IllegalArgumentException
                    && throwable
                        .getMessage()
                        .equals(
                            ApiActions.ADJUST.actionName()
                                + " failed with errors: Brightness must be in range [-100, 100],"
                                + " Clarity must be in range [-100, 100],"
                                + " Contrast must be in range [-100, 100],"
                                + " Exactly one image source must be set,"
                                + " Highlights must be in range [-100, 100],"
                                + " Hue must be in range [-100, 100],"
                                + " Noise must be in range [0, 100],"
                                + " Saturation must be in range [-100, 100],"
                                + " Shadows must be in range [-100, 100],"
                                + " Sharpen must be in range [0, 100],"
                                + " Temperature must be in range [-100, 100],"
                                + " Vignette must be in range [0, 100]"))
        .verify();
  }

  @DisplayName(
      "Should return error Mono when adjust is called and downstream service is unavailable")
  @Test
  public void shouldReturnErrorMonoWhenAdjustIsCalledAndDownstreamServiceIsUnavailable() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.ADJUST.url()),
            API_KEY,
            VALID_ADJUST_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.error(new ServiceUnavailableException("Service Unavailable", METADATA)));

    // When
    var actualResponse = imageApiClient.adjust(CONFIG, VALID_ADJUST_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof ServiceUnavailableException
                    && throwable.getMessage().equals("Service Unavailable"))
        .verify();
  }

  @DisplayName(
      "Should return TextureGeneratorResponse when textureGenerator is called with valid request")
  @Test
  public void shouldReturnTextureGeneratorResponseWhenBackgroundTextureIsCalledWithValidRequest() {
    // Given
    var validResponseBody =
        """
                {
                    "status": "DONE",
                    "data": {
                            "id": "testId",
                            "url": "testUrl"
                    }
                }
                """;
    var expectedResponse = new BackgroundTextureResponse("DONE", new Image("testId", "testUrl"));
    var httpClientResponse200 = mock(HttpClientResponse.class);
    when(httpClientResponse200.status()).thenReturn(new HttpResponseStatus(200, "OK"));
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.BACKGROUND_TEXTURE.url()),
            API_KEY,
            VALID_TEXTURE_GENERATOR_REQUEST,
            TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = imageApiClient.backgroundTexture(CONFIG, VALID_TEXTURE_GENERATOR_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectNextMatches(
            response ->
                response.getHttpClientResponse().status().code() == 200
                    && response.getBody().equals(expectedResponse))
        .verifyComplete();
  }

  @DisplayName("Should return error Mono when textureGenerator is called with invalid request")
  @Test
  public void shouldReturnErrorMonoWhenBackgroundTextureIsCalledWithInvalidRequest() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.BACKGROUND_TEXTURE.url()),
            API_KEY,
            INVALID_TEXTURE_GENERATOR_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.empty());

    // When
    var actualResponse =
        imageApiClient.backgroundTexture(CONFIG, INVALID_TEXTURE_GENERATOR_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof IllegalArgumentException
                    && throwable
                        .getMessage()
                        .equals(
                            ApiActions.BACKGROUND_TEXTURE.actionName()
                                + " failed with errors: Exactly one image source must be set,"
                                + " Height must be less than 8000,"
                                + " Rotate must be in range [-180, 180],"
                                + " Scale must be in range [0.5, 10],"
                                + " Width must be greater than 0"))
        .verify();
  }

  @DisplayName(
      "Should return error Mono when textureGenerator is called and downstream service is unavailable")
  @Test
  public void
      shouldReturnErrorMonoWhenBackgroundTextureIsCalledAndDownstreamServiceIsUnavailable() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.BACKGROUND_TEXTURE.url()),
            API_KEY,
            VALID_TEXTURE_GENERATOR_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.error(new ServiceUnavailableException("Service Unavailable", METADATA)));

    // When
    var actualResponse = imageApiClient.backgroundTexture(CONFIG, VALID_TEXTURE_GENERATOR_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof ServiceUnavailableException
                    && throwable.getMessage().equals("Service Unavailable"))
        .verify();
  }

  @DisplayName("Should return SurfaceMapResponse when surfaceMap is called with valid request")
  @Test
  public void shouldReturnSurfaceMapResponseWhenSurfaceMapIsCalledWithValidRequest() {
    // Given
    var validResponseBody =
        """
                {
                    "status": "DONE",
                    "data": {
                            "id": "testId",
                            "url": "testUrl"
                    }
                }
                """;
    var expectedResponse = new SurfaceMapResponse("DONE", new Image("testId", "testUrl"));
    var httpClientResponse200 = mock(HttpClientResponse.class);
    when(httpClientResponse200.status()).thenReturn(new HttpResponseStatus(200, "OK"));
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.SURFACE_MAP.url()),
            API_KEY,
            VALID_SURFACE_MAP_REQUEST,
            TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = imageApiClient.surfaceMap(CONFIG, VALID_SURFACE_MAP_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectNextMatches(
            response ->
                response.getHttpClientResponse().status().code() == 200
                    && response.getBody().equals(expectedResponse))
        .verifyComplete();
  }

  @DisplayName("Should return error Mono when surfaceMap is called with invalid request")
  @Test
  public void shouldReturnErrorMonoWhenSurfaceMapIsCalledWithInvalidRequest() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.SURFACE_MAP.url()),
            API_KEY,
            INVALID_SURFACE_MAP_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.empty());

    // When
    var actualResponse = imageApiClient.surfaceMap(CONFIG, INVALID_SURFACE_MAP_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof IllegalArgumentException
                    && throwable
                        .getMessage()
                        .equals(
                            ApiActions.SURFACE_MAP.actionName()
                                + " failed with errors: Exactly one image source must be set,"
                                + " Exactly one mask source must be set,"
                                + " Exactly one sticker source must be set"))
        .verify();
  }

  @DisplayName(
      "Should return error Mono when surfaceMap is called and downstream service is unavailable")
  @Test
  public void shouldReturnErrorMonoWhenSurfaceMapIsCalledAndDownstreamServiceIsUnavailable() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.SURFACE_MAP.url()),
            API_KEY,
            VALID_SURFACE_MAP_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.error(new ServiceUnavailableException("Service Unavailable", METADATA)));

    // When
    var actualResponse = imageApiClient.surfaceMap(CONFIG, VALID_SURFACE_MAP_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof ServiceUnavailableException
                    && throwable.getMessage().equals("Service Unavailable"))
        .verify();
  }

  @DisplayName("Should return UploadResponse when upload is called with valid request")
  @Test
  public void shouldReturnUploadResponseWhenUploadIsCalledWithValidRequest() {
    // Given
    var validResponseBody =
        """
                {
                    "status": "DONE",
                    "data": {
                            "id": "testId",
                            "url": "testUrl"
                    }
                }
                """;
    var expectedResponse = new UploadResponse("DONE", new Image("testId", "testUrl"));
    var httpClientResponse200 = mock(HttpClientResponse.class);
    when(httpClientResponse200.status()).thenReturn(new HttpResponseStatus(200, "OK"));
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.UPLOAD.url()),
            API_KEY,
            VALID_UPLOAD_REQUEST,
            TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = imageApiClient.upload(CONFIG, VALID_UPLOAD_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectNextMatches(
            response ->
                response.getHttpClientResponse().status().code() == 200
                    && response.getBody().equals(expectedResponse))
        .verifyComplete();
  }

  @DisplayName("Should return error Mono when upload is called with invalid request")
  @Test
  public void shouldReturnErrorMonoWhenUploadIsCalledWithInvalidRequest() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.UPLOAD.url()),
            API_KEY,
            INVALID_UPLOAD_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.empty());

    // When
    var actualResponse = imageApiClient.upload(CONFIG, INVALID_UPLOAD_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof IllegalArgumentException
                    && throwable
                        .getMessage()
                        .equals(
                            ApiActions.UPLOAD.actionName()
                                + " failed with errors: Exactly one image source must be set"))
        .verify();
  }

  @DisplayName(
      "Should return error Mono when upload is called and downstream service is unavailable")
  @Test
  public void shouldReturnErrorMonoWhenUploadIsCalledAndDownstreamServiceIsUnavailable() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.UPLOAD.url()),
            API_KEY,
            VALID_UPLOAD_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.error(new ServiceUnavailableException("Service Unavailable", METADATA)));

    // When
    var actualResponse = imageApiClient.upload(CONFIG, VALID_UPLOAD_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof ServiceUnavailableException
                    && throwable.getMessage().equals("Service Unavailable"))
        .verify();
  }

  // balance
  @DisplayName("Should return BalanceResponse when balance is called")
  @Test
  void shouldReturnBalanceResponseWhenBalanceIsCalled() {
    // Given
    var validResponseBody =
        """
                {
                    "credits": 100
                }
                """;
    var expectedResponse = new BalanceResponse(100);
    var httpClientResponse200 = mock(HttpClientResponse.class);
    when(httpClientResponse200.status()).thenReturn(new HttpResponseStatus(200, "OK"));
    when(apiHttpClient.sendGetRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.BALANCE.url()), API_KEY, TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = imageApiClient.balance(CONFIG);

    // Then
    StepVerifier.create(actualResponse)
        .expectNextMatches(
            response ->
                response.getHttpClientResponse().status().code() == 200
                    && response.getBody().equals(expectedResponse))
        .verifyComplete();
  }

  @DisplayName(
      "Should return error Mono when balance is called and downstream service is unavailable")
  @Test
  void shouldReturnErrorMonoWhenBalanceIsCalledAndDownstreamServiceIsUnavailable() {
    // Given
    when(apiHttpClient.sendGetRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.BALANCE.url()), API_KEY, TIMEOUT))
        .thenReturn(Mono.error(new ServiceUnavailableException("Service Unavailable", METADATA)));

    // When
    var actualResponse = imageApiClient.balance(CONFIG);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof ServiceUnavailableException
                    && throwable.getMessage().equals("Service Unavailable"))
        .verify();
  }
}
