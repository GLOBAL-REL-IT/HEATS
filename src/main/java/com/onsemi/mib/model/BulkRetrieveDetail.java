<<<<<<< HEAD:src/main/java/com/onsemi/mib/model/BulkRetrieveDetail.java
package com.onsemi.mib.model;
=======
package com.onsemi.ostorms.model;
>>>>>>> 6efe209c46c7289024abf9bf84bf5b36e7452772:src/main/java/com/onsemi/ostorms/model/BulkRetrieveDetail.java

public class BulkRetrieveDetail {

    private String id;
    private String bulkId;
    private String reqId;
    private String invId;
    private String returnable;
    private String remarks;
    private String createdDate;
    private String flag;

    //from another table
    private String rmsLotEvent;
    private String location;
    private String qty;
    private String scrapDate;
    private String pkgFamily;
    private String pkgName;
    private String completeDate;

    public String getInvId() {
        return invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getRmsLotEvent() {
        return rmsLotEvent;
    }

    public void setRmsLotEvent(String rmsLotEvent) {
        this.rmsLotEvent = rmsLotEvent;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getScrapDate() {
        return scrapDate;
    }

    public void setScrapDate(String scrapDate) {
        this.scrapDate = scrapDate;
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

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBulkId() {
        return bulkId;
    }

    public void setBulkId(String bulkId) {
        this.bulkId = bulkId;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getReturnable() {
        return returnable;
    }

    public void setReturnable(String returnable) {
        this.returnable = returnable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
