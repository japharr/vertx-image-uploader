package com.japharr.uploader.image.imgur.service.impl;

import com.japharr.uploader.image.imgur.service.ImgurService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public class ImgurServiceImpl implements ImgurService {
  private final Vertx vertx;

  public ImgurServiceImpl(Vertx vertx, Handler<AsyncResult<ImgurService>> resultHandler) {
    this.vertx = vertx;

    // TODO:: to check if the imgur service are available
    resultHandler.handle(Future.succeededFuture(this));
  }

  @Override
  public Future<String> upload(String imagePath) {
    return Future.succeededFuture("upload to imgur");
  }
}
