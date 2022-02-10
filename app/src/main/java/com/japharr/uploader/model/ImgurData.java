package com.japharr.uploader.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImgurData {
  @JsonProperty("type") private String type;
  @JsonProperty("url") private String url;
  @JsonProperty("width") private Integer width;
  @JsonProperty("height") private Integer height;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }
}
