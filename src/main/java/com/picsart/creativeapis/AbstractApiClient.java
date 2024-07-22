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

package com.picsart.creativeapis;

import com.google.common.annotations.VisibleForTesting;
import com.picsart.creativeapis.busobj.ApiConfig;
import com.picsart.creativeapis.busobj.HttpResponseWithStringBody;
import com.picsart.creativeapis.http.ApiHttpClient;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public abstract class AbstractApiClient {
  ApiHttpClient apiHttpClient;

  @NotNull
  private static String removeTailingSlashIfAny(String baseUrl) {
    return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
  }

  @VisibleForTesting
  public static String appendBaseUrl(String baseUrl, String url) {
    return "%s/%s".formatted(removeTailingSlashIfAny(baseUrl), url);
  }

  protected Mono<HttpResponseWithStringBody> getAsyncResponse(
      ApiConfig config, String url, int repeatCount, Duration delay) {
    return apiHttpClient
        .sendGetRequest(appendBaseUrl(config.baseUrl(), url), config.apiKey(), config.timeout())
        .filter(
            httpResponseWithParsedBody -> {
              var code = httpResponseWithParsedBody.getHttpClientResponse().status().code();
              return code == 200;
            })
        .repeatWhenEmpty(repeatCount, repeat -> repeat.delayElements(delay));
  }
}
