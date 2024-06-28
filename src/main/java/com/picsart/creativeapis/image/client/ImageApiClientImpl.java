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

package com.picsart.creativeapis.image.client;

import com.picsart.creativeapis.busobj.image.request.*;
import com.picsart.creativeapis.busobj.image.response.*;
import com.picsart.creativeapis.busobj.mapper.MetadataMapper;
import com.picsart.creativeapis.http.ApiHttpClient;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import com.picsart.creativeapis.busobj.ApiActions;
import com.picsart.creativeapis.busobj.ApiConfig;
import com.picsart.creativeapis.busobj.HttpResponseWithBody;
import com.picsart.creativeapis.busobj.exception.FailureResponseException;
import com.picsart.creativeapis.busobj.image.config.ImageApiClientConfig;
import com.picsart.creativeapis.AbstractApiClient;
import reactor.core.publisher.Mono;

import static com.picsart.creativeapis.utils.Constants.*;
import static com.picsart.creativeapis.utils.ValidationUtils.*;

@Slf4j
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class ImageApiClientImpl extends AbstractApiClient implements ImageApiClient {
    ImageApiClientConfig clientConfig;

    public ImageApiClientImpl(@NonNull ApiHttpClient apiHttpClient, ImageApiClientConfig clientConfig) {
        super(apiHttpClient);
        this.clientConfig = clientConfig;
    }

    @Override
    public Mono<HttpResponseWithBody<RemoveBackgroundResponse>> removeBackground(ApiConfig config,
                                                                                 RemoveBackgroundRequest request) {
        var apiKey = config.apiKey();
        var baseUrl = config.baseUrl();
        var validateRequestMono = validateRequestMono(request, ApiActions.REMOVE_BACKGROUND.actionName());
        var sendRequestMono = apiHttpClient.sendPostRequest(
                appendBaseUrl(baseUrl, ApiActions.REMOVE_BACKGROUND.url()),
                apiKey,
                request,
                config.timeout()
        ).map(response -> response.parseBody(RemoveBackgroundResponse.class));
        return validateRequestMono.then(sendRequestMono);
    }

    @Override
    public Mono<HttpResponseWithBody<EffectResponse>> effect(ApiConfig config,
                                                             EffectRequest request) {
        var apiKey = config.apiKey();
        var baseUrl = config.baseUrl();
        var validateRequestMono = validateRequestMono(request, ApiActions.EFFECT.actionName());
        var sendRequestMono = apiHttpClient.sendPostRequest(
                appendBaseUrl(baseUrl, ApiActions.EFFECT.url()),
                apiKey,
                request,
                config.timeout()
        ).map(response -> response.parseBody(EffectResponse.class));
        return validateRequestMono.then(sendRequestMono);
    }

    @Override
    public Mono<HttpResponseWithBody<ListEffectsResponse>> listEffects(ApiConfig config) {
        var apiKey = config.apiKey();
        var baseUrl = config.baseUrl();
        return apiHttpClient.sendGetRequest(
                appendBaseUrl(baseUrl, ApiActions.LIST_EFFECTS.url()),
                apiKey,
                config.timeout()
        ).map(response -> response.parseBody(ListEffectsResponse.class));
    }

    @Override
    public Mono<HttpResponseWithBody<UltraUpscaleResponse>> ultraUpscale(ApiConfig config,
                                                                         UltraUpscaleRequest request) {
        var apiKey = config.apiKey();
        var baseUrl = config.baseUrl();
        var validateRequestMono = validateRequestMono(request, ApiActions.ULTRA_UPSCALE.actionName());
        var sendRequestMono = apiHttpClient.sendPostRequest(
                appendBaseUrl(baseUrl, ApiActions.ULTRA_UPSCALE.url()),
                apiKey,
                request,
                config.timeout()
        ).flatMap(responseWithStringBody -> {
            var status = responseWithStringBody.getHttpClientResponse().status();
            if (HttpResponseStatus.OK.equals(status)) {
                return Mono.just(responseWithStringBody.parseBody(UltraUpscaleResponse.class));
            } else if (HttpResponseStatus.ACCEPTED.equals(status)) {
                var middleResponse = responseWithStringBody.parseBody(UpscaleUltraMiddleResponse.class).getBody();
                return Mono.delay(clientConfig.upscaleUltraPollingFirstDelay())
                        .then(getUpscaleUltraAsyncResponse(config, middleResponse));
            }
            var metadata = MetadataMapper.INSTANCE.toMetadata(responseWithStringBody.getHttpClientResponse());
            return Mono.error(new FailureResponseException("Unexpected response status", status, metadata));
        });
        return validateRequestMono.then(sendRequestMono);
    }

    @Override
    public Mono<HttpResponseWithBody<UpscaleResponse>> upscale(ApiConfig config, UpscaleRequest request) {
        var apiKey = config.apiKey();
        var baseUrl = config.baseUrl();
        var validateRequestMono = validateRequestMono(request, ApiActions.UPSCALE.actionName());
        var sendRequestMono = apiHttpClient.sendPostRequest(
                appendBaseUrl(baseUrl, ApiActions.UPSCALE.url()),
                apiKey,
                request,
                config.timeout()
        ).map(response -> response.parseBody(UpscaleResponse.class));
        return validateRequestMono.then(sendRequestMono);
    }

    @Override
    public Mono<HttpResponseWithBody<UltraEnhanceResponse>> ultraEnhance(ApiConfig config,
                                                                         UltraEnhanceRequest request) {
        var apiKey = config.apiKey();
        var baseUrl = config.baseUrl();
        var validateRequestMono = validateRequestMono(request, ApiActions.ULTRA_ENHANCE.actionName());
        var sendRequestMono = apiHttpClient.sendPostRequest(
                appendBaseUrl(baseUrl, ApiActions.ULTRA_ENHANCE.url()),
                apiKey,
                request,
                config.timeout()
        ).map(response -> response.parseBody(UltraEnhanceResponse.class));
        return validateRequestMono.then(sendRequestMono);
    }

    @Override
    public Mono<HttpResponseWithBody<EnhanceFaceResponse>> enhanceFace(ApiConfig config,
                                                                       EnhanceFaceRequest request) {
        var apiKey = config.apiKey();
        var baseUrl = config.baseUrl();
        var validateRequestMono = validateRequestMono(request, ApiActions.ENHANCE_FACE.actionName());
        var sendRequestMono = apiHttpClient.sendPostRequest(
                appendBaseUrl(baseUrl, ApiActions.ENHANCE_FACE.url()),
                apiKey,
                request,
                config.timeout()
        ).map(response -> response.parseBody(EnhanceFaceResponse.class));
        return validateRequestMono.then(sendRequestMono);
    }

    @Override
    public Mono<HttpResponseWithBody<EffectsPreviewsResponse>> effectsPreviews(ApiConfig config,
                                                                               EffectsPreviewsRequest request) {
        var apiKey = config.apiKey();
        var baseUrl = config.baseUrl();
        var validateRequestMono = validateRequestMono(request, ApiActions.EFFECTS_PREVIEWS.actionName());
        var sendRequestMono = apiHttpClient.sendPostRequest(
                appendBaseUrl(baseUrl, ApiActions.EFFECTS_PREVIEWS.url()),
                apiKey,
                request,
                config.timeout()
        ).map(response -> response.parseBody(EffectsPreviewsResponse.class));
        return validateRequestMono.then(sendRequestMono);
    }

    @Override
    public Mono<HttpResponseWithBody<AdjustResponse>> adjust(ApiConfig config, AdjustRequest request) {
        var apiKey = config.apiKey();
        var baseUrl = config.baseUrl();
        var validateRequestMono = validateRequestMono(request, ApiActions.ADJUST.actionName());
        var sendRequestMono = apiHttpClient.sendPostRequest(
                appendBaseUrl(baseUrl, ApiActions.ADJUST.url()),
                apiKey,
                request,
                config.timeout()
        ).map(response -> response.parseBody(AdjustResponse.class));
        return validateRequestMono.then(sendRequestMono);
    }

    @Override
    public Mono<HttpResponseWithBody<BackgroundTextureResponse>> backgroundTexture(ApiConfig config,
                                                                                   BackgroundTextureRequest request) {
        var apiKey = config.apiKey();
        var baseUrl = config.baseUrl();
        var validateRequestMono = validateRequestMono(request, ApiActions.BACKGROUND_TEXTURE.actionName());
        var sendRequestMono = apiHttpClient.sendPostRequest(
                appendBaseUrl(baseUrl, ApiActions.BACKGROUND_TEXTURE.url()),
                apiKey,
                request,
                config.timeout()
        ).map(response -> response.parseBody(BackgroundTextureResponse.class));
        return validateRequestMono.then(sendRequestMono);
    }

    @Override
    public Mono<HttpResponseWithBody<SurfaceMapResponse>> surfaceMap(ApiConfig config, SurfaceMapRequest request) {
        var apiKey = config.apiKey();
        var baseUrl = config.baseUrl();
        var validateRequestMono = validateRequestMono(request, ApiActions.SURFACE_MAP.actionName());
        var sendRequestMono = apiHttpClient.sendPostRequest(
                appendBaseUrl(baseUrl, ApiActions.SURFACE_MAP.url()),
                apiKey,
                request,
                config.timeout()
        ).map(response -> response.parseBody(SurfaceMapResponse.class));
        return validateRequestMono.then(sendRequestMono);
    }

    @Override
    public Mono<HttpResponseWithBody<UploadResponse>> upload(ApiConfig config, UploadRequest request) {
        var apiKey = config.apiKey();
        var baseUrl = config.baseUrl();
        var validateRequestMono = validateRequestMono(request, ApiActions.UPLOAD.actionName());
        var sendRequestMono = apiHttpClient.sendPostRequest(
                appendBaseUrl(baseUrl, ApiActions.UPLOAD.url()),
                apiKey,
                request,
                config.timeout()
        ).map(response -> response.parseBody(UploadResponse.class));
        return validateRequestMono.then(sendRequestMono);
    }

    @Override
    public Mono<HttpResponseWithBody<BalanceResponse>> balance(ApiConfig config) {
        var apiKey = config.apiKey();
        var baseUrl = config.baseUrl();
        return apiHttpClient.sendGetRequest(
                appendBaseUrl(baseUrl, ApiActions.BALANCE.url()),
                apiKey,
                config.timeout()
        ).map(response -> response.parseBody(BalanceResponse.class));
    }

    private Mono<HttpResponseWithBody<UltraUpscaleResponse>> getUpscaleUltraAsyncResponse(
            ApiConfig config,
            UpscaleUltraMiddleResponse upscaleUltraMiddleResponse) {
        var id = upscaleUltraMiddleResponse.transactionId();
        return getAsyncResponse(config, ApiActions.ULTRA_UPSCALE.url() + SLASH + id,
                clientConfig.upscaleUltraPollingRepeatCount(), clientConfig.upscaleUltraPollingRepeatDelay())
                .map(response -> response.parseBody(UltraUpscaleResponse.class));
    }
}
