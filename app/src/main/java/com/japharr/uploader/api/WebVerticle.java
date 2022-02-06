package com.japharr.uploader.api;

import com.japharr.uploader.api.handler.UploadApi;
import com.japharr.uploader.image.ImageService;
import com.japharr.uploader.util.MicroServiceVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.japharr.uploader.Constants.*;
import static com.japharr.uploader.api.Endpoints.UPLOAD;

public class WebVerticle extends MicroServiceVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(WebVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    super.start(startPromise);
    LOGGER.info("deploying webVerticle");
    Promise<ImageService> imageServicePromise = Promise.promise();

    String imageServiceActive = config().getJsonObject(IMAGE_SERVICE_KEY)
        .getString(IMAGE_SERVICE_ACTIVE_KEY);

    retrieveEventBusService(imageServiceActive, ImageService.class, imageServicePromise);

    imageServicePromise.future()
        .compose(this::startServer)
        .onComplete(res -> {
          if(res.succeeded()) {
            LOGGER.info("RESULT: {}", res.result());
            startPromise.complete();
          } else {
            LOGGER.error("An error occurred", res.cause());
            startPromise.fail(res.cause());
          }
        });
  }

  private Future<HttpServer> startServer(ImageService imageService) {
    int httpServerPort = config().getJsonObject(HTTP_KEY).getInteger(HTTP_PORT_KEY);

    HttpServer httpServer = vertx.createHttpServer();
    BodyHandler bodyHandler = BodyHandler.create().setBodyLimit(UPLOAD_LIMIT);
    Router router = Router.router(vertx);

    router.post(UPLOAD).handler(bodyHandler);
    router.post(UPLOAD).handler(UploadApi.imageUpload(imageService));

    return httpServer
        .requestHandler(router)
        .listen(httpServerPort);
  }
}
