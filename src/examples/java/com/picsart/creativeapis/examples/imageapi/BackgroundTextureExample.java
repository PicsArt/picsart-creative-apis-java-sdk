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
import com.picsart.creativeapis.busobj.image.BackgroundTexturePattern;
import com.picsart.creativeapis.busobj.image.ImageFormat;
import com.picsart.creativeapis.busobj.image.ImageSource;
import com.picsart.creativeapis.busobj.image.parameters.BackgroundTextureParameters;
import com.picsart.creativeapis.busobj.image.result.BackgroundTextureResult;
import com.picsart.creativeapis.image.ImageApi;
import java.util.concurrent.CountDownLatch;
import reactor.core.publisher.Mono;

public class BackgroundTextureExample {
  public static void main(String[] args) throws InterruptedException {
    ImageApi imageApi = PicsartEnterprise.createImageApi("YOUR_API_KEY");
    ImageSource mainImage = ImageSource.fromUrl("https://url-to-your-image.jpg");

    // Create a CountDownLatch to keep the main thread alive (not for production code)
    CountDownLatch latch = new CountDownLatch(1);

    BackgroundTextureParameters parameters =
        BackgroundTextureParameters.builder(mainImage)
            .format(ImageFormat.PNG)
            .width(800)
            .height(800)
            .offsetX(5)
            .offsetY(5)
            .pattern(BackgroundTexturePattern.DIAMOND)
            .rotate(40)
            .scale(1.5f)
            .build();
    Mono<BackgroundTextureResult> resultMono = imageApi.backgroundTexture(parameters);
    resultMono
        .doFinally(
            signalType -> latch.countDown()) // Release the main thread (not for production code)
        .subscribe(
            result -> { // non-blocking subscribe
              System.out.println("Result Image: " + result.image());
              System.out.println("Result metadata traceId: " + result.metadata().traceId());
              System.out.println("Result metadata rateLimit: " + result.metadata().rateLimit());
              System.out.println(
                  "Result metadata rateLimitRemaining: " + result.metadata().rateLimitRemaining());
              System.out.println(
                  "Result metadata rateLimitReset: " + result.metadata().rateLimitReset());
              System.out.println(
                  "Result metadata creditAvailable: " + result.metadata().creditAvailable());
            });
    // Keep the main thread alive for the example to finish (not for production code)
    latch.await();
  }
}
