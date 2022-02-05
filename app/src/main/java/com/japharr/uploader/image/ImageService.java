package com.japharr.uploader.image;

import io.vertx.core.Future;

public interface ImageService {
  Future<String> upload(String imagePath);
}
