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

package com.picsart.creativeapis.utils;

import com.picsart.creativeapis.busobj.exception.*;
import com.picsart.creativeapis.busobj.mapper.MetadataMapper;
import com.picsart.creativeapis.busobj.result.Metadata;
import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.Map;
import java.util.function.BiFunction;
import lombok.experimental.UtilityClass;
import reactor.netty.http.client.HttpClientResponse;

@UtilityClass
public class ExceptionUtils {
  final Map<HttpResponseStatus, BiFunction<String, Metadata, FailureResponseException>>
      HTTP_RESPONSE_TO_EXCEPTION_MAP;

  static {
    HTTP_RESPONSE_TO_EXCEPTION_MAP =
        Map.ofEntries(
            Map.entry(HttpResponseStatus.BAD_REQUEST, BadRequestException::new),
            Map.entry(HttpResponseStatus.UNAUTHORIZED, UnauthorizedException::new),
            Map.entry(HttpResponseStatus.FORBIDDEN, ForbiddenException::new),
            Map.entry(HttpResponseStatus.NOT_FOUND, NotFoundException::new),
            Map.entry(HttpResponseStatus.METHOD_NOT_ALLOWED, MethodNotAllowedException::new),
            Map.entry(HttpResponseStatus.REQUEST_TIMEOUT, RequestTimeoutException::new),
            Map.entry(
                HttpResponseStatus.REQUEST_ENTITY_TOO_LARGE, RequestEntityTooLargeException::new),
            Map.entry(
                HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE, UnsupportedMediaTypeException::new),
            Map.entry(HttpResponseStatus.TOO_MANY_REQUESTS, TooManyRequestsException::new),
            Map.entry(
                HttpResponseStatus.REQUEST_HEADER_FIELDS_TOO_LARGE,
                RequestHeaderFieldsTooLargeException::new),
            Map.entry(HttpResponseStatus.INTERNAL_SERVER_ERROR, InternalServerErrorException::new),
            Map.entry(HttpResponseStatus.BAD_GATEWAY, BadGatewayException::new),
            Map.entry(HttpResponseStatus.SERVICE_UNAVAILABLE, ServiceUnavailableException::new),
            Map.entry(HttpResponseStatus.GATEWAY_TIMEOUT, GatewayTimeoutException::new));
  }

  public FailureResponseException mapToFailureResponseException(
      String message, HttpClientResponse response) {
    var metadata = MetadataMapper.INSTANCE.toMetadata(response);
    var status = response.status();
    return HTTP_RESPONSE_TO_EXCEPTION_MAP
        .getOrDefault(
            status, (mess, meta) -> new FailureResponseException(message, status, metadata))
        .apply(message, metadata);
  }
}
