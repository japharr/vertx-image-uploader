package com.japharr.uploader.image.cloudinary;

import com.japharr.uploader.image.cloudinary.service.CloudinaryService;
import com.japharr.uploader.util.BaseImageVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

import static com.japharr.uploader.Constants.*;

public class CloudinaryVerticle extends BaseImageVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    super.start(startPromise);

    String cloudinaryEbAddress = config().getJsonObject(EB_ADDRESSES_KEY)
        .getString(EB_IMAGE_CLOUDINARY_ADDRESS_KEY);

    String cloudinaryServiceName = config().getJsonObject(IMAGE_SERVICE_KEY)
        .getJsonObject(IMAGE_SERVICE_CLOUDINARY_KEY).getString(IMAGE_SERVICE_NAME_KEY);

    JsonObject cloudinaryConf = config().getJsonObject(IMAGE_SERVICE_KEY)
        .getJsonObject(IMAGE_SERVICE_CLOUDINARY_KEY);

    CloudinaryService.create(WebClient.create(vertx), config(), bindAndPublish(cloudinaryEbAddress, cloudinaryServiceName, startPromise, CloudinaryService.class));
  }
}
