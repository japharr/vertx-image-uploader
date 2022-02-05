package com.japharr.uploader.api.handler;

import com.japharr.uploader.exception.BadRequestException;
import io.vertx.core.Handler;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import static com.japharr.uploader.util.RestApiUtil.restResponse;

public class FailureHandler implements Handler<RoutingContext> {
  @Override
  public void handle(RoutingContext routingContext) {
    Throwable failure = routingContext.failure();
    if (failure instanceof BadRequestException) {
      restResponse(routingContext, 400, errorMessageToErrorBody(failure.getMessage()));
    } else if (failure instanceof DecodeException) {
      restResponse(routingContext, 400, errorMessageToErrorBody("Problems parsing JSON"));
    } else {
      restResponse(routingContext, 500, errorMessageToErrorBody(failure.getMessage()));
    }
  }

  private String errorMessageToErrorBody(String message) {
    return new JsonObject().put("message", message).toString();
  }
}