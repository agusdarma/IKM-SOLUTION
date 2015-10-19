package com.jakarta.software.web.entity;

public class PasswordHistory {

	private int userId;
	private int sequence;
	private int  updatedBy;
	private String passHistory1;
	private String passHistory2;
	private String passHistory3;
	private String passHistory4;
	private String passHistory5;
	private String passHistory6;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getPassHistory1() {
		return passHistory1;
	}
	public void setPassHistory1(String passHistory1) {
		this.passHistory1 = passHistory1;
	}
	public String getPassHistory2() {
		return passHistory2;
	}
	public void setPassHistory2(String passHistory2) {
		this.passHistory2 = passHistory2;
	}
	public String getPassHistory3() {
		return passHistory3;
	}
	public void setPassHistory3(String passHistory3) {
		this.passHistory3 = passHistory3;
	}
	public String getPassHistory4() {
		return passHistory4;
	}
	public void setPassHistory4(String passHistory4) {
		this.passHistory4 = passHistory4;
	}
	public String getPassHistory5() {
		return passHistory5;
	}
	public void setPassHistory5(String passHistory5) {
		this.passHistory5 = passHistory5;
	}
	public String getPassHistory6() {
		return passHistory6;
	}
	public void setPassHistory6(String passHistory6) {
		this.passHistory6 = passHistory6;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
