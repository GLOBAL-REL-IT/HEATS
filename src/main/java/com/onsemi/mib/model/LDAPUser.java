package com.onsemi.mib.model;

public class LDAPUser {

    /* LDAPUser */
    private String id;
    private String loginId;
    private String oncid;
    private String firstname;
    private String lastname;
    private String email;
    private String title;
    private String groupId;
    private String isActive;
    private String createdBy;
    private String createdTime;
    private String modifiedBy;
    private String modifiedTime;
    private String password;

    /*extra features*/
    private String srEmailShipping;
    private String srEmailRetrieve;
    private String hwEmailShipping;
    private String hwEmailRetrieve;
    private String featuresTestEmail;
    private String featuresTrackGts;
    private String featuresTrackInventory;
    private String featuresCreateGts;
    private String srEmailShipToRl;
    private String hwEmailShipToRl;
    private String scrap;

    /* LDAP User Group */
    private String groupCode;
    private String groupName;

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
    private String eqptFamilyAddGlobal;
    private String eqptRelTestGroupAddGlobal;

    private String requestAccess;

    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEqptFamilyAddGlobal() {
        return eqptFamilyAddGlobal;
    }

    public void setEqptFamilyAddGlobal(String eqptFamilyAddGlobal) {
        this.eqptFamilyAddGlobal = eqptFamilyAddGlobal;
    }

    public String getEqptRelTestGroupAddGlobal() {
        return eqptRelTestGroupAddGlobal;
    }

    public void setEqptRelTestGroupAddGlobal(String eqptRelTestGroupAddGlobal) {
        this.eqptRelTestGroupAddGlobal = eqptRelTestGroupAddGlobal;
    }

    public String getRequestAccess() {
        return requestAccess;
    }

    public void setRequestAccess(String requestAccess) {
        this.requestAccess = requestAccess;
    }

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

    public String getScrap() {
        return scrap;
    }

    public void setScrap(String scrap) {
        this.scrap = scrap;
    }

    public LDAPUser() {
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

    public String getOncid() {
        return oncid;
    }

    public void setOncid(String oncid) {
        this.oncid = oncid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getSrEmailShipping() {
        return srEmailShipping;
    }

    public void setSrEmailShipping(String srEmailShipping) {
        this.srEmailShipping = srEmailShipping;
    }

    public String getSrEmailRetrieve() {
        return srEmailRetrieve;
    }

    public void setSrEmailRetrieve(String srEmailRetrieve) {
        this.srEmailRetrieve = srEmailRetrieve;
    }

    public String getHwEmailShipping() {
        return hwEmailShipping;
    }

    public void setHwEmailShipping(String hwEmailShipping) {
        this.hwEmailShipping = hwEmailShipping;
    }

    public String getHwEmailRetrieve() {
        return hwEmailRetrieve;
    }

    public void setHwEmailRetrieve(String hwEmailRetrieve) {
        this.hwEmailRetrieve = hwEmailRetrieve;
    }

    public String getFeaturesTestEmail() {
        return featuresTestEmail;
    }

    public void setFeaturesTestEmail(String featuresTestEmail) {
        this.featuresTestEmail = featuresTestEmail;
    }

    public String getFeaturesTrackGts() {
        return featuresTrackGts;
    }

    public void setFeaturesTrackGts(String featuresTrackGts) {
        this.featuresTrackGts = featuresTrackGts;
    }

    public String getFeaturesTrackInventory() {
        return featuresTrackInventory;
    }

    public void setFeaturesTrackInventory(String featuresTrackInventory) {
        this.featuresTrackInventory = featuresTrackInventory;
    }

    public String getFeaturesCreateGts() {
        return featuresCreateGts;
    }

    public void setFeaturesCreateGts(String featuresCreateGts) {
        this.featuresCreateGts = featuresCreateGts;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSrEmailShipToRl() {
        return srEmailShipToRl;
    }

    public void setSrEmailShipToRl(String srEmailShipToRl) {
        this.srEmailShipToRl = srEmailShipToRl;
    }

    public String getHwEmailShipToRl() {
        return hwEmailShipToRl;
    }

    public void setHwEmailShipToRl(String hwEmailShipToRl) {
        this.hwEmailShipToRl = hwEmailShipToRl;
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
