package com.rviewer.skeletons.utils.entities;

import com.rviewer.skeletons.domain.model.Dispenser;

public class TestDispenserEntityFactory {
    public static Dispenser createDispenser() {
        Dispenser dispenser = new Dispenser();
        dispenser.setFlowVolume(0.064);
        return dispenser;
    }

}
