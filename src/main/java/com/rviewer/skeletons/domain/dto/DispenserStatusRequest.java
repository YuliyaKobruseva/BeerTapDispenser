package com.rviewer.skeletons.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DispenserStatusRequest {
    private String dispenserId;
    private String status;
}
