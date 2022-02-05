package com.japharr.uploader;

import com.japharr.uploader.api.WebVerticle;
import com.japharr.uploader.image.ImageVerticle;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }

  @Override
  public void start(Promise<Void> startFuture) throws Exception {
    initConfig()
        .compose(this::deployVerticle)
        .onComplete(res -> {
          if(res.succeeded())
            startFuture.complete();
          else
            startFuture.fail(res.cause());
        });
  }

  private Future<JsonObject> initConfig() {
    ConfigStoreOptions storeOptions = new ConfigStoreOptions()
        .setFormat("yaml").setType("file")
        .setConfig(new JsonObject().put("path", "conf.yaml"));

    ConfigRetrieverOptions retrievalOptions = new ConfigRetrieverOptions()
        .addStore(storeOptions);

    return ConfigRetriever.create(vertx, retrievalOptions).getConfig();
  }

  private Future<Void> deployVerticle(JsonObject config) {
    DeploymentOptions deploymentOptions = new DeploymentOptions().setConfig(config);

    return vertx.deployVerticle(new ImageVerticle(), deploymentOptions)
        .compose(id -> vertx.deployVerticle(new WebVerticle(), deploymentOptions)).mapEmpty();
  }
}
