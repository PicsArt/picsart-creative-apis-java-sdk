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

package com.picsart.creativeapis.busobj.mapper;

import com.picsart.creativeapis.busobj.result.Metadata;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import reactor.netty.http.client.HttpClientResponse;

@Mapper
public interface MetadataMapper {
  MetadataMapper INSTANCE = Mappers.getMapper(MetadataMapper.class);

  String RATE_LIMIT = "X-Picsart-Ratelimit-Limit";
  String RATE_LIMIT_REMAINING = "X-Picsart-Ratelimit-Available";
  String RATE_LIMIT_RESET = "X-Picsart-Ratelimit-Reset-Time";
  String CORRELATION_ID = "x-picsart-correlation-id";
  String CREDIT_AVAILABLE = "x-picsart-credit-available";

  default Metadata toMetadata(HttpClientResponse httpClientResponse) {
    var rateLimit = httpClientResponse.responseHeaders().getInt(RATE_LIMIT);
    var rateLimitRemaining = httpClientResponse.responseHeaders().getInt(RATE_LIMIT_REMAINING);
    var rateLimitReset = httpClientResponse.responseHeaders().getInt(RATE_LIMIT_RESET);
    var traceId = httpClientResponse.responseHeaders().get(CORRELATION_ID);
    var creditAvailable = httpClientResponse.responseHeaders().getInt(CREDIT_AVAILABLE);
    return new Metadata(rateLimit, rateLimitRemaining, rateLimitReset, traceId, creditAvailable);
  }
}
