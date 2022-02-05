package com.japharr.uploader.image.imgur.service.impl;

import com.japharr.uploader.image.imgur.service.ImgurService;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class ImgurServiceImpl implements ImgurService {

  public ImgurServiceImpl(Vertx vertx) {}

  @Override
  public Future<String> upload(String imagePath) {
    return Future.succeededFuture("upload to imgur");
  }
}
