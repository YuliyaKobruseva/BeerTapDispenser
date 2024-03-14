package com.rviewer.skeletons.domain.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DispenserRevenueResponse {
    private double amount;
    private List<UsageDto> usages;

    // Constructors
    public DispenserRevenueResponse() {
        //Do nothing
    }

    public DispenserRevenueResponse(double amount, List<UsageDto> usages) {
        this.amount = amount;
        this.usages = usages;
    }

}
