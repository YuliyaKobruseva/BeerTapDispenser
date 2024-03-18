package com.rviewer.skeletons.utils.entities;

import com.rviewer.skeletons.domain.dto.requests.DispenserCreateRequest;
import com.rviewer.skeletons.domain.dto.requests.DispenserStatusRequest;

public class TestRequestsEntityFactory {
    public static DispenserCreateRequest createNewDispenserRequest() {
        DispenserCreateRequest request = new DispenserCreateRequest();
        request.setFlowVolume(0.064);
        return request;
    }

    public static DispenserStatusRequest createDispenserStatusRequest() {
        DispenserStatusRequest request = new DispenserStatusRequest();
        request.setStatus("open");
        return request;
    }
}
