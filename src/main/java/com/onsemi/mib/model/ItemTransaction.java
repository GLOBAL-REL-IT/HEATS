package com.onsemi.mib.model;

public class ItemTransaction {

	private String id;
	private String sptsPkid;
	private String siteName;
	private String dateTime;
	private String itemPkid;
	private String transType;
	private String transTypeName;
	private String transQty;
	private String transInQty;
	private String transOutQty;
	private String alu;
	private String remarks;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSptsPkid() {
		return sptsPkid;
	}

	public void setSptsPkid(String sptsPkid) {
		this.sptsPkid = sptsPkid;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getItemPkid() {
		return itemPkid;
	}

	public void setItemPkid(String itemPkid) {
		this.itemPkid = itemPkid;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransTypeName() {
		return transTypeName;
	}

	public void setTransTypeName(String transTypeName) {
		this.transTypeName = transTypeName;
	}

	public String getTransQty() {
		return transQty;
	}

	public void setTransQty(String transQty) {
		this.transQty = transQty;
	}

	public String getTransInQty() {
		return transInQty;
	}

	public void setTransInQty(String transInQty) {
		this.transInQty = transInQty;
	}

	public String getTransOutQty() {
		return transOutQty;
	}

	public void setTransOutQty(String transOutQty) {
		this.transOutQty = transOutQty;
	}

	public String getAlu() {
		return alu;
	}

	public void setAlu(String alu) {
		this.alu = alu;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}