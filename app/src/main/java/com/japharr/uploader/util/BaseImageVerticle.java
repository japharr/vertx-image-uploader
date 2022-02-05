package com.japharr.uploader.util;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseImageVerticle extends MicroServiceVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(BaseImageVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    super.start(startPromise);
  }

  protected <T> Handler<AsyncResult<T>> bindAndPublish(String ebAddress, String serviceName, Promise<Void> startPromise, Class<T> clazz) {
    return result -> {
      if(result.succeeded()) {
        new ServiceBinder(vertx)
            .setAddress(ebAddress)
            .register(clazz, result.result())
            .exceptionHandler(throwable -> {
              LOGGER.error("Failed to establish image-service", throwable);
              startPromise.fail(throwable);
            })
            .completionHandler(res -> {
              LOGGER.info("PostgreSQL database service is successfully established in \"" + ebAddress + "\"");
              publishEventBusService(serviceName, ebAddress, clazz, r -> {
                if(r.succeeded()) {
                  LOGGER.info("image-service published");
                  startPromise.complete();
                } else {
                  LOGGER.error("POST_DATABASE_SERVICE_NAME failed to published");
                  startPromise.fail(r.cause());
                }
              });
            });
      } else {
        LOGGER.error("Failed to initiate the connection to database", result.cause());
        startPromise.fail(result.cause());
      }
    };
  }
}
