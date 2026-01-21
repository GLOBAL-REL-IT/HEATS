package com.onsemi.mib.model;

public class ItemHardwareLog {

	private String id;
	private String mibHardwareId;
	private String rmsEvent;
	private String alu;
	private String createdBy;
	private String createdDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMibHardwareId() {
		return mibHardwareId;
	}

	public void setMibHardwareId(String mibHardwareId) {
		this.mibHardwareId = mibHardwareId;
	}

	public String getRmsEvent() {
		return rmsEvent;
	}

	public void setRmsEvent(String rmsEvent) {
		this.rmsEvent = rmsEvent;
	}

	public String getAlu() {
		return alu;
	}

	public void setAlu(String alu) {
		this.alu = alu;
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