package com.japharr.uploader.util;

import io.vertx.core.*;
import io.vertx.core.impl.ConcurrentHashSet;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.ServiceReference;
import io.vertx.servicediscovery.types.EventBusService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MicroServiceVerticle extends AbstractVerticle {
  protected ServiceDiscovery discovery;
  protected Set<Record> registeredRecords = new ConcurrentHashSet<>();

  @Override
  public void start(Promise<Void> startFuture) throws Exception {
    discovery = ServiceDiscovery.create(vertx, new ServiceDiscoveryOptions().setBackendConfiguration(config()));
  }

  public void publishEventBusService(String name, String address, Class<?> serviceClass, Handler<AsyncResult<Void>> completionHandler) {
    Record record = EventBusService.createRecord(name, address, serviceClass);
    publish(record, completionHandler);
  }

  protected void publish(Record record, Handler<AsyncResult<Void>> completionHandler) {
    if (discovery == null) {
      try {
        start();
      } catch (Exception e) {
        throw new RuntimeException("Cannot create discovery service");
      }
    }

    discovery.publish(record, ar -> {
      if (ar.succeeded()) {
        registeredRecords.add(record);
      }
      completionHandler.handle(ar.map((Void)null));
    });
  }

  protected <T> void retrieveEventBusService(String serviceName, Class<T> serviceClass, Handler<AsyncResult<T>> resultHandler) {
    discovery.getRecord(new JsonObject().put("name", serviceName), ar -> {
      if (ar.succeeded() && ar.result() != null) {

        // Retrieve the service reference
        ServiceReference reference = discovery.getReference(ar.result());

        // Retrieve the service object
        T service = reference.getAs(serviceClass);

        resultHandler.handle(Future.succeededFuture(service));
      }
    });
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    List<Future> futures = new ArrayList<>();
    registeredRecords.forEach(record -> {
      futures.add(discovery.unpublish(record.getRegistration()));
    });

    if (futures.isEmpty()) {
      discovery.close();
      stopPromise.complete();
    } else {
      CompositeFuture composite = CompositeFuture.all(futures);
      composite.onComplete(ar -> {
        discovery.close();
        if (ar.failed()) {
          stopPromise.fail(ar.cause());
        } else {
          stopPromise.complete();
        }
      });
    }
  }
}
