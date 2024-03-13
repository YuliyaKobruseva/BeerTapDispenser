package com.rviewer.skeletons.domain.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class DispenserCreateRequest {
    @JsonProperty("flow_volume")
    @NotNull
    private Double flowVolume;
}
