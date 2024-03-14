package com.rviewer.skeletons.domain.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsageDto {
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;
    private double flowVolume;
    private double totalSpent;

    public UsageDto() {}

    public UsageDto(LocalDateTime openedAt, LocalDateTime closedAt, double flowVolume, double totalSpent) {
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.flowVolume = flowVolume;
        this.totalSpent = totalSpent;
    }
}

