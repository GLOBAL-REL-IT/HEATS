package com.onsemi.mib.model;

public class SRRetrieve {

    private String id;
    private String reqId;
    private String boxId;
    private String reqType;
    private String reqName;
    private String reqDate;
    private String reqDetails;
    private String reqRemarks;
    private String event;
    private String mthToScrap;
    private String pkgFamily;
    private String createdDate;
    private String createdBy;
    private String modifiedDate;
    private String modifiedBy;
    private String flag;
    private String status;
    private String rlReceivedBy;
    private String rlReceivedDate;

    //sendayan data
    private String verificationDate;
    private String verificationBy;
    private String invoiceNo;
    private String shipDate;
    private String shipBy;

    //data mgt
    private String aging;
    private String rmsLotEventConcat;
    private String lotQty;

    //kpi
    private String lotConcat;
    private String cycleTime1;
    private String cycleTime2;
    private String rmsNo;

    //shipment status
    private String shipStatus;
    private String shipRemark;

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus;
    }

    public String getShipRemark() {
        return shipRemark;
    }

    public void setShipRemark(String shipRemark) {
        this.shipRemark = shipRemark;
    }

    public SRRetrieve() {
    }

    public SRRetrieve(String reqId, String boxId, String invoiceNo, String verificationDate, String shipDate) {
        this.reqId = reqId;
        this.boxId = boxId;
        this.invoiceNo = invoiceNo;
        this.verificationDate = verificationDate;
        this.shipDate = shipDate;
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

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getReqDetails() {
        return reqDetails;
    }

    public void setReqDetails(String reqDetails) {
        this.reqDetails = reqDetails;
    }

    public String getReqRemarks() {
        return reqRemarks;
    }

    public void setReqRemarks(String reqRemarks) {
        this.reqRemarks = reqRemarks;
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

    public String getAging() {
        return aging;
    }

    public void setAging(String aging) {
        this.aging = aging;
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

    public String getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(String verificationDate) {
        this.verificationDate = verificationDate;
    }

    public String getVerificationBy() {
        return verificationBy;
    }

    public void setVerificationBy(String verificationBy) {
        this.verificationBy = verificationBy;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getShipBy() {
        return shipBy;
    }

    public void setShipBy(String shipBy) {
        this.shipBy = shipBy;
    }

    public String getRlReceivedBy() {
        return rlReceivedBy;
    }

    public void setRlReceivedBy(String rlReceivedBy) {
        this.rlReceivedBy = rlReceivedBy;
    }

    public String getRlReceivedDate() {
        return rlReceivedDate;
    }

    public void setRlReceivedDate(String rlReceivedDate) {
        this.rlReceivedDate = rlReceivedDate;
    }

    public String getLotConcat() {
        return lotConcat;
    }

    public void setLotConcat(String lotConcat) {
        this.lotConcat = lotConcat;
    }

    public String getRmsNo() {
        return rmsNo;
    }

    public void setRmsNo(String rmsNo) {
        this.rmsNo = rmsNo;
    }

    public String getCycleTime1() {
        return cycleTime1;
    }

    public void setCycleTime1(String cycleTime1) {
        this.cycleTime1 = cycleTime1;
    }

    public String getCycleTime2() {
        return cycleTime2;
    }

    public void setCycleTime2(String cycleTime2) {
        this.cycleTime2 = cycleTime2;
    }

}
