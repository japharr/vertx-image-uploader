package com.japharr.uploader.exception;

public class UploadResponseException extends RuntimeException {
  private int statusCode;

  public UploadResponseException(String message) {
    super(message);
  }

  public UploadResponseException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public UploadResponseException(Throwable throwable) {
    super(throwable);
  }

  public UploadResponseException(int statusCode, Throwable throwable) {
    super(throwable);
    this.statusCode = statusCode;
  }

  public UploadResponseException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public UploadResponseException(int statusCode, String message, Throwable throwable) {
    super(message, throwable);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return this.statusCode;
  }
}
