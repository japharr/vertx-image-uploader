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
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

@ProxyGen
public interface CloudinaryService extends ImageService {
  @GenIgnore
  static CloudinaryService create(WebClient webClient, JsonObject conf, Handler<AsyncResult<CloudinaryService>> resultHandler) {
    return new CloudinaryServiceImpl(webClient, conf, resultHandler);
  }

  @GenIgnore
  static CloudinaryService createProxy(Vertx vertx, String address) {
    return new CloudinaryServiceVertxEBProxy(vertx, address);
  }

  Future<String> upload(String imagePath);
}
