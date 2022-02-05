package com.japharr.uploader.image.cloudinary.service.impl;

import com.japharr.uploader.image.cloudinary.service.CloudinaryService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public class CloudinaryServiceImpl implements CloudinaryService {
  private final Vertx vertx;

  public CloudinaryServiceImpl(Vertx vertx, Handler<AsyncResult<CloudinaryService>> resultHandler) {
    this.vertx = vertx;

    resultHandler.handle(Future.succeededFuture(this));
  }

  @Override
  public Future<String> upload(String imagePath) {
    return Future.succeededFuture("upload to cloudinary");
  }
}
