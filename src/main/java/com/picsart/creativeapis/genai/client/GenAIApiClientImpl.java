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

package com.picsart.creativeapis.genai.client;

import static com.picsart.creativeapis.utils.Constants.*;
import static com.picsart.creativeapis.utils.ValidationUtils.*;

import com.picsart.creativeapis.AbstractApiClient;
import com.picsart.creativeapis.busobj.ApiActions;
import com.picsart.creativeapis.busobj.ApiConfig;
import com.picsart.creativeapis.busobj.HttpResponseWithBody;
import com.picsart.creativeapis.busobj.genai.config.GenAIApiClientConfig;
import com.picsart.creativeapis.busobj.genai.request.Text2ImageRequest;
import com.picsart.creativeapis.busobj.genai.response.Text2ImageMiddleResponse;
import com.picsart.creativeapis.busobj.genai.response.Text2ImageResponse;
import com.picsart.creativeapis.http.ApiHttpClient;
import com.picsart.creativeapis.utils.UrlUtils;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class GenAIApiClientImpl extends AbstractApiClient implements GenAIApiClient {
  GenAIApiClientConfig clientConfig;

  public GenAIApiClientImpl(
      @NonNull ApiHttpClient apiHttpClient, GenAIApiClientConfig clientConfig) {
    super(apiHttpClient);
    this.clientConfig = clientConfig;
  }

  @Override
  public Mono<HttpResponseWithBody<Text2ImageResponse>> text2Image(
      ApiConfig config, Text2ImageRequest request) {
    var apiKey = config.apiKey();
    var baseUrl = config.baseUrl();
    var validateRequestMono = validateRequestMono(request, ApiActions.TEXT2IMAGE.actionName());
    var sendRequestMono =
        apiHttpClient
            .sendPostRequest(
                UrlUtils.appendBaseUrl(baseUrl, ApiActions.TEXT2IMAGE.url()),
                apiKey,
                request,
                config.timeout())
            .map(response -> response.parseBody(Text2ImageMiddleResponse.class))
            .map(HttpResponseWithBody::getBody)
            .map(Text2ImageMiddleResponse::inferenceId)
            .delayElement(clientConfig.text2ImagePollingFirstDelay())
            .flatMap(id -> getText2ImageAsyncResult(config, id));
    return validateRequestMono.then(sendRequestMono);
  }

  // TODO: use getAsyncResponse method from AbstractApiClient when status status issue will be fixed
  private Mono<HttpResponseWithBody<Text2ImageResponse>> getText2ImageAsyncResult(
      ApiConfig config, String id) {
    return apiHttpClient
        .sendGetRequest(
            UrlUtils.appendBaseUrl(
                config.baseUrl(),
                ApiActions.TEXT2IMAGE.url() + SLASH + INFERENCES_URL.formatted(id)),
            config.apiKey(),
            config.timeout())
        .map(response -> response.parseBody(Text2ImageResponse.class))
        .filter(
            httpResponseWithParsedBody ->
                "DONE".equalsIgnoreCase(httpResponseWithParsedBody.getBody().status()))
        .repeatWhenEmpty(
            clientConfig.text2ImagePollingRepeatCount(),
            repeat -> repeat.delayElements(clientConfig.text2ImagePollingRepeatDelay()));
  }
}
