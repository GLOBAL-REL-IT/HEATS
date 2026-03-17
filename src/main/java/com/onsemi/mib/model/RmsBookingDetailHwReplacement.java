package com.onsemi.mib.model;

public class RmsBookingDetailHwReplacement {

    private String id;
    private String bookingPkid;
    private String bookingHwPkid;
    private String itemPkid;
    private String itemId;
    private String remarks;
    private String createdBy;
    private String createdDate;

    private String itemType;
    private String status;
    private String qty;
    private String bookingHwId;
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getBookingHwId() {
        return bookingHwId;
    }

    public void setBookingHwId(String bookingHwId) {
        this.bookingHwId = bookingHwId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingPkid() {
        return bookingPkid;
    }

    public void setBookingPkid(String bookingPkid) {
        this.bookingPkid = bookingPkid;
    }

    public String getBookingHwPkid() {
        return bookingHwPkid;
    }

    public void setBookingHwPkid(String bookingHwPkid) {
        this.bookingHwPkid = bookingHwPkid;
    }

    public String getItemPkid() {
        return itemPkid;
    }

    public void setItemPkid(String itemPkid) {
        this.itemPkid = itemPkid;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
