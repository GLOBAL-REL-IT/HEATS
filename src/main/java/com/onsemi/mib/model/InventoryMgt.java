package com.onsemi.mib.model;

public class InventoryMgt {

    private String id;
    private String reqId;
    private String rackMonth;
    private String rack;
    private String shelf;
    private String status;
    private String flag;
    private String modifiedDate;
    private String dateCreated;

    private String rmsLotEvent;

    public String getRmsLotEvent() {
        return rmsLotEvent;
    }

    public void setRmsLotEvent(String rmsLotEvent) {
        this.rmsLotEvent = rmsLotEvent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getRackMonth() {
        return rackMonth;
    }

    public void setRackMonth(String rackMonth) {
        this.rackMonth = rackMonth;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

}
