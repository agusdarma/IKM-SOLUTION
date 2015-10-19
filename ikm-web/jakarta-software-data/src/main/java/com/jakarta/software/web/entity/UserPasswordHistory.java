package com.jakarta.software.web.entity;

public class UserPasswordHistory implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private int userId;
	private int sequence;
	private String oldPassword;
	private String oldPassword2;
	private String oldPassword3;
	private String oldPassword4;
	private String oldPassword5;
	private String oldPassword6;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getOldPassword2() {
		return oldPassword2;
	}
	public void setOldPassword2(String oldPassword2) {
		this.oldPassword2 = oldPassword2;
	}
	public String getOldPassword3() {
		return oldPassword3;
	}
	public void setOldPassword3(String oldPassword3) {
		this.oldPassword3 = oldPassword3;
	}
	public String getOldPassword4() {
		return oldPassword4;
	}
	public void setOldPassword4(String oldPassword4) {
		this.oldPassword4 = oldPassword4;
	}
	public String getOldPassword5() {
		return oldPassword5;
	}
	public void setOldPassword5(String oldPassword5) {
		this.oldPassword5 = oldPassword5;
	}
	public String getOldPassword6() {
		return oldPassword6;
	}
	public void setOldPassword6(String oldPassword6) {
		this.oldPassword6 = oldPassword6;
	}
	
}
