package com.japharr.uploader.image.imgur;

import com.japharr.uploader.image.imgur.service.ImgurService;
import com.japharr.uploader.util.BaseImageVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.client.WebClient;

import static com.japharr.uploader.Constants.*;

public class ImgurVerticle extends BaseImageVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    super.start(startPromise);

    String imgurEbAddress = config().getJsonObject(EB_ADDRESSES_KEY)
        .getString(EB_IMAGE_IMGUR_ADDRESS_KEY);

    String imgurServiceName = config().getJsonObject(IMAGE_SERVICE_KEY)
            .getJsonObject(IMAGE_SERVICE_IMGUR_KEY).getString(IMAGE_SERVICE_NAME_KEY);

    ImgurService.create(WebClient.create(vertx), config(), bindAndPublish(imgurEbAddress, imgurServiceName, startPromise, ImgurService.class));
  }
}
