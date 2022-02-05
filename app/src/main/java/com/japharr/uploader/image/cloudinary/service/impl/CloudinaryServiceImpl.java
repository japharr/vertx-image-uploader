package com.japharr.uploader.image.cloudinary.service.impl;

import com.japharr.uploader.image.cloudinary.service.CloudinaryService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class CloudinaryServiceImpl implements CloudinaryService {

  public CloudinaryServiceImpl(Vertx vertx) {

  }

  @Override
  public Future<String> upload(String imagePath) {
    return Future.succeededFuture("upload to cloudinary");
  }
}
