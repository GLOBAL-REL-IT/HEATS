package com.onsemi.mib.model;

public class SRArchive {

    private String id;
    private String ftpId;
    private String reqType;
    private String reasonsExc;
    private String reqName;
    private String relReqName;
    private String relDateReq;
    private String remarks;
    private String modifiedDate;
    private String modifiedBy;
    private String createdDate;
    private String createdBy;
    private String status;
    private String flag;

    //ftpdata
    private String groupId;
    private String rmsId;
    private String rmsEvent;
    private String lotType;
    private String rmsLotEvent;
    private String lotQty;
    private String rmsStatus;
    private String pStatus;
    private String pStatusDate;
    private String pkgFamily;
    private String pkgName;
    private String scrapDate;
    private String mthToScrap;
    private String compDate;
    private String ftpStatus;
    private String ftpFlag;
    private String cancelBy;
    private String cancelDate;

    //others
    private String lotConcat;
    private String rmsLotConcat;
    private String aging;

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

    public String getFtpId() {
        return ftpId;
    }

    public void setFtpId(String ftpId) {
        this.ftpId = ftpId;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getReasonsExc() {
        return reasonsExc;
    }

    public void setReasonsExc(String reasonsExc) {
        this.reasonsExc = reasonsExc;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public String getRelReqName() {
        return relReqName;
    }

    public void setRelReqName(String relReqName) {
        this.relReqName = relReqName;
    }

    public String getRelDateReq() {
        return relDateReq;
    }

    public void setRelDateReq(String relDateReq) {
        this.relDateReq = relDateReq;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    //ftp
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

    public String getRmsEvent() {
        return rmsEvent;
    }

    public void setRmsEvent(String rmsEvent) {
        this.rmsEvent = rmsEvent;
    }

    public String getLotType() {
        return lotType;
    }

    public void setLotType(String lotType) {
        this.lotType = lotType;
    }

    public String getRmsLotEvent() {
        return rmsLotEvent;
    }

    public void setRmsLotEvent(String rmsLotEvent) {
        this.rmsLotEvent = rmsLotEvent;
    }

    public String getLotQty() {
        return lotQty;
    }

    public void setLotQty(String lotQty) {
        this.lotQty = lotQty;
    }

    public String getRmsStatus() {
        return rmsStatus;
    }

    public void setRmsStatus(String rmsStatus) {
        this.rmsStatus = rmsStatus;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getpStatusDate() {
        return pStatusDate;
    }

    public void setpStatusDate(String pStatusDate) {
        this.pStatusDate = pStatusDate;
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

    public String getCompDate() {
        return compDate;
    }

    public void setCompDate(String compDate) {
        this.compDate = compDate;
    }

    public String getFtpStatus() {
        return ftpStatus;
    }

    public void setFtpStatus(String ftpStatus) {
        this.ftpStatus = ftpStatus;
    }

    public String getFtpFlag() {
        return ftpFlag;
    }

    public void setFtpFlag(String ftpFlag) {
        this.ftpFlag = ftpFlag;
    }

    public String getLotConcat() {
        return lotConcat;
    }

    public void setLotConcat(String lotConcat) {
        this.lotConcat = lotConcat;
    }

    public String getRmsLotConcat() {
        return rmsLotConcat;
    }

    //others
    public void setRmsLotConcat(String rmsLotConcat) {
        this.rmsLotConcat = rmsLotConcat;
    }

    public String getAging() {
        return aging;
    }

    public void setAging(String aging) {
        this.aging = aging;
    }

}
