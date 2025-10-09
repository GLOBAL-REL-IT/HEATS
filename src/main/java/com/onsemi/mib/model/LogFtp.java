<<<<<<< HEAD:src/main/java/com/onsemi/mib/model/LogFtp.java
package com.onsemi.mib.model;
=======
package com.onsemi.ostorms.model;
>>>>>>> 6efe209c46c7289024abf9bf84bf5b36e7452772:src/main/java/com/onsemi/ostorms/model/LogFtp.java

public class LogFtp {

	private String id;
	private String ftpId;
	private String detail;
	private String createdBy;
	private String createdDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFtpId() {
		return ftpId;
	}

	public void setFtpId(String ftpId) {
		this.ftpId = ftpId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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