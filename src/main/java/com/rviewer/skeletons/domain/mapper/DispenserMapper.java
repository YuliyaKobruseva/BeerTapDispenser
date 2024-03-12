package com.rviewer.skeletons.domain.mapper;

import com.rviewer.skeletons.domain.dto.DispenserCreateRequest;
import com.rviewer.skeletons.domain.model.Dispenser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DispenserMapper {
    DispenserMapper INSTANCE = Mappers.getMapper(DispenserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "flowVolume", target = "flowVolume")
    Dispenser dispenserCreateRequestToDispenser(DispenserCreateRequest request);
}
