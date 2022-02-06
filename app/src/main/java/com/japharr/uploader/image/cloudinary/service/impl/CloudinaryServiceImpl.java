package com.japharr.uploader.image.cloudinary.service.impl;

import com.japharr.uploader.image.cloudinary.service.CloudinaryService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

public class CloudinaryServiceImpl implements CloudinaryService {
  private final WebClient webClient;
  private final JsonObject conf;

  public CloudinaryServiceImpl(WebClient webClient, JsonObject conf, Handler<AsyncResult<CloudinaryService>> resultHandler) {
    this.webClient = webClient;
    this.conf = conf;

    resultHandler.handle(Future.succeededFuture(this));
  }

  @Override
  public Future<String> upload(String imagePath) {
    return Future.succeededFuture("upload to cloudinary");
  }
}
