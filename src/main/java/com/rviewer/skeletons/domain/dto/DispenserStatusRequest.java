package com.rviewer.skeletons.domain.dto;

public class DispenserStatusRequest {
    private String dispenserId;
    private String status;

    public String getDispenserId() {
        return dispenserId;
    }

    public void setDispenserId(String dispenserId) {
        this.dispenserId = dispenserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
