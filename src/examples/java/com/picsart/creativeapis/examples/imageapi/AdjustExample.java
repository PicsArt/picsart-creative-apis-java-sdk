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

package com.picsart.creativeapis.examples.imageapi;

import com.picsart.creativeapis.PicsartEnterprise;
import com.picsart.creativeapis.busobj.image.ImageFormat;
import com.picsart.creativeapis.busobj.image.ImageSource;
import com.picsart.creativeapis.busobj.image.parameters.AdjustParameters;
import com.picsart.creativeapis.busobj.image.result.AdjustResult;
import com.picsart.creativeapis.image.ImageApi;
import reactor.core.publisher.Mono;

import java.util.concurrent.CountDownLatch;

public class AdjustExample {
    public static void main(String[] args) throws InterruptedException {
        ImageApi imageApi = PicsartEnterprise.createImageApi("YOUR_API_KEY");
        ImageSource mainImage = ImageSource.fromUrl("https://url-to-your-image.jpg");

        // Create a CountDownLatch to keep the main thread alive (not for production code)
        CountDownLatch latch = new CountDownLatch(1);

        AdjustParameters parameters = AdjustParameters.builder(mainImage)
                .format(ImageFormat.PNG)
                .brightness(50)
                .contrast(50)
                .clarity(50)
                .saturation(50)
                .hue(50)
                .shadows(50)
                .highlights(50)
                .temperature(50)
                .sharpen(50)
                .noise(50)
                .vignette(50)
                .build();
        Mono<AdjustResult> resultMono = imageApi.adjust(parameters);
        resultMono
                .doFinally(signalType -> latch.countDown()) // Release the main thread (not for production code)
                .subscribe(result -> { // non-blocking subscribe
                    System.out.println("Result Image: " + result.image());
                    System.out.println("Result metadata traceId: " + result.metadata().traceId());
                    System.out.println("Result metadata rateLimit: " + result.metadata().rateLimit());
                    System.out.println("Result metadata rateLimitRemaining: " + result.metadata().rateLimitRemaining());
                    System.out.println("Result metadata rateLimitReset: " + result.metadata().rateLimitReset());
                    System.out.println("Result metadata creditAvailable: " + result.metadata().creditAvailable());
                });
        // Keep the main thread alive for the example to finish (not for production code)
        latch.await();
    }
}
