package com.onsemi.mib.model;

public class SRInventory {
    private String id;
    private String reqId;
    private String boxId;
    private String event;
    private String mthToScrap;
    private String pkgFamily;
    private String gtsNo;
    private String receivedDate;
    private String customNo;
    private String customDate;
    private String inventoryRack;
    private String inventoryShelf;
    private String inventoryBy;
    private String inventoryDate;
    private String createdDate;
    private String createdBy;
    private String modifiedDate;
    private String modifiedBy;
    private String flag;
    private String status;
    private String inventoryDateNew;
    private String inventoryRemarks;
    private String aging;
    private String rmsLotEventConcat;
    private String lotQty;
    private String concatRmsEvent;
    
    private String mthToScrapView;
    private String mthToScrapDb;

    public SRInventory() {
    }

    public SRInventory(String reqId, String boxId, String receivedDate, String gtsNo, String customNo, String customDate, String inventoryRack, String inventoryShelf, String inventoryDate) {
        this.reqId = reqId;
        this.boxId = boxId;
        this.gtsNo = gtsNo;
        this.receivedDate = receivedDate;
        this.customNo = customNo;
        this.customDate = customDate;
        this.inventoryRack = inventoryRack;
        this.inventoryShelf = inventoryShelf;
        this.inventoryDate = inventoryDate;
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

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMthToScrap() {
        return mthToScrap;
    }

    public void setMthToScrap(String mthToScrap) {
        this.mthToScrap = mthToScrap;
    }

    public String getPkgFamily() {
        return pkgFamily;
    }

    public void setPkgFamily(String pkgFamily) {
        this.pkgFamily = pkgFamily;
    }

    public String getGtsNo() {
        return gtsNo;
    }

    public void setGtsNo(String gtsNo) {
        this.gtsNo = gtsNo;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getCustomNo() {
        return customNo;
    }

    public void setCustomNo(String customNo) {
        this.customNo = customNo;
    }

    public String getCustomDate() {
        return customDate;
    }

    public void setCustomDate(String customDate) {
        this.customDate = customDate;
    }

    public String getInventoryRack() {
        return inventoryRack;
    }

    public void setInventoryRack(String inventoryRack) {
        this.inventoryRack = inventoryRack;
    }

    public String getInventoryShelf() {
        return inventoryShelf;
    }

    public void setInventoryShelf(String inventoryShelf) {
        this.inventoryShelf = inventoryShelf;
    }

    public String getInventoryBy() {
        return inventoryBy;
    }

    public void setInventoryBy(String inventoryBy) {
        this.inventoryBy = inventoryBy;
    }

    public String getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(String inventoryDate) {
        this.inventoryDate = inventoryDate;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInventoryDateNew() {
        return inventoryDateNew;
    }

    public void setInventoryDateNew(String inventoryDateNew) {
        this.inventoryDateNew = inventoryDateNew;
    }

    public String getInventoryRemarks() {
        return inventoryRemarks;
    }

    public void setInventoryRemarks(String inventoryRemarks) {
        this.inventoryRemarks = inventoryRemarks;
    }

    public String getAging() {
        return aging;
    }

    public void setAging(String aging) {
        this.aging = aging;
    }

    public String getRmsLotEventConcat() {
        return rmsLotEventConcat;
    }

    public void setRmsLotEventConcat(String rmsLotEventConcat) {
        this.rmsLotEventConcat = rmsLotEventConcat;
    }

    public String getLotQty() {
        return lotQty;
    }

    public void setLotQty(String lotQty) {
        this.lotQty = lotQty;
    }

    public String getConcatRmsEvent() {
        return concatRmsEvent;
    }

    public void setConcatRmsEvent(String concatRmsEvent) {
        this.concatRmsEvent = concatRmsEvent;
    }

    public String getMthToScrapView() {
        return mthToScrapView;
    }

    public void setMthToScrapView(String mthToScrapView) {
        this.mthToScrapView = mthToScrapView;
    }

    public String getMthToScrapDb() {
        return mthToScrapDb;
    }

    public void setMthToScrapDb(String mthToScrapDb) {
        this.mthToScrapDb = mthToScrapDb;
    }
    
    
}
