package com.japharr.uploader.cofig;

import io.vertx.core.json.JsonObject;

import static com.japharr.uploader.Constants.*;

public record ImgurConfig (String apiLink, String clientId, String accessToken) {
  public static ImgurConfig of(JsonObject conf) {
    JsonObject imgurConf = conf
        .getJsonObject(IMAGE_SERVICE_KEY)
        .getJsonObject(IMAGE_SERVICE_IMGUR_KEY);

    String apiLink = imgurConf.getString(IMAGE_SERVICE_API_LINK_KEY);
    String clientId = conf.getString(resolveEnv(imgurConf.getString(IMAGE_SERVICE_CLIENT_ID_KEY)));
    String accessToken = conf.getString(resolveEnv(imgurConf.getString(IMAGE_SERVICE_ACCESS_TOKEN_KEY)));

    return new ImgurConfig(apiLink, clientId, accessToken);
  }
}
