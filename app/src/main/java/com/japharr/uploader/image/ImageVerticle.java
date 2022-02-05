package com.japharr.uploader.image;

import com.japharr.uploader.image.cloudinary.CloudinaryVerticle;
import com.japharr.uploader.image.imgur.ImgurVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;

public class ImageVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    deployVerticle()
        .onComplete(res -> {
          if(res.succeeded()) {
            startPromise.complete();
          } else {
            startPromise.fail(res.cause());
          }
        });
  }

  private Future<Void> deployVerticle() {
    Future<String> imgurVerticle = vertx.deployVerticle(new ImgurVerticle());
    Future<String> cloudinaryVerticle = vertx.deployVerticle(new CloudinaryVerticle());

    return CompositeFuture.all(imgurVerticle, cloudinaryVerticle).mapEmpty();
  }
}
