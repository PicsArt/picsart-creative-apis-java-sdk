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

package com.picsart.creativeapis.examples.genai;

import com.picsart.creativeapis.PicsartEnterprise;
import com.picsart.creativeapis.busobj.genai.parameters.Text2ImageParameters;
import com.picsart.creativeapis.busobj.genai.result.Text2ImageResult;
import com.picsart.creativeapis.genai.GenAIApi;
import java.util.concurrent.CountDownLatch;
import reactor.core.publisher.Mono;

public class Text2ImageExample {
  public static void main(String[] args) throws InterruptedException {
    GenAIApi genAIApi = PicsartEnterprise.createGenAIApi("YOUR_API_KEY");

    // Create a CountDownLatch to keep the main thread alive (not for production code)
    CountDownLatch latch = new CountDownLatch(1);

    Text2ImageParameters parameters =
        Text2ImageParameters.builder("your-prompt", "your-negative-prompt")
            .count(10)
            .width(512)
            .height(512)
            .build();
    Mono<Text2ImageResult> resultMono = genAIApi.text2Image(parameters);
    resultMono
        .doFinally(
            signalType -> latch.countDown()) // Release the main thread (not for production code)
        .subscribe(
            result -> { // non-blocking subscribe
              System.out.println("Result Images: " + result.images());
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
