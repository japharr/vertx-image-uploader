package com.japharr.uploader.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ImgurImage(
    @JsonProperty("data") ImgurData data,
    @JsonProperty("success") boolean success,
    @JsonProperty("status") int status
) {
}
