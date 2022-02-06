package com.japharr.uploader.image.imgur.service;

import com.japharr.uploader.image.ImageService;
import com.japharr.uploader.image.imgur.service.impl.ImgurServiceImpl;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

@ProxyGen
public interface ImgurService extends ImageService {
  @GenIgnore
  static ImgurService create(WebClient webClient, JsonObject conf, Handler<AsyncResult<ImgurService>> resultHandler) {
    return new ImgurServiceImpl(webClient, conf, resultHandler);
  }

  @GenIgnore
  static ImgurService createProxy(Vertx vertx, String address) {
    return new ImgurServiceVertxEBProxy(vertx, address);
  }

  Future<String> upload(String imagePath);
}
