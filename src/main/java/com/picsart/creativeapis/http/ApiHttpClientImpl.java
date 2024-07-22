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

package com.picsart.creativeapis.http;

import com.picsart.creativeapis.busobj.HttpResponseWithStringBody;
import com.picsart.creativeapis.busobj.MultipartBodyRequest;
import com.picsart.creativeapis.busobj.exception.ApiException;
import com.picsart.creativeapis.busobj.image.response.ErrorResponse;
import com.picsart.creativeapis.utils.Constants;
import com.picsart.creativeapis.utils.ExceptionUtils;
import com.picsart.creativeapis.utils.JacksonUtils;
import io.netty.handler.codec.http.*;
import java.time.Duration;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ApiHttpClientImpl implements ApiHttpClient {
  HttpClient client;

  public ApiHttpClientImpl() {
    client = HttpClient.create();
    client.warmup().block();
  }

  @Override
  public Mono<HttpResponseWithStringBody> sendGetRequest(
      String url, String apiKey, Duration timeout) {
    var receiver =
        client
            .headers(
                headers -> {
                  addCommonHeaders(apiKey, headers);
                  headers.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
                })
            .responseTimeout(timeout)
            .get()
            .uri(url);
    return handleResponse(receiver, "GET", url, "{no body}");
  }

  @Override
  public Mono<HttpResponseWithStringBody> sendPostRequest(
      String url, String apiKey, Object request, Duration timeout) {
    var isMultipartBodyRequest = request instanceof MultipartBodyRequest;
    var requestSender =
        client
            .headers(
                headers -> {
                  addCommonHeaders(apiKey, headers);
                  if (isMultipartBodyRequest) {
                    headers.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.MULTIPART_FORM_DATA);
                  } else {
                    headers.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
                  }
                })
            .responseTimeout(timeout)
            .post()
            .uri(url);
    HttpClient.ResponseReceiver<?> receiver;
    if (isMultipartBodyRequest) {
      receiver =
          requestSender.sendForm(
              (httpClientRequest, httpClientForm) ->
                  ((MultipartBodyRequest) request).addBodyToForm(httpClientForm));
    } else {
      receiver =
          requestSender.send(ByteBufFlux.fromString(Mono.just(JacksonUtils.toJson(request))));
    }
    return handleResponse(receiver, "POST", url, request);
  }

  private Mono<HttpResponseWithStringBody> handleResponse(
      HttpClient.ResponseReceiver<?> receiver, String method, String url, Object request) {
    return receiver
        .responseSingle(
            (response, byteBufMono) -> {
              var stringBody =
                  byteBufMono
                      .asString()
                      .doOnNext(
                          body ->
                              log.debug(
                                  """
                                            Response received for {} request to '{}'
                                            RequestBody: {}
                                            Response: status: {}, body: {}""",
                                  method,
                                  url,
                                  request,
                                  response.status(),
                                  body));
              if (response.status().codeClass() != HttpStatusClass.SUCCESS) { // not 2xx
                return stringBody
                    .map(responseBody -> JacksonUtils.fromJson(responseBody, ErrorResponse.class))
                    .map(
                        errorResponse ->
                            ExceptionUtils.mapToFailureResponseException(
                                errorResponse.detail(), response))
                    .flatMap(Mono::error);
              }
              // Process the response as normal if it's 2xx
              return stringBody.map(body -> HttpResponseWithStringBody.of(response, body));
            })
        .onErrorMap(
            e -> !(e instanceof ApiException),
            e -> {
              log.error(
                  "Error sending {} request to '{}'\nRequestBody: {}", method, url, request, e);
              return new ApiException("Error sending request", e);
            });
  }

  private static void addCommonHeaders(String apiKey, HttpHeaders headers) {
    headers.add(Constants.API_KEY_HEADER, apiKey);
    headers.add(HttpHeaderNames.ACCEPT, HttpHeaderValues.APPLICATION_JSON);
    headers.add(HttpHeaderNames.USER_AGENT, Constants.USER_AGENT);
  }
}
