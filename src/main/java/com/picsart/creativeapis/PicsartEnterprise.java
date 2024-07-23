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

import com.picsart.creativeapis.busobj.ApiConfig;
import com.picsart.creativeapis.busobj.genai.config.GenAIApiClientConfig;
import com.picsart.creativeapis.busobj.image.config.ImageApiClientConfig;
import com.picsart.creativeapis.genai.GenAIApi;
import com.picsart.creativeapis.genai.GenAIApiImpl;
import com.picsart.creativeapis.genai.client.GenAIApiClientImpl;
import com.picsart.creativeapis.http.ApiHttpClient;
import com.picsart.creativeapis.http.ApiHttpClientImpl;
import com.picsart.creativeapis.image.ImageApi;
import com.picsart.creativeapis.image.ImageApiImpl;
import com.picsart.creativeapis.image.client.ImageApiClientImpl;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/** This class provides methods to create instances of ImageApi and GenAIApi. */
public class PicsartEnterprise {
  private static final String DEFAULT_IMAGE_API_BASE_URL = "https://api.picsart.io/tools/1.0";
  private static final String DEFAULT_GEN_AI_API_BASE_URL = "https://genai-api.picsart.io/v1";
  private static final Duration DEFAULT_CONNECTION_TIMEOUT = Duration.of(60, ChronoUnit.SECONDS);
  private static final ApiHttpClient API_HTTP_CLIENT = new ApiHttpClientImpl();
  private static final ImageApiClientImpl IMAGE_API_CLIENT =
      new ImageApiClientImpl(API_HTTP_CLIENT, ImageApiClientConfig.DEFAULT);
  private static final GenAIApiClientImpl GEN_AI_API_CLIENT =
      new GenAIApiClientImpl(API_HTTP_CLIENT, GenAIApiClientConfig.DEFAULT);

  /**
   * Creates an instance of ImageApi with the specified API key.
   *
   * @param apiKey The API key to use for the ImageApi.
   * @return An instance of ImageApi.
   */
  public static ImageApi createImageApi(String apiKey) {
    return new ImageApiImpl(createApiConfig(apiKey, DEFAULT_IMAGE_API_BASE_URL), IMAGE_API_CLIENT);
  }

  /**
   * Creates an instance of GenAIApi with the specified API key.
   *
   * @param apiKey The API key to use for the GenAIApi.
   * @return An instance of GenAIApi.
   */
  public static GenAIApi createGenAIApi(String apiKey) {
    return new GenAIApiImpl(
        createApiConfig(apiKey, DEFAULT_GEN_AI_API_BASE_URL), GEN_AI_API_CLIENT);
  }

  /**
   * Creates an instance of ApiConfig with the specified API key and base URL.
   *
   * @param apiKey The API key to use for the ApiConfig.
   * @param baseUrl The base URL to use for the ApiConfig.
   * @return An instance of ApiConfig.
   */
  private static ApiConfig createApiConfig(String apiKey, String baseUrl) {
    return new ApiConfig(apiKey, baseUrl, DEFAULT_CONNECTION_TIMEOUT);
  }
}
