package com.japharr.uploader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Constants {
  String HTTP_KEY = "http";
  String HTTP_PORT_KEY = "port";

  String EB_ADDRESSES_KEY = "eb.addresses";
  String EB_IMAGE_IMGUR_ADDRESS_KEY = "image.imgur";
  String EB_IMAGE_CLOUDINARY_ADDRESS_KEY = "image.cloudinary";

  String IMAGE_SERVICE_KEY = "image-service";
  String IMAGE_SERVICE_ACTIVE_KEY = "active";
  String IMAGE_SERVICE_IMGUR_KEY = "imgur";
  String IMAGE_SERVICE_CLOUDINARY_KEY = "cloudinary";

  String IMAGE_SERVICE_NAME_KEY = "name";
  String IMAGE_SERVICE_API_LINK_KEY = "api-link";
  String IMAGE_SERVICE_CLIENT_ID_KEY = "client-id";
  String IMAGE_SERVICE_ACCESS_TOKEN_KEY = "access-token";
  String IMAGE_SERVICE_CLOUD_NAME_KEY = "cloud-name";
  String IMAGE_SERVICE_API_KEY_KEY = "api-key";
  String IMAGE_SERVICE_UPLOAD_PRESET_KEY = "upload-preset";

  int HTTP_OK = 200;
  int HTTP_BAD_REQUEST = 400;
  long UPLOAD_LIMIT = 5*1024*1024;

  String IMAGE_IMGUR_RESPONSE_DATA = "data";
  String IMAGE_IMGUR_RESPONSE_STATUS = "status";
  String IMAGE_IMGUR_RESPONSE_DATA_LINK = "link";

  String IMAGE_CLOUDINARY_RESPONSE_URL = "url";
  String IMAGE_CLOUDINARY_RESPONSE_SECURE_URL = "secure_url";

  String FAILED_TO_UPLOAD_IMAGE_MESSAGE = "Unable to upload image to {}";

  String ENV_PATTERN = "[^{}]+(?=})";

  Pattern VALIDATE_ENV = Pattern.compile(ENV_PATTERN);

  static String resolveEnv(String key) {
    Matcher matcher = VALIDATE_ENV.matcher(key);

    if(!matcher.find()) return key;

    return matcher.group(0);
  }
}
