package com.rviewer.skeletons.domain.services.interfaces;

import com.rviewer.skeletons.domain.dto.DispenserCreateRequest;
import com.rviewer.skeletons.domain.mapper.DispenserMapper;
import com.rviewer.skeletons.domain.model.Dispenser;
import com.rviewer.skeletons.domain.repository.DispenserRepository;
import com.rviewer.skeletons.domain.services.DispenserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IDispenserService implements DispenserService {

    @Autowired
    private DispenserRepository dispenserRepository;

    @Override
    public Dispenser createDispenser(DispenserCreateRequest dispenserCreateRequest) {
        return dispenserRepository.save(DispenserMapper.INSTANCE.toDispenser(dispenserCreateRequest));
    }
}
