package com.japharr.uploader.cofig;

import io.vertx.core.json.JsonObject;

import static com.japharr.uploader.Constants.*;

public class ImgurConfig {
  private final String apiLink;
  private final String clientId;
  private final String accessToken;

  public ImgurConfig(JsonObject conf) {
    apiLink = conf.getString(IMAGE_SERVICE_API_LINK_KEY);
    clientId = conf.getString(IMAGE_SERVICE_CLIENT_ID_KEY);
    accessToken = conf.getString(IMAGE_SERVICE_ACCESS_TOKEN_KEY);
  }

  public String getApiLink() {
    return apiLink;
  }

  public String getClientId() {
    return clientId;
  }

  public String getAccessToken() {
    return accessToken;
  }
}
