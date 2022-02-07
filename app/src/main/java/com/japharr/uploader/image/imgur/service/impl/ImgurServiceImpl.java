package com.japharr.uploader.image.imgur.service.impl;

import com.japharr.uploader.cofig.ImgurConfig;
import com.japharr.uploader.image.imgur.service.ImgurService;
import com.japharr.uploader.util.FileUtil;
import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;

import java.io.File;
import java.io.IOException;

public class ImgurServiceImpl implements ImgurService {
  private final WebClient webClient;
  private final ImgurConfig conf;

  public ImgurServiceImpl(WebClient webClient, JsonObject conf, Handler<AsyncResult<ImgurService>> resultHandler) {
    this.webClient = webClient;
    this.conf = ImgurConfig.of(conf);

    // TODO:: to check if the imgur service are available
    resultHandler.handle(Future.succeededFuture(this));
  }

  @Override
  public Future<String> upload(String imagePath) {
    HttpRequest<Buffer> request = webClient.postAbs(conf.apiLink());

    MultiMap form = MultiMap.caseInsensitiveMultiMap();
    try {
      form.set("image", FileUtil.encodeFileToBase64(new File(imagePath)));
    } catch (IOException exception) {
      return Future.failedFuture(exception);
    }

    return request
        .putHeader("Authorization", conf.accessToken())
        .putHeader("Content-Type", "multipart/form-data")
        .sendForm(form, "UTF-8")
        .compose(ss -> Future.succeededFuture("upload to imgur"));
  }
}
