package com.onsemi.mib.model;

public class User {

    /* User */
    private String id;
    private String loginId;
    private String password;
    private String groupId;
    private String isActive;
    private String createdBy;
    private String createdTime;
    private String modifiedBy;
    private String modifiedTime;

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

    private String eqptAdd;
    private String eqptEdit;
    private String eqptDelete;
    private String eqptFamilyAdd;
    private String eqptFamilyDelete;
    private String eqptRelTestGroupAdd;
    private String eqptRelTestGroupDelete;
    private String eqptTechAdd;
    private String eqptTechDelete;
    private String eqptMonAdd;
    private String eqptMonDelete;
    private String eqptViMonAdd;
    private String eqptViMonDelete;

    public User() {
    }

    public User(String id, String loginId, String password, String groupId, String isActive, String createdBy, String createdTime, String modifiedBy, String modifiedTime) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.groupId = groupId;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.modifiedBy = modifiedBy;
        this.modifiedTime = modifiedTime;
    }

    public User(String id, String loginId, String password, String groupId, String isActive, String createdBy, String createdTime, String modifiedBy, String modifiedTime, String groupCode, String groupName, String fullname, String email) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.groupId = groupId;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.createdTime = createdTime;
        this.modifiedBy = modifiedBy;
        this.modifiedTime = modifiedTime;
        this.groupCode = groupCode;
        this.groupName = groupName;
        this.fullname = fullname;
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /* User Group */
    private String groupCode;
    private String groupName;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /* User Profile */
    private String fullname;
    private String email;

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

    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getItemAdd() {
        return itemAdd;
    }

    public void setItemAdd(String itemAdd) {
        this.itemAdd = itemAdd;
    }

    public String getEqptAdd() {
        return eqptAdd;
    }

    public void setEqptAdd(String eqptAdd) {
        this.eqptAdd = eqptAdd;
    }

    public String getEqptEdit() {
        return eqptEdit;
    }

    public void setEqptEdit(String eqptEdit) {
        this.eqptEdit = eqptEdit;
    }

    public String getEqptDelete() {
        return eqptDelete;
    }

    public void setEqptDelete(String eqptDelete) {
        this.eqptDelete = eqptDelete;
    }

    public String getEqptFamilyAdd() {
        return eqptFamilyAdd;
    }

    public void setEqptFamilyAdd(String eqptFamilyAdd) {
        this.eqptFamilyAdd = eqptFamilyAdd;
    }

    public String getEqptFamilyDelete() {
        return eqptFamilyDelete;
    }

    public void setEqptFamilyDelete(String eqptFamilyDelete) {
        this.eqptFamilyDelete = eqptFamilyDelete;
    }

    public String getEqptRelTestGroupAdd() {
        return eqptRelTestGroupAdd;
    }

    public void setEqptRelTestGroupAdd(String eqptRelTestGroupAdd) {
        this.eqptRelTestGroupAdd = eqptRelTestGroupAdd;
    }

    public String getEqptRelTestGroupDelete() {
        return eqptRelTestGroupDelete;
    }

    public void setEqptRelTestGroupDelete(String eqptRelTestGroupDelete) {
        this.eqptRelTestGroupDelete = eqptRelTestGroupDelete;
    }

    public String getEqptTechAdd() {
        return eqptTechAdd;
    }

    public void setEqptTechAdd(String eqptTechAdd) {
        this.eqptTechAdd = eqptTechAdd;
    }

    public String getEqptTechDelete() {
        return eqptTechDelete;
    }

    public void setEqptTechDelete(String eqptTechDelete) {
        this.eqptTechDelete = eqptTechDelete;
    }

    public String getEqptMonAdd() {
        return eqptMonAdd;
    }

    public void setEqptMonAdd(String eqptMonAdd) {
        this.eqptMonAdd = eqptMonAdd;
    }

    public String getEqptMonDelete() {
        return eqptMonDelete;
    }

    public void setEqptMonDelete(String eqptMonDelete) {
        this.eqptMonDelete = eqptMonDelete;
    }

    public String getEqptViMonAdd() {
        return eqptViMonAdd;
    }

    public void setEqptViMonAdd(String eqptViMonAdd) {
        this.eqptViMonAdd = eqptViMonAdd;
    }

    public String getEqptViMonDelete() {
        return eqptViMonDelete;
    }

    public void setEqptViMonDelete(String eqptViMonDelete) {
        this.eqptViMonDelete = eqptViMonDelete;
    }

}
