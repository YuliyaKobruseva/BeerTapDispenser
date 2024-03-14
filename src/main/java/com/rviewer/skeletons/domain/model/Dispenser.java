package com.rviewer.skeletons.domain.model;

import com.rviewer.skeletons.domain.enums.DispenserStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Dispenser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double flowVolume; // liters per second
    @Enumerated(EnumType.STRING)
    private DispenserStatusEnum status = DispenserStatusEnum.CLOSE;

    public Dispenser() {}

}