package com.japharr.uploader.image.imgur.service.impl;

import com.japharr.uploader.cofig.ImgurConfig;
import com.japharr.uploader.image.imgur.service.ImgurService;
import com.japharr.uploader.util.FileUtil;
import io.vertx.core.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import static com.japharr.uploader.Constants.*;

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
        .as(BodyCodec.jsonObject())
        .putHeader("Authorization", conf.accessToken())
        .putHeader("Content-Type", "multipart/form-data")
        .sendForm(form, "UTF-8")
        .compose(this::mapToString);
  }

  private Future<String> mapToString(HttpResponse<JsonObject> response) {
    String failedMessage = MessageFormat.format(FAILED_TO_UPLOAD_IMAGE_MESSAGE, "imgur");
    if(response.statusCode() != HTTP_OK) {
      return Future.failedFuture(failedMessage);
    }

    JsonObject jsonObject = response.body();
    int statusCode = jsonObject.getInteger(IMAGE_IMGUR_RESPONSE_STATUS);

    if(statusCode == HTTP_OK) {
      return Future.succeededFuture(response.body()
          .getJsonObject(IMAGE_IMGUR_RESPONSE_DATA)
          .getString(IMAGE_IMGUR_RESPONSE_DATA_LINK));
    }

    return Future.failedFuture(failedMessage);
  }
}
