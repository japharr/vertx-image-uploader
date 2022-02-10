package com.japharr.uploader.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CloudinaryImage (
    @JsonProperty("format") String format,
    @JsonProperty("type") String type,
    @JsonProperty("url") String url,
    @JsonProperty("secure_url") String secureUrl,
    @JsonProperty("width") Integer width,
    @JsonProperty("height") Integer height
) {}