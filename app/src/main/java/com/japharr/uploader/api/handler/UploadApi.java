package com.japharr.uploader.api.handler;

import com.japharr.uploader.exception.BadRequestException;
import com.japharr.uploader.image.ImageService;
import io.vertx.core.Handler;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.japharr.uploader.util.RestApiUtil.restResponse;

public class UploadApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(UploadApi.class);

  public static Handler<RoutingContext> imageUpload(ImageService imageService) {
    return ctx -> {
      Optional<FileUpload> opt = ctx.fileUploads().stream().findFirst();
      if(!opt.isPresent() || !opt.get().contentType().contains("image")) {
        restResponse(ctx, 400, "Please, upload an image");
        return;
      }

      FileUpload fileUpload = opt.get();
      imageService.upload(fileUpload.uploadedFileName())
          .onComplete(rs -> {
            if(rs.succeeded()) {
              restResponse(ctx, 200, rs.result());
            } else {
              ctx.fail(rs.cause());
            }
          });
    };
  }
}
