package com.japharr.uploader.image.cloudinary.service.impl;

import com.japharr.uploader.cofig.CloudinaryConfig;
import com.japharr.uploader.image.cloudinary.service.CloudinaryService;
import com.japharr.uploader.util.FileUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CloudinaryServiceImpl implements CloudinaryService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CloudinaryServiceImpl.class);
  private final WebClient webClient;
  private final CloudinaryConfig conf;

  public CloudinaryServiceImpl(WebClient webClient, JsonObject conf, Handler<AsyncResult<CloudinaryService>> resultHandler) {
    this.webClient = webClient;
    this.conf = CloudinaryConfig.of(conf);

    resultHandler.handle(Future.succeededFuture(this));
  }

  @Override
  public Future<String> upload(String imagePath) {
    HttpRequest<Buffer> request = webClient.postAbs(conf.apiLink());

    MultiMap form = MultiMap.caseInsensitiveMultiMap();
    try {
      File imageFile = new File(imagePath);
      String contentType = Files.probeContentType(imageFile.toPath());
      String base64str = FileUtil.encodeFileToBase64(new File(imagePath));
      String dataUrl = String.format("data:%s;base64,%s", contentType, base64str);

      form.set("file", dataUrl);
    } catch (IOException exception) {
      return Future.failedFuture(exception);
    }

    form.set("api_key", conf.apiKey());
    form.set("upload_preset", conf.uploadPreset());

    return request
      .putHeader("Content-Type", "multipart/form-data")
      .sendForm(form, "UTF-8")
      .onFailure(ss -> {
        LOGGER.error("error", ss.getCause());
      })
      .compose(ss -> {
        return Future.succeededFuture(ss.bodyAsString());
      });
  }
}
