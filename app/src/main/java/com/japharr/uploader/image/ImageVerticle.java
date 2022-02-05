package com.japharr.uploader.image;

import com.japharr.uploader.image.cloudinary.CloudinaryVerticle;
import com.japharr.uploader.image.imgur.ImgurVerticle;
import io.vertx.core.*;

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
    DeploymentOptions deploymentOptions = new DeploymentOptions().setConfig(config());

    Future<String> imgurVerticle = vertx.deployVerticle(new ImgurVerticle(), deploymentOptions);
    Future<String> cloudinaryVerticle = vertx.deployVerticle(new CloudinaryVerticle(), deploymentOptions);

    return CompositeFuture.all(imgurVerticle, cloudinaryVerticle).mapEmpty();
  }
}
