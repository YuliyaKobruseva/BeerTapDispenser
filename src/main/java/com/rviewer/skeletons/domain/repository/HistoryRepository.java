package com.rviewer.skeletons.domain.repository;

import com.rviewer.skeletons.domain.enums.DispenserStatusEnum;
import com.rviewer.skeletons.domain.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {

    Optional<History> findByDispenserId(Long dispenserId);
    List<History> findAllByDispenserId(Long dispenserId);
    Optional<History> findByDispenserIdAndOpenedAtIsNotNullAndClosedAtIsNull(Long dispenserId);
}
