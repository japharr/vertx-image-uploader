package com.japharr.uploader.image.imgur;

import com.japharr.uploader.image.imgur.service.ImgurService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import static com.japharr.uploader.Constants.EB_ADDRESSES_KEY;
import static com.japharr.uploader.Constants.EB_IMAGE_IMGUR_ADDRESS_KEY;

public class ImgurVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    String imgurEbAddress = config().getJsonObject(EB_ADDRESSES_KEY)
        .getString(EB_IMAGE_IMGUR_ADDRESS_KEY);

    ImgurService.create(vertx, res -> {

    });
  }
}
