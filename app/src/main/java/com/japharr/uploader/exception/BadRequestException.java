package com.japharr.uploader.exception;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(Throwable throwable) {
    super(throwable);
  }

  public BadRequestException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
