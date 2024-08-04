# PICSART CREATIVE APIS

## Description

This is a Java SDK of Picsart Programmable Image APIs and Picsart GenAI APIs. 
You can easily do many actions with your images just by adding few lines of code to your Java or Kotlin projects.


## Installation

To use the SDK, add the following dependency to your Maven `pom.xml` file:

```xml
<dependency>
    <groupId>com.picsart</groupId>
    <artifactId>picsart-creative-apis-java-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

If you are using Gradle, add the following to your `build.gradle` file:

```gradle
implementation 'com.picsart:picsart-creative-apis-java-sdk:1.0.0'
```

_**Warning**_: The SDK is using Reactor java and has a transitive dependency on `io.projectreactor:reactor-core`. If you are using a different version of Reactor, you may face compatibility issues. In this case, you can exclude the transitive dependency and add your own version of Reactor to your project.

## Usage

### _Get an API instance_

```java
import com.picsart.creativeapis.PicsartEnterprise;
import com.picsart.creativeapis.image.ImageApi;
import com.picsart.creativeapis.genai.GenAIApi;

// Get an instance of Image API with default baseUrl and timeout
ImageApi imageApi = PicsartEnterprise.createImageApi("YOUR_API_KEY");

// Get an instance of Image API with custom baseUrl and timeout
ImageApi customBaseUrlImageApi = PicsartEnterprise.createImageApi("YOUR_API_KEY")
                        .withBaseUrl("https://custom-base-url.com")
                        .withTimeout(Duration.ofSeconds(10));

// Get an instance of GenAI API
GenAIApi genaiApi = PicsartEnterprise.createGenAIApi("YOUR_API_KEY");

// Get an instance of GenAI API with custom baseUrl and timeout
GenAIApi customBaseUrlGenaiApi = PicsartEnterprise.createGenAIApi("YOUR_API_KEY")
                        .withBaseUrl("https://custom-base-url.com")
                        .withTimeout(Duration.ofSeconds(10));
```

### _Create an ImageSource instance_

```java
import com.picsart.busobj.image.ImageSource;
import com.picsart.creativeapis.image.ImageApi;
import com.picsart.creativeapis.image.models.ImageSource;

import java.io.File;

var imageUrl = ImageSource.fromUrl("https://url-to-your-image.jpg");
// or
var imageFile = ImageSource.fromFile(new File("path/to/your-image.jpg"));
// or
var imageId = ImageSource.fromImageId("your-image-id");

```

### _Remove background_

```java
RemoveBackgroundParameters parameters = RemoveBackgroundParameters.builder(mainImageSource)
        .bgImage(backgroundImageSource)
        .build();
Mono<RemoveBackgroundResult> removeBackgroundResultMono = imageApi.removeBackground(parameters);
removeBackgroundResultMono
        .subscribe(result -> { // non-blocking subscribe
            System.out.println("Result Image: " + result.image());
            System.out.println("Result metadata: " + result.metadata());
        });
```
For more details please check [RemoveBackgroundExample.java](src/examples/java/com/picsart/creativeapis/examples/imageapi/RemoveBackgroundExample.java).

### _Adjust image_

```java
AdjustParameters parameters = AdjustParameters.builder(imageSource)
                .brightness(20)
                .contrast(30)
                .saturation(40)
                .build();
Mono<AdjustResult> adjustResultMono = imageApi.adjust(parameters);
adjustResultMono
        .subscribe(result -> { // non-blocking subscribe
            System.out.println("Result Image: " + result.image());
            System.out.println("Result metadata: " + result.metadata());
        });

```
For more details please check [AdjustExample.java](src/examples/java/com/picsart/creativeapis/examples/imageapi/AdjustExample.java).

### _Apply effect_

```java
EffectParameters parameters = EffectParameters.builder(imageSource, effectName)
        .build();
Mono<EffectResult> resultMono = imageApi.effect(parameters);
resultMono
        .subscribe(result -> { // non-blocking subscribe
            System.out.println("Result Image: " + result.image());
            System.out.println("Result metadata: " + result.metadata());
        });
```

For more details please check [EffectExample.java](src/examples/java/com/picsart/creativeapis/examples/imageapi/EffectExample.java).

### _Get list of effects_

```java
Mono<ListEffectsResult> resultMono = imageApi.listEffects();
resultMono
        .subscribe(result -> { // non-blocking subscribe
            System.out.println("Effects: " + result.effects());
            System.out.println("Result metadata: " + result.metadata());
        });
```

For more details please check [ListEffectsExample.java](src/examples/java/com/picsart/creativeapis/examples/imageapi/ListEffectsExample.java).

//Effects Previews
### _Preview selected effects on an image_

```java
EffectsPreviewsParameters parameters = EffectsPreviewsParameters.builder(imageSource)
                .addEffectName(effectName1)
                .addEffectName(effectName2)
                .addEffectName(effectName3)
                .format(ImageFormat.PNG)
                .build();
Mono<EffectsPreviewsResult> resultMono = imageApi.effectsPreviews(parameters);
resultMono
        .subscribe(result -> { // non-blocking subscribe
            System.out.println("Result images with effects" + result.effectsPreviews());
            System.out.println("Result metadata: " + result.metadata());
        });
```

For more details please check [EffectsPreviewsExample.java](src/examples/java/com/picsart/creativeapis/examples/imageapi/EffectsPreviewsExample.java).

### _Upload Image_

```java
Mono<UploadResult> resultMono = imageApi.upload(imageFile); // either imageFile or imageUrl
resultMono
        .subscribe(result -> { // non-blocking subscribe
            System.out.println("Result Image: " + result.image());
            System.out.println("Result metadata: " + result.metadata());
        });
```

For more details please check [UploadExample.java](src/examples/java/com/picsart/creativeapis/examples/imageapi/UploadExample.java).

### _Upscale_

```java
 UpscaleParameters parameters = UpscaleParameters.builder(imageSource)
        .upscaleFactor(4)
        .format(ImageFormat.PNG)
        .build();
Mono<UpscaleResult> resultMono = imageApi.upscale(parameters);
resultMono
        .subscribe(result -> { // non-blocking subscribe
            System.out.println("Result Image: " + result.image());
            System.out.println("Result metadata: " + result.metadata());
        });
```

For more details please check [UpscaleExample.java](src/examples/java/com/picsart/creativeapis/examples/imageapi/UpscaleExample.java).


### _Ultra Upscale_

```java
UltraUpscaleParameters parameters = UltraUpscaleParameters.builder(imageSource)
        .mode(UpscaleMode.ASYNC)
        .upscaleFactor(4)
        .format(ImageFormat.PNG)
        .build();
Mono<UltraUpscaleResult> resultMono = imageApi.ultraUpscale(parameters);
resultMono
        .subscribe(result -> { // non-blocking subscribe
            System.out.println("Result Image: " + result.image());
            System.out.println("Result metadata: " + result.metadata());
        });
```

For more details please check [UltraUpscaleExample.java](src/examples/java/com/picsart/creativeapis/examples/imageapi/UltraUpscaleExample.java).

### _Ultra Enhance_

```java
UltraEnhanceParameters parameters = UltraEnhanceParameters.builder(imageSource)
        .upscaleFactor(4)
        .format(ImageFormat.PNG)
        .build();
Mono<UltraEnhanceResult> resultMono = imageApi.ultraEnhance(parameters);
resultMono
        .subscribe(result -> { // non-blocking subscribe
            System.out.println("Result Image: " + result.image());
            System.out.println("Result metadata: " + result.metadata());
        });
```

For more details please check [UltraEnhanceExample.java](src/examples/java/com/picsart/creativeapis/examples/imageapi/UltraEnhanceExample.java).

### _Enhance face_
```java
EnhanceFaceParameters parameters = EnhanceFaceParameters.builder(imageSource)
                .format(ImageFormat.PNG)
                .build();
Mono<EnhanceFaceResult> resultMono = imageApi.enhanceFace(parameters);
resultMono
        .subscribe(result -> { // non-blocking subscribe
            System.out.println("Result Image: " + result.image());
            System.out.println("Result metadata: " + result.metadata());
        });
```

For more details please check [EnhanceFaceExample.java](src/examples/java/com/picsart/creativeapis/examples/imageapi/EnhanceFaceExample.java).

### _Surface Map_
```java
SurfaceMapParameters parameters = SurfaceMapParameters.builder(mainImageSource, maskSource, stickerSource)
                .format(ImageFormat.PNG)
                .build();
Mono<SurfaceMapResult> resultMono = imageApi.surfaceMap(parameters);
resultMono
        .subscribe(result -> { // non-blocking subscribe
            System.out.println("Result Image: " + result.image());
            System.out.println("Result metadata: " + result.metadata());
        });
```

For more details please check [SurfaceMapExample.java](src/examples/java/com/picsart/creativeapis/examples/imageapi/SurfaceMapExample.java).

### _Generate background textures_
```java
BackgroundTextureParameters parameters = BackgroundTextureParameters.builder(imageSource)
                .format(ImageFormat.PNG)
                .width(800)
                .height(800)
                .pattern(BackgroundTexturePattern.DIAMOND)
                .build();
        Mono<BackgroundTextureResult> resultMono = imageApi.backgroundTexture(parameters);
        resultMono
                .subscribe(result -> { // non-blocking subscribe
                    System.out.println("Result Image: " + result.image());
                    System.out.println("Result metadata: " + result.metadata());
                });
```

For more details please check [BackgroundTextureExample.java](src/examples/java/com/picsart/creativeapis/examples/imageapi/BackgroundTextureExample.java).

### _Get balance_
```java
Mono<BalanceResult> resultMono = imageApi.balance();
resultMono
        .subscribe(result -> { // non-blocking subscribe
            System.out.println("Result credits: " + result.credits());
        });
```

For more details please check [BalanceExample.java](src/examples/java/com/picsart/creativeapis/examples/imageapi/BalanceExample.java).

### _Generate images from text with GenAI_

```java
Text2ImageParameters parameters = Text2ImageParameters.builder("your-prompt", "your-negative-prompt")
        .count(1)
        .width(512)
        .height(512)
        .build();
Mono<Text2ImageResult> resultMono = genAIApi.text2Image(parameters);
resultMono
        .subscribe(result -> { // non-blocking subscribe
            System.out.println("Result Images: " + result.images());
            System.out.println("Result metadata: " + result.metadata());
        });
```

For more details please check [Text2ImageExample.java](src/examples/java/com/picsart/creativeapis/examples/genai/Text2ImageExample.java).

### _Apply multiple modifications_

In the example below we are generating an image from text, then removing the background from the generated image, and finally upscaling the result.

```java
Text2ImageParameters parameters = Text2ImageParameters.builder("your-prompt", "your-negative-prompt")
        .count(10)
        .width(512)
        .height(512)
        .build();
Mono<Text2ImageResult> resultMono = genAIApi.text2Image(parameters);
resultMono
        .flatMapIterable(Text2ImageResult::images) // flatMapIterable is used to iterate over the result images and process each of them individually
        .flatMap(image -> { // this will be called for each image in the result (10 times in this case)
            RemoveBackgroundParameters removeBackgroundParameters = RemoveBackgroundParameters
                    .builder(image.toImageSource())
                    .build();
            return imageApi.removeBackground(removeBackgroundParameters); // remove background from each image
        })
        .flatMap(removeBackgroundResult -> { // this will be called for each image in the result (10 times in this case)
            var imageSource = removeBackgroundResult.image().toImageSource();
            UpscaleParameters upscaleParameters = UpscaleParameters.builder(imageSource)
                     .upscaleFactor(2)
                     .build();
            return imageApi.upscale(upscaleParameters); // upscale each image
        })
        .subscribe(result -> { // non-blocking subscribe
            // each image will be printed separately
            System.out.println("Result Image: " + result.image());
            System.out.println("Result metadata: " + result.metadata());
        });
```

For more details please check [Text2ImageRemoveBackgroundUpscaleExample.java](src/examples/java/com/picsart/creativeapis/examples/Text2ImageRemoveBackgroundUpscaleExample.java).

### _Error handling and retries_
Api exceptions are handled by the SDK and are thrown as [ApiException](src/main/java/com/picsart/creativeapis/busobj/exception/ApiException.java) or child of it. 
If the response status is other than 2xx, `FailureResponseException` or child of it is thrown according to the response status code.
Exceptions that can potentially be resolved by retrying the request like `ServiceUnavailableException` are marked with [Recoverable interface](src/main/java/com/picsart/creativeapis/busobj/exception/Recoverable.java).
You can catch those exceptions and retry the request if needed.

Example of retrying a request in case of a recoverable exception:
```java
Mono<BalanceResult> resultMono = imageApi.balance();
resultMono
        // can be used to perform a side-effect in case of an error
        .doOnError(throwable -> {
            System.out.println("exception: " + throwable.getMessage());
        })
        // Retry 3 times with an initial backoff of 2 seconds. 
        // The delay between retries will increase exponentially up to max delay of 5 seconds.
        .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                .maxBackoff(Duration.ofSeconds(5))
                 .filter(throwable -> throwable instanceof Recoverable)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> new RuntimeException("Retry exhausted", retrySignal.failure()))
        )
        .subscribe(result -> { // non-blocking subscribe
            System.out.println("Result credits: " + result.credits());
        });
```


# License

Picsart Creative APIs SDK is provided under the MIT license that can be found in the
[LICENSE](./LICENSE) file.
By using, distributing, or contributing to this project, you agree to
the terms and conditions of this license.

This project has some third-party dependencies, each of which may have independent licensing:

- [Jackson Databind:2.17.1](https://github.com/FasterXML/jackson) ([The Apache Software License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt))
- [Guava:33.2.1-jre](https://github.com/google/guava/) ([The Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt))
- [Reactor Netty HTTP:1.1.19](https://github.com/reactor/reactor-netty) ([The Apache Software License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt))
- [Reactor Core:3.6.6](https://github.com/reactor/reactor-core) ([The Apache Software License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt))
- [Jakarta Validation API:3.1.0](https://www.eclipse.org) ([The Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt))
- [Apache BVal JSR:3.0.0](https://bval.apache.org/) ([The Apache Software License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt))
- [Apache Tika Core:2.9.2](https://tika.apache.org/) ([The Apache Software License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt))
- [MapStruct:1.5.5.Final](https://mapstruct.org/mapstruct/) ([The Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt))

# How to contribute?

If you like Picsart Creative APIs SDK and would like to contribute to this open-source project, please check the [Contribution
guide](./CONTRIBUTING.md).
