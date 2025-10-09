<<<<<<< HEAD:src/main/java/com/onsemi/mib/model/BulkRetrieve.java
package com.onsemi.mib.model;
=======
package com.onsemi.ostorms.model;
>>>>>>> 6efe209c46c7289024abf9bf84bf5b36e7452772:src/main/java/com/onsemi/ostorms/model/BulkRetrieve.java

public class BulkRetrieve {

	private String id;
	private String requestor;
	private String date;
	private String flag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRequestor() {
		return requestor;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}