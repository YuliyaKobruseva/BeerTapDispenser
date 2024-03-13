package com.rviewer.skeletons.domain.dto.responses;

import com.rviewer.skeletons.domain.model.History;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DispenserRevenueResponse {
    private double amount;
    private List<History> usages;

    // Constructors
    public DispenserRevenueResponse() {
        //Do nothing
    }

}
