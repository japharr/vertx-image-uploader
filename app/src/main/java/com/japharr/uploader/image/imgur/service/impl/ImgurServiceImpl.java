package com.japharr.uploader.image.imgur.service.impl;

import com.japharr.uploader.image.imgur.service.ImgurService;
import com.japharr.uploader.util.FileUtil;
import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.multipart.MultipartForm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Base64;

public class ImgurServiceImpl implements ImgurService {
  private final Vertx vertx;
  private WebClient client;

  public ImgurServiceImpl(Vertx vertx, Handler<AsyncResult<ImgurService>> resultHandler) {
    this.vertx = vertx;
    this.client = WebClient.create(vertx);

    // TODO:: to check if the imgur service are available
    resultHandler.handle(Future.succeededFuture(this));
  }

  @Override
  public Future<String> upload(String imagePath) {
    HttpRequest<Buffer> request = client.postAbs("https://api.imgur.com/3/image");

    MultiMap form = MultiMap.caseInsensitiveMultiMap();
    try {
      form.set("image", FileUtil.encodeFileToBase64(new File(imagePath)));
    } catch (IOException exception) {
      return Future.failedFuture(exception);
    }

    return request
        .putHeader("Authorization", "")
        .putHeader("Content-Type", "multipart/form-data")
        .sendForm(form, "UTF-8")
        .compose(ss -> Future.succeededFuture("upload to imgur"));
  }
}
