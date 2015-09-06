package com.parking.data;


public class BaseResponse implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String rc; //result code
	private String em; //error message
	
	public String getRc() {
		return rc;
	}
	public void setRc(String rc) {
		this.rc = rc;
	}
	public String getEm() {
		return em;
	}
	public void setEm(String em) {
		this.em = em;
	}
	
	
	
}
