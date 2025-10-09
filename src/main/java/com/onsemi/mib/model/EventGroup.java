package com.onsemi.mib.model;

public class EventGroup {
    //eventGroup
    private String groupId;
    private String eventGroupCode;
    private String eventGroupDetails;
    private String eventGroupStatus;
    private String eventGroupFlag;
    private String groupModifiedBy;
    private String groupModifiedDate;
    private String groupCreatedBy;
    private String groupCreatedDate;
    
    //event
    private String eventId;
    private String eventGroupId;
    private String eventCode;
    private String eventName;
    private String requirementStatus;
    private String eventCreatedBy;
    private String eventCreatedDate;
    private String eventModifiedBy;
    private String eventModifiedDate;

    public EventGroup() {
    }

    public EventGroup(String groupId, String eventGroupCode, String eventGroupDetails, String eventGroupStatus, String eventGroupFlag, String groupModifiedBy, String groupModifiedDate, String groupCreatedBy, String groupCreatedDate ) {
        this.groupId = groupId;
        this.eventGroupCode = eventGroupCode;
        this.eventGroupDetails = eventGroupDetails;
        this.eventGroupStatus = eventGroupStatus;
        this.eventGroupFlag = eventGroupFlag;
        this.groupModifiedBy = groupModifiedBy;
        this.groupModifiedDate = groupModifiedDate;
        this.groupCreatedBy = groupCreatedBy;
        this.groupCreatedDate = groupCreatedDate;
    }

    public EventGroup(String groupId, String eventGroupCode, String eventGroupDetails, String eventGroupStatus, String eventGroupFlag, String groupCreatedBy, String groupCreatedDate, String groupModifiedBy, String groupModifiedDate, String eventId, String eventGroupId, String eventCode, String eventName, String eventCreatedBy, String eventCreatedDate, String eventModifiedBy, String eventModifiedDate, String requirementStatus) {
        this.groupId = groupId;
        this.eventGroupCode = eventGroupCode;
        this.eventGroupDetails = eventGroupDetails;
        this.eventGroupStatus = eventGroupStatus;
        this.eventGroupFlag = eventGroupFlag;
        this.groupCreatedBy = groupCreatedBy;
        this.groupCreatedDate = groupCreatedDate;
        this.groupModifiedBy = groupModifiedBy;
        this.groupModifiedDate = groupModifiedDate;
        this.eventId = eventId;
        this.eventGroupId = eventGroupId;
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.eventCreatedBy = eventCreatedBy;
        this.eventCreatedDate = eventCreatedDate;
        this.eventModifiedBy = eventModifiedBy;
        this.eventModifiedDate = eventModifiedDate;
        this.requirementStatus = requirementStatus;
    }

    
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getEventGroupCode() {
        return eventGroupCode;
    }

    public void setEventGroupCode(String eventGroupCode) {
        this.eventGroupCode = eventGroupCode;
    }

    public String getEventGroupDetails() {
        return eventGroupDetails;
    }

    public void setEventGroupDetails(String eventGroupDetails) {
        this.eventGroupDetails = eventGroupDetails;
    }

    public String getEventGroupStatus() {
        return eventGroupStatus;
    }

    public void setEventGroupStatus(String eventGroupStatus) {
        this.eventGroupStatus = eventGroupStatus;
    }

    public String getEventGroupFlag() {
        return eventGroupFlag;
    }

    public void setEventGroupFlag(String eventGroupFlag) {
        this.eventGroupFlag = eventGroupFlag;
    }

    public String getGroupCreatedBy() {
        return groupCreatedBy;
    }

    public void setGroupCreatedBy(String groupCreatedBy) {
        this.groupCreatedBy = groupCreatedBy;
    }

    public String getGroupCreatedDate() {
        return groupCreatedDate;
    }

    public void setGroupCreatedDate(String groupCreatedDate) {
        this.groupCreatedDate = groupCreatedDate;
    }

    public String getGroupModifiedBy() {
        return groupModifiedBy;
    }

    public void setGroupModifiedBy(String groupModifiedBy) {
        this.groupModifiedBy = groupModifiedBy;
    }

    public String getGroupModifiedDate() {
        return groupModifiedDate;
    }

    public void setGroupModifiedDate(String groupModifiedDate) {
        this.groupModifiedDate = groupModifiedDate;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventGroupId() {
        return eventGroupId;
    }

    public void setEventGroupId(String eventGroupId) {
        this.eventGroupId = eventGroupId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventCreatedBy() {
        return eventCreatedBy;
    }

    public void setEventCreatedBy(String eventCreatedBy) {
        this.eventCreatedBy = eventCreatedBy;
    }

    public String getEventCreatedDate() {
        return eventCreatedDate;
    }

    public void setEventCreatedDate(String eventCreatedDate) {
        this.eventCreatedDate = eventCreatedDate;
    }

    public String getEventModifiedBy() {
        return eventModifiedBy;
    }

    public void setEventModifiedBy(String eventModifiedBy) {
        this.eventModifiedBy = eventModifiedBy;
    }

    public String getEventModifiedDate() {
        return eventModifiedDate;
    }

    public void setEventModifiedDate(String eventModifiedDate) {
        this.eventModifiedDate = eventModifiedDate;
    }

    public String getRequirementStatus() {
        return requirementStatus;
    }

    public void setRequirementStatus(String requirementStatus) {
        this.requirementStatus = requirementStatus;
    }

    
}
