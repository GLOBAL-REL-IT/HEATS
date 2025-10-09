package com.onsemi.mib.model;

public class FTPdata {

    private String id;
    private String groupId;
    private String rmsId; //
    private String rmsLotEvent;
//    private String rmsStatus;
    private String processStatus; //
//    private String processStatusDateTime;
    private String lotType; //
    private String unitQty; //
//    private String stressCompStatus;
    private String pkgFamily; //
    private String pkgName; //
    private String scrapDate; //
    private String mthToScrap; //
    private String completeDate; //
    private String event; //
    private String modifiedDate;
    private String modifiedBy;
    private String createdDate;
    private String createdBy;
    private String status;
    private String flag;

    private String concatLot;
    private String concatSubEvent;
    private String countSubEvent;
    private String aging;

    //added 310124
    private String cancelBy;
    private String cancelDate;
    private String packingDay;

    private String actualQty;

    private String creator;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getActualQty() {
        return actualQty;
    }

    public void setActualQty(String actualQty) {
        this.actualQty = actualQty;
    }

    public String getPackingDay() {
        return packingDay;
    }

    public void setPackingDay(String packingDay) {
        this.packingDay = packingDay;
    }

    public String getCancelBy() {
        return cancelBy;
    }

    public void setCancelBy(String cancelBy) {
        this.cancelBy = cancelBy;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getRmsId() {
        return rmsId;
    }

    public void setRmsId(String rmsId) {
        this.rmsId = rmsId;
    }

    public String getRmsLotEvent() {
        return rmsLotEvent;
    }

    public void setRmsLotEvent(String rmsLotEvent) {
        this.rmsLotEvent = rmsLotEvent;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getLotType() {
        return lotType;
    }

    public void setLotType(String lotType) {
        this.lotType = lotType;
    }

    public String getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(String unitQty) {
        this.unitQty = unitQty;
    }

    public String getPkgFamily() {
        return pkgFamily;
    }

    public void setPkgFamily(String pkgFamily) {
        this.pkgFamily = pkgFamily;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getScrapDate() {
        return scrapDate;
    }

    public void setScrapDate(String scrapDate) {
        this.scrapDate = scrapDate;
    }

    public String getMthToScrap() {
        return mthToScrap;
    }

    public void setMthToScrap(String mthToScrap) {
        this.mthToScrap = mthToScrap;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getConcatLot() {
        return concatLot;
    }

    public void setConcatLot(String concatLot) {
        this.concatLot = concatLot;
    }

    public String getConcatSubEvent() {
        return concatSubEvent;
    }

    public void setConcatSubEvent(String concatSubEvent) {
        this.concatSubEvent = concatSubEvent;
    }

    public String getCountSubEvent() {
        return countSubEvent;
    }

    public void setCountSubEvent(String countSubEvent) {
        this.countSubEvent = countSubEvent;
    }

    public String getAging() {
        return aging;
    }

    public void setAging(String aging) {
        this.aging = aging;
    }
}
