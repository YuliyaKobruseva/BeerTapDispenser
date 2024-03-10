package com.rviewer.skeletons.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DispenserCreateRequest {
    @JsonProperty("flow_volume")
    private Double flowVolume;
}
