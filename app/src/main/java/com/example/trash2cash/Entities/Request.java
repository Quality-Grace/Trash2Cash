package com.example.trash2cash.Entities;

public class Request {
    private RecyclableItem requestItem;
    private RequestStatus requestStatus;

    private int id;

    public Request(RecyclableItem requestItem, RequestStatus requestStatus, int id) {
        this.requestItem = requestItem;
        this.requestStatus = requestStatus;
        this.id = id;
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


}
