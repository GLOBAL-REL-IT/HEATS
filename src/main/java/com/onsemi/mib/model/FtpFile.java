package com.onsemi.mib.model;

public class FtpFile {

    private String completeDate;
    private String mthToScrap;
    private String rmsId;
    private String lotType;
    private String pkgName;
    private String event;
    private String unitQty;
    private String processStatus;
    private String pkgFamily;
    private String scrapDate;

    public FtpFile(String rmsId, String pkgName, String event, String lotType, String pkgFamily, String completeDate, String scrapDate, String mthToScrap, String processStatus, String unitQty) {
        this.completeDate = completeDate;
        this.mthToScrap = mthToScrap;
        this.rmsId = rmsId;
        this.lotType = lotType;
        this.pkgName = pkgName;
        this.event = event;
        this.unitQty = unitQty;
        this.processStatus = processStatus;
        this.pkgFamily = pkgFamily;
        this.scrapDate = scrapDate;
    }
    
//    public FtpFile(String completeDate, String mthToScrap, String rmsId, String lotType, String pkgName, String event, String unitQty, String processStatus, String pkgFamily, String scrapDate) {
//        this.completeDate = completeDate;
//        this.mthToScrap = mthToScrap;
//        this.rmsId = rmsId;
//        this.lotType = lotType;
//        this.pkgName = pkgName;
//        this.event = event;
//        this.unitQty = unitQty;
//        this.processStatus = processStatus;
//        this.pkgFamily = pkgFamily;
//        this.scrapDate = scrapDate;
//    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getMthToScrap() {
        return mthToScrap;
    }

    public void setMthToScrap(String mthToScrap) {
        this.mthToScrap = mthToScrap;
    }

    public String getRmsId() {
        return rmsId;
    }

    public void setRmsId(String rmsId) {
        this.rmsId = rmsId;
    }

    public String getLotType() {
        return lotType;
    }

    public void setLotType(String lotType) {
        this.lotType = lotType;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(String unitQty) {
        this.unitQty = unitQty;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getPkgFamily() {
        return pkgFamily;
    }

    public void setPkgFamily(String pkgFamily) {
        this.pkgFamily = pkgFamily;
    }

    public String getScrapDate() {
        return scrapDate;
    }

    public void setScrapDate(String scrapDate) {
        this.scrapDate = scrapDate;
    }

}
