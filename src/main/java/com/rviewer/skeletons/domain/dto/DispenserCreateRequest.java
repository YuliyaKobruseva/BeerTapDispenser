package com.rviewer.skeletons.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DispenserCreateRequest {
    @JsonProperty("flow_volume")
    @NotNull
    private Double flowVolume;
}
