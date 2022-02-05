package com.japharr.uploader.image.cloudinary.service;

import com.japharr.uploader.image.ImageService;
import com.japharr.uploader.image.cloudinary.service.impl.CloudinaryServiceImpl;
import com.japharr.uploader.image.imgur.service.ImgurService;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

@ProxyGen
public interface CloudinaryService extends ImageService {
  @GenIgnore
  static CloudinaryService create(Vertx vertx, Handler<AsyncResult<CloudinaryService>> resultHandler) {
    return new CloudinaryServiceImpl(vertx, resultHandler);
  }

  @GenIgnore
  static CloudinaryService createProxy(Vertx vertx, String address) {
    return new CloudinaryServiceVertxEBProxy(vertx, address);
  }

  Future<String> upload(String imagePath);
}
