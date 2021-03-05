package com.example.nargisshoppapp10.view.image;

import android.net.Uri;

import java.net.URI;

public final class ImageUtility {

  public static String getSizedImageUrl(final String featuredImageUrl, int imageTargetWidthPx, int imageTargetHeightPx) {
    if (featuredImageUrl == null) {
      return null;
    }

    try {
      URI originalUri = new URI(featuredImageUrl);
      String path = originalUri.getPath();
      String suffixedPath;
      String sizeSuffix = getImageSuffixForDimensions(imageTargetWidthPx, imageTargetHeightPx);
      int extensionSeparatorPos = path.lastIndexOf('.');
      if (-1 == extensionSeparatorPos) {
        // Shopify should always store the images in the CDN with an extension, even if you upload
        // the file without one. But just in case.
        suffixedPath = path + sizeSuffix;
      } else {
        suffixedPath = path.substring(0, extensionSeparatorPos) + sizeSuffix + path.substring(extensionSeparatorPos);
      }

      return Uri.parse(featuredImageUrl).buildUpon().path(suffixedPath).toString();
    } catch (Exception e) {
      return null;
    }
  }

  public static String getImageSuffixForDimensions(int width, int height) {
    final int pixels = Math.max(width, height);
    if (pixels <= 16) {
      return "_pico";
    } else if (pixels <= 32) {
      return "_icon";
    } else if (pixels <= 50) {
      return "_thumb";
    } else if (pixels <= 100) {
      return "_small";
    } else if (pixels <= 160) {
      return "_compact";
    } else if (pixels <= 240) {
      return "_medium";
    } else if (pixels <= 480) {
      return "_large";
    } else if (pixels <= 600) {
      return "_grande";
    } else {
      return "_1024x1024";
    }
  }

  private ImageUtility() {
  }
}
