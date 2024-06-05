package com.example.trash2cash.Entities;

import java.io.Serializable;

public enum RequestStatus implements Serializable {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private String requestStatus;

    RequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestStatus() {
        return requestStatus;
    }


}
