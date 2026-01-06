package com.onsemi.mib.model;

public class EquipmentFamily {

    private String id;
    private String sptsPkid;
    private String familyName;
    private String createdBy;
    private String createdDate;
    
    private String sptsGuid;
    private String inGlobalOrNot;

    public String getSptsGuid() {
        return sptsGuid;
    }

    public void setSptsGuid(String sptsGuid) {
        this.sptsGuid = sptsGuid;
    }

    public String getInGlobalOrNot() {
        return inGlobalOrNot;
    }

    public void setInGlobalOrNot(String inGlobalOrNot) {
        this.inGlobalOrNot = inGlobalOrNot;
    }
    
    private String selected;

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
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

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

}
