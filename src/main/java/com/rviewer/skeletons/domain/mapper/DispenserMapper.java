package com.rviewer.skeletons.domain.mapper;

import com.rviewer.skeletons.domain.dto.DispenserCreateRequest;
import com.rviewer.skeletons.domain.model.Dispenser;
import com.rviewer.skeletons.infrastructure.controllers.DispenserController;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mapper(componentModel = "spring")
public interface DispenserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "flowVolume", target = "flowVolume")
    Dispenser dispenserCreateRequestToDispenser(DispenserCreateRequest request);
}
