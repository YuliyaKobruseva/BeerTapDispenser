package com.rviewer.skeletons.domain.services;

import com.rviewer.skeletons.domain.dto.requests.DispenserCreateRequest;
import com.rviewer.skeletons.domain.dto.requests.DispenserStatusRequest;
import com.rviewer.skeletons.domain.dto.responses.DispenserRevenueResponse;
import com.rviewer.skeletons.domain.model.Dispenser;

public interface DispenserService {
    Dispenser createDispenser(DispenserCreateRequest dispenserCreateRequest);
    void changeStatus(Long id, DispenserStatusRequest dispenserStatusRequest);

    DispenserRevenueResponse calculateRevenue(Long id);
}
