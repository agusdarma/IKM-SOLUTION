package com.myproject.parking.lib.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class MallSlotAvailable implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id; // mall id (int)
	private int slotAvailable; // available slot per mall
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSlotAvailable() {
		return slotAvailable;
	}
	public void setSlotAvailable(int slotAvailable) {
		this.slotAvailable = slotAvailable;
	}
	
}
