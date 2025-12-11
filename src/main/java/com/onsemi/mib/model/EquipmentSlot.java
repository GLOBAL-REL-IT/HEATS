package com.onsemi.mib.model;

public class EquipmentSlot {

	private String id;
	private String sptsPkid;
	private String slotId;
	private String equipmentId;

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

	public String getSlotId() {
		return slotId;
	}

	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

}