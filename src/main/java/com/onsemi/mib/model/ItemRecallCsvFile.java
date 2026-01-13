package com.onsemi.mib.model;

public class ItemRecallCsvFile {

    private String id;
    private String file;
    private String active;
    private String remarks;
    private String emailCsv;
    private String emailNotification;

    public String getEmailCsv() {
        return emailCsv;
    }

    public void setEmailCsv(String emailCsv) {
        this.emailCsv = emailCsv;
    }

    public String getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(String emailNotification) {
        this.emailNotification = emailNotification;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
