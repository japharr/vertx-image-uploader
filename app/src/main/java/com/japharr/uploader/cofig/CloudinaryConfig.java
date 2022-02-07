package com.japharr.uploader.cofig;

import io.vertx.core.json.JsonObject;

import static com.japharr.uploader.Constants.*;

public record CloudinaryConfig (String apiLink, String apiKey, String uploadPreset) {
  public static CloudinaryConfig of(JsonObject conf) {
    JsonObject imgurConf = conf
        .getJsonObject(IMAGE_SERVICE_KEY)
        .getJsonObject(IMAGE_SERVICE_CLOUDINARY_KEY);

    String apiLink = imgurConf.getString(IMAGE_SERVICE_API_LINK_KEY);
    String apiKey = conf.getString(resolveEnv(imgurConf.getString(IMAGE_SERVICE_API_KEY_KEY)));
    String uploadPreset = conf.getString(resolveEnv(imgurConf.getString(IMAGE_SERVICE_UPLOAD_PRESET_KEY)));

    return new CloudinaryConfig(apiLink, apiKey, uploadPreset);
  }
}
