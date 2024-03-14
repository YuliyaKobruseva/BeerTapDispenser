package com.rviewer.skeletons.domain.mapper;

import com.rviewer.skeletons.domain.dto.requests.DispenserCreateRequest;
import com.rviewer.skeletons.domain.model.Dispenser;

public interface DispenserMapper {
    Dispenser dispenserCreateRequestToDispenser(DispenserCreateRequest request);
}
