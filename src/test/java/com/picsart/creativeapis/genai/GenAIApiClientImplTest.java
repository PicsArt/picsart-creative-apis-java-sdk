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

package com.picsart.creativeapis.genai;

import static com.picsart.creativeapis.utils.Constants.INFERENCES_URL;
import static com.picsart.creativeapis.utils.Constants.SLASH;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.picsart.creativeapis.AbstractApiClient;
import com.picsart.creativeapis.busobj.ApiActions;
import com.picsart.creativeapis.busobj.ApiConfig;
import com.picsart.creativeapis.busobj.HttpResponseWithStringBody;
import com.picsart.creativeapis.busobj.exception.ServiceUnavailableException;
import com.picsart.creativeapis.busobj.genai.config.GenAIApiClientConfig;
import com.picsart.creativeapis.busobj.genai.request.Text2ImageRequest;
import com.picsart.creativeapis.busobj.genai.response.Text2ImageResponse;
import com.picsart.creativeapis.busobj.image.Image;
import com.picsart.creativeapis.busobj.result.Metadata;
import com.picsart.creativeapis.genai.client.GenAIApiClientImpl;
import com.picsart.creativeapis.http.ApiHttpClient;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class GenAIApiClientImplTest {

  private static final Text2ImageRequest INVALID_COUNT_REQUEST =
      new Text2ImageRequest("testPrompt", "testNegativePrompt", 0, -1, 0);
  private static final Text2ImageRequest VALID_REQUEST =
      new Text2ImageRequest("testPrompt", "testNegativePrompt", null, null, null);
  private static final String API_KEY = "apiKey";
  private static final String BASE_URL = "baseUrl";
  private static final Duration TIMEOUT = Duration.ofSeconds(60);
  private static final ApiConfig CONFIG = new ApiConfig(API_KEY, BASE_URL, TIMEOUT);
  @Mock private ApiHttpClient apiHttpClient;

  @Spy
  private GenAIApiClientConfig genAIApiClientConfig =
      GenAIApiClientConfig.builder()
          .text2ImagePollingFirstDelay(Duration.ofMillis(1))
          .text2ImagePollingRepeatDelay(Duration.ofMillis(1))
          .text2ImagePollingRepeatCount(1)
          .build();

  @InjectMocks private GenAIApiClientImpl genAIApiClient;

  @BeforeEach
  public void setup() {
    Hooks.onOperatorDebug();
  }

  @DisplayName("Should return Text2ImageResponse when text2Image is called with valid request")
  @Test
  public void shouldReturnText2ImageResponseWhenText2ImageIsCalledWithValidRequest() {
    // Given
    var validMiddleResponseBody =
        """
                {
                    "inference_id": "testInferenceId"
                }
                """;
    var validResponseBody =
        """
                {
                    "status": "DONE",
                    "data": [
                        {
                            "id": "testId",
                            "url": "testUrl"
                        }
                    ]
                }
                """;
    var httpClientResponse200 = mock(HttpClientResponse.class);
    var httpClientResponse202 = mock(HttpClientResponse.class);
    when(httpClientResponse200.status()).thenReturn(new HttpResponseStatus(200, "OK"));
    var expectedResponse = new Text2ImageResponse("DONE", List.of(new Image("testId", "testUrl")));
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.TEXT2IMAGE.url()),
            API_KEY,
            VALID_REQUEST,
            TIMEOUT))
        .thenReturn(
            Mono.just(
                HttpResponseWithStringBody.of(httpClientResponse202, validMiddleResponseBody)));

    when(apiHttpClient.sendGetRequest(
            AbstractApiClient.appendBaseUrl(
                BASE_URL,
                ApiActions.TEXT2IMAGE.url() + SLASH + INFERENCES_URL.formatted("testInferenceId")),
            API_KEY,
            TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = genAIApiClient.text2Image(CONFIG, VALID_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectNextMatches(
            response ->
                response.getHttpClientResponse().status().code() == 200
                    && response.getBody().equals(expectedResponse))
        .verifyComplete();
  }

  @DisplayName(
      "Should return error response when text2Image fail to finish within the configured repeats")
  @Test
  public void shouldReturnErrorResponseWhenText2ImageFailToFinishWithinConfiguredRepeats() {
    // Given
    var validMiddleResponseBody =
        """
                {
                    "inference_id": "testInferenceId"
                }
                """;
    var validResponseBody =
        """
                {
                    "status": "PROGRESS",
                    "data": [
                    ]
                }
                """;
    var httpClientResponse200 = mock(HttpClientResponse.class);
    var httpClientResponse202 = mock(HttpClientResponse.class);
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.TEXT2IMAGE.url()),
            API_KEY,
            VALID_REQUEST,
            TIMEOUT))
        .thenReturn(
            Mono.just(
                HttpResponseWithStringBody.of(httpClientResponse202, validMiddleResponseBody)));

    when(apiHttpClient.sendGetRequest(
            AbstractApiClient.appendBaseUrl(
                BASE_URL,
                ApiActions.TEXT2IMAGE.url() + SLASH + INFERENCES_URL.formatted("testInferenceId")),
            API_KEY,
            TIMEOUT))
        .thenReturn(
            Mono.just(HttpResponseWithStringBody.of(httpClientResponse200, validResponseBody)));

    // When
    var actualResponse = genAIApiClient.text2Image(CONFIG, VALID_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            error ->
                error instanceof IllegalStateException
                    && error.getMessage().equals("Exceeded maximum number of repeats"))
        .verify();
  }

  @DisplayName("Should return error Mono when text2Image is called with invalid request")
  @Test
  public void shouldReturnErrorMonoWhenText2ImageIsCalledWithInvalidRequest() {
    // Given
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.TEXT2IMAGE.url()),
            API_KEY,
            INVALID_COUNT_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.empty());

    // When
    var actualResponse = genAIApiClient.text2Image(CONFIG, INVALID_COUNT_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof IllegalArgumentException
                    && throwable
                        .getMessage()
                        .equals(
                            ApiActions.TEXT2IMAGE.actionName()
                                + " failed with errors: Count must be greater than 0,"
                                + " Height must be greater than 0, Width must be greater than 0"))
        .verify();
  }

  @DisplayName(
      "Should return error Mono when text2Image is called and downstream service is unavailable")
  @Test
  public void shouldReturnErrorMonoWhenText2ImageIsCalledAndDownstreamServiceIsUnavailable() {
    // Given
    var metadata = new Metadata(null, null, null, null, null);
    when(apiHttpClient.sendPostRequest(
            AbstractApiClient.appendBaseUrl(BASE_URL, ApiActions.TEXT2IMAGE.url()),
            API_KEY,
            VALID_REQUEST,
            TIMEOUT))
        .thenReturn(Mono.error(new ServiceUnavailableException("Service Unavailable", metadata)));

    // When
    var actualResponse = genAIApiClient.text2Image(CONFIG, VALID_REQUEST);

    // Then
    StepVerifier.create(actualResponse)
        .expectErrorMatches(
            throwable ->
                throwable instanceof ServiceUnavailableException
                    && throwable.getMessage().equals("Service Unavailable"))
        .verify();
  }
}
