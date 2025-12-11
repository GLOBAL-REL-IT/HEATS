package com.onsemi.mib.model;

public class EquipmentRelTestGroup {

    private String id;
    private String sptsPkid;
    private String relTestGroupName;
    private String createdBy;
    private String createdDate;

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

    public String getRelTestGroupName() {
        return relTestGroupName;
    }

    public void setRelTestGroupName(String relTestGroupName) {
        this.relTestGroupName = relTestGroupName;
    }

}
