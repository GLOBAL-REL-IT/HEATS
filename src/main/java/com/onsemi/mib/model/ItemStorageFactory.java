package com.onsemi.mib.model;

public class ItemStorageFactory {

    private String id;
    private String sfPkid;
    private String itemPkid;
    private String movementType;
    private String qty;
    private String rack;
    private String shelf;
    private String movementDatetime;
    private String flag;

    private String itemId;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSfPkid() {
        return sfPkid;
    }

    public void setSfPkid(String sfPkid) {
        this.sfPkid = sfPkid;
    }

    public String getItemPkid() {
        return itemPkid;
    }

    public void setItemPkid(String itemPkid) {
        this.itemPkid = itemPkid;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getMovementDatetime() {
        return movementDatetime;
    }

    public void setMovementDatetime(String movementDatetime) {
        this.movementDatetime = movementDatetime;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
