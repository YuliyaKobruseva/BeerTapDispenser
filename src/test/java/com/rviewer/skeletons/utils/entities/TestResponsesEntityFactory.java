package com.rviewer.skeletons.utils.entities;

import com.rviewer.skeletons.domain.dto.responses.DispenserRevenueResponse;

public class TestResponsesEntityFactory {
    public static DispenserRevenueResponse createDispenserRevenueResponse() {
        DispenserRevenueResponse response = new DispenserRevenueResponse();
        response.setAmount(57.678);
        response.setUsages(TestHistoryEntityFactory.dispenserUsages());
        return response;
    }


}
