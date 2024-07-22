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

import com.picsart.creativeapis.busobj.ApiConfig;
import com.picsart.creativeapis.busobj.genai.mapper.ParametersMapper;
import com.picsart.creativeapis.busobj.genai.mapper.ResponseMapper;
import com.picsart.creativeapis.busobj.genai.parameters.Text2ImageParameters;
import com.picsart.creativeapis.busobj.genai.result.Text2ImageResult;
import com.picsart.creativeapis.genai.client.GenAIApiClient;
import java.time.Duration;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.With;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public final class GenAIApiImpl implements GenAIApi {

  @With(AccessLevel.PRIVATE)
  ApiConfig config;

  GenAIApiClient client;

  @Override
  public GenAIApi withApiKey(String apiKey) {
    return withConfig(config.withApiKey(apiKey));
  }

  @Override
  public GenAIApi withBaseUrl(String baseUrl) {
    return withConfig(config.withBaseUrl(baseUrl));
  }

  @Override
  public GenAIApi withResponseTimeout(Duration timeout) {
    return withConfig(config.withTimeout(timeout));
  }

  @Override
  public Mono<Text2ImageResult> text2Image(Text2ImageParameters request) {
    return client
        .text2Image(config, ParametersMapper.INSTANCE.toRequest(request))
        .map(
            response ->
                ResponseMapper.INSTANCE.toResult(
                    response.getBody(), response.getHttpClientResponse()));
  }
}
