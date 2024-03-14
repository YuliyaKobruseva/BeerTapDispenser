package com.rviewer.skeletons.domain.mapper.interfaces;

import com.rviewer.skeletons.domain.dto.requests.DispenserCreateRequest;
import com.rviewer.skeletons.domain.exceptions.InvalidRequestException;
import com.rviewer.skeletons.domain.mapper.DispenserMapper;
import com.rviewer.skeletons.domain.model.Dispenser;
import org.springframework.stereotype.Component;

@Component
public class IDispenserMapper implements DispenserMapper {

    @Override
    public Dispenser dispenserCreateRequestToDispenser(DispenserCreateRequest request) {
        if (request == null) {
            throw new InvalidRequestException("Request cannot be null");
        }

        Dispenser dispenser = new Dispenser();
        dispenser.setFlowVolume(request.getFlowVolume());
        return dispenser;
    }
}