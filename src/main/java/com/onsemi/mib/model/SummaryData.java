package com.onsemi.mib.model;

public class SummaryData {
    private String pkgFamily;
    private String event;
    private String mthToScrap;
    private String mthToScrapDetails;
    private String rmsNo;
    private String lotNo;
    private String ftpId;
    
    private String totalLot;
    private String mthToScrapView;
    
    public SummaryData(String pkgFamily, String event, String mthToScrap, String totalLot) {
        this.pkgFamily = pkgFamily;
        this.event = event;
        this.mthToScrap = mthToScrap;
        this.totalLot = totalLot;
    }

    public SummaryData() {
        
    }

    
    public String getPkgFamily() {
        return pkgFamily;
    }

    public void setPkgFamily(String pkgFamily) {
        this.pkgFamily = pkgFamily;
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

    public String getMthToScrapDetails() {
        return mthToScrapDetails;
    }

    public void setMthToScrapDetails(String mthToScrapDetails) {
        this.mthToScrapDetails = mthToScrapDetails;
    }

    public String getRmsNo() {
        return rmsNo;
    }

    public void setRmsNo(String rmsNo) {
        this.rmsNo = rmsNo;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getFtpId() {
        return ftpId;
    }

    public void setFtpId(String ftpId) {
        this.ftpId = ftpId;
    }

    public String getTotalLot() {
        return totalLot;
    }

    public void setTotalLot(String totalLot) {
        this.totalLot = totalLot;
    }

    public String getMthToScrapView() {
        return mthToScrapView;
    }

    public void setMthToScrapView(String mthToScrapView) {
        this.mthToScrapView = mthToScrapView;
    }
}
