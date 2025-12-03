package com.onsemi.mib.model;

public class UserSession {

    private String id;
    private String loginId;
    private String fullname;
    private String email;
    private String group;
    private String[] programAccess;
    private String firstName;

    private String itemEdit;
    private String itemDelete;
    private String itemAdd;
    private String itemActivityConfig;
    private String itemActivityAdd;
    private String itemActivityEdit;
    private String itemHardwareAdd;
    private String itemHardwareEdit;
    private String itemHardwareDelete;
    private String itemMovementAdd;
    private String itemSfRecall;

    public String getItemAdd() {
        return itemAdd;
    }

    public void setItemAdd(String itemAdd) {
        this.itemAdd = itemAdd;
    }

    public String getItemEdit() {
        return itemEdit;
    }

    public void setItemEdit(String itemEdit) {
        this.itemEdit = itemEdit;
    }

    public String getItemDelete() {
        return itemDelete;
    }

    public void setItemDelete(String itemDelete) {
        this.itemDelete = itemDelete;
    }

    public String getItemActivityConfig() {
        return itemActivityConfig;
    }

    public void setItemActivityConfig(String itemActivityConfig) {
        this.itemActivityConfig = itemActivityConfig;
    }

    public String getItemActivityAdd() {
        return itemActivityAdd;
    }

    public void setItemActivityAdd(String itemActivityAdd) {
        this.itemActivityAdd = itemActivityAdd;
    }

    public String getItemActivityEdit() {
        return itemActivityEdit;
    }

    public void setItemActivityEdit(String itemActivityEdit) {
        this.itemActivityEdit = itemActivityEdit;
    }

    public String getItemHardwareAdd() {
        return itemHardwareAdd;
    }

    public void setItemHardwareAdd(String itemHardwareAdd) {
        this.itemHardwareAdd = itemHardwareAdd;
    }

    public String getItemHardwareEdit() {
        return itemHardwareEdit;
    }

    public void setItemHardwareEdit(String itemHardwareEdit) {
        this.itemHardwareEdit = itemHardwareEdit;
    }

    public String getItemHardwareDelete() {
        return itemHardwareDelete;
    }

    public void setItemHardwareDelete(String itemHardwareDelete) {
        this.itemHardwareDelete = itemHardwareDelete;
    }

    public String getItemMovementAdd() {
        return itemMovementAdd;
    }

    public void setItemMovementAdd(String itemMovementAdd) {
        this.itemMovementAdd = itemMovementAdd;
    }

    public String getItemSfRecall() {
        return itemSfRecall;
    }

    public void setItemSfRecall(String itemSfRecall) {
        this.itemSfRecall = itemSfRecall;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String[] getProgramAccess() {
        return programAccess;
    }

    public void setProgramAccess(String[] programAccess) {
        this.programAccess = programAccess;
    }
}
