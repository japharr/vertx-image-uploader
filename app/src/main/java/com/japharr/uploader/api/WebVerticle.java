package com.japharr.uploader.api;

import com.japharr.uploader.image.ImageService;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebVerticle extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(WebVerticle.class);

  private ServiceDiscovery discovery;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOGGER.info("deploying webVerticle");
    discovery = ServiceDiscovery.create(vertx);

    getService(res -> {
      if(res.succeeded()) {
        ImageService service = res.result();
        Future<String> result = service.upload("Hello world");

        result.onComplete(ress -> {
          if(ress.succeeded()) {
            LOGGER.info("RESULT: {}", ress.result());
            startPromise.complete();
          } else {
            LOGGER.error("An error occurred", ress.cause());
            startPromise.fail(ress.cause());
          }
        });
      }
    });
  }

  private void getService(Handler<AsyncResult<ImageService>> resultHandler) {
    discovery.getRecord(new JsonObject().put("name", "imgur"), ar -> {
      if (ar.succeeded() && ar.result() != null) {

        // Retrieve the service reference
        ServiceReference reference = discovery.getReference(ar.result());

        // Retrieve the service object
        ImageService service = reference.getAs(ImageService.class);

        resultHandler.handle(Future.succeededFuture(service));
      }
    });
  }
}
