package com.rviewer.skeletons.domain.mapper;

import com.rviewer.skeletons.domain.dto.requests.DispenserCreateRequest;
import com.rviewer.skeletons.domain.model.Dispenser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DispenserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "flowVolume", target = "flowVolume")
    Dispenser dispenserCreateRequestToDispenser(DispenserCreateRequest request);
}
