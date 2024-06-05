package com.example.trash2cash.Entities;

import java.io.Serializable;

public class Request implements Serializable {
    private RecyclableItem requestItem;
    private RequestStatus requestStatus;

    private final int user_id;

    private int id;

    public Request(RecyclableItem requestItem, RequestStatus requestStatus, int id, int user_id) {
        this.requestItem = requestItem;
        this.requestStatus = requestStatus;
        this.id = id;
        this.user_id = user_id;
    }

    public RecyclableItem getRequestItem() {
        return requestItem;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public int getId() {
        return id;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public int getUser_id() {
        return user_id;
    }


}
