package com.rviewer.skeletons.domain.services;

import com.rviewer.skeletons.domain.dto.DispenserCreateRequest;
import com.rviewer.skeletons.domain.model.Dispenser;

public interface DispenserService {
    Dispenser createDispenser(DispenserCreateRequest dispenserCreateRequest);
}
