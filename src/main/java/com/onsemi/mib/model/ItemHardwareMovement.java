package com.onsemi.mib.model;

public class ItemHardwareMovement {

    private String id;
    private String sptsPkid;
    private String mibHardwareId;
    private String transType;
    private String rmsEvent;
    private String alu;
    private String createdBy;
    private String createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSptsPkid() {
        return sptsPkid;
    }

    public void setSptsPkid(String sptsPkid) {
        this.sptsPkid = sptsPkid;
    }

    public String getMibHardwareId() {
        return mibHardwareId;
    }

    public void setMibHardwareId(String mibHardwareId) {
        this.mibHardwareId = mibHardwareId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getRmsEvent() {
        return rmsEvent;
    }

    public void setRmsEvent(String rmsEvent) {
        this.rmsEvent = rmsEvent;
    }

    public String getAlu() {
        return alu;
    }

    public void setAlu(String alu) {
        this.alu = alu;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
