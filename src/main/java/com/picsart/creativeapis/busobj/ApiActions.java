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

package com.picsart.creativeapis.busobj;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * This enum represents the different API actions that can be performed. Each API action has a name
 * and a URL associated with it.
 */
@Getter
@Accessors(fluent = true)
public enum ApiActions {
  TEXT2IMAGE("text2image", "text2image"),
  REMOVE_BACKGROUND("removeBackground", "removebg"),
  EFFECT("effect", "effects"),
  LIST_EFFECTS("listEffects", "effects"),
  ULTRA_UPSCALE("ultraUpscale", "upscale/ultra"),
  UPSCALE("upscale", "upscale"),
  ULTRA_ENHANCE("ultraEnhance", "upscale/enhance"),
  ENHANCE_FACE("enhanceFace", "enhance/face"),
  EFFECTS_PREVIEWS("effectsPreviews", "effects/previews"),
  ADJUST("adjust", "adjust"),
  BACKGROUND_TEXTURE("backgroundTexture", "background/texture"),
  SURFACE_MAP("surfaceMap", "surfacemap"),
  UPLOAD("upload", "upload"),
  BALANCE("balance", "balance");

  /** The name of the API action. */
  private final String actionName;

  /** The URL of the API action. */
  private final String url;

  /**
   * Constructs a new ApiActions with the specified action name and URL.
   *
   * @param actionName The name of the API action.
   * @param url The URL of the API action.
   */
  ApiActions(String actionName, String url) {
    this.actionName = actionName;
    this.url = url;
  }
}
