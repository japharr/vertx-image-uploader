package com.japharr.uploader.image.cloudinary.service.impl;

import com.japharr.uploader.cofig.CloudinaryConfig;
import com.japharr.uploader.exception.UploadResponseException;
import com.japharr.uploader.image.cloudinary.service.CloudinaryService;
import com.japharr.uploader.model.CloudinaryImage;
import com.japharr.uploader.util.FileUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;

import static com.japharr.uploader.Constants.*;

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
        .as(BodyCodec.json(CloudinaryImage.class))
      .putHeader("Content-Type", "multipart/form-data")
      .sendForm(form, "UTF-8")
        .compose(this::mapToString);
      //.compose(response -> Future.succeededFuture(response.body().toString()));
  }

  /*
  private Future<String> mapToString(HttpResponse<JsonObject> response) {
    if(response.statusCode() != HTTP_OK) {
      LOGGER.error("failed to upload: {}", response.statusCode());
      return Future.failedFuture(new UploadResponseException(response.statusCode(), response.body().toString()));
    }

    LOGGER.info("status code: {}", response.statusCode());
    JsonObject jsonObject = response.body();

    JsonObject result = new JsonObject()
        .put("url", jsonObject.getString("resource_type"))
        .put("url", jsonObject.getString("secure_url"))
        .put("width", jsonObject.getInteger("width"))
        .put("height", jsonObject.getInteger("height"));

    return Future.succeededFuture(jsonObject.toString());
  }
  */

  private Future<String> mapToString(HttpResponse<CloudinaryImage> response) {
    if(response.statusCode() != HTTP_OK) {
      LOGGER.error("failed to upload: {}", response.statusCode());
      return Future.failedFuture(new UploadResponseException(response.statusCode(), response.body().toString()));
    }

    LOGGER.info("status code: {}", response.statusCode());
    CloudinaryImage jsonObject = response.body();

    JsonObject result = new JsonObject()
        .put("url", jsonObject.url())
        .put("type", jsonObject.type())
        .put("width", jsonObject.width())
        .put("height", jsonObject.height());

    return Future.succeededFuture(result.toString());
  }
}
