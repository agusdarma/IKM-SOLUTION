package com.jakarta.software.web.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserData.class);
	private final SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyyMMddHHmmss");

	private int pk;  // U3PK       INTEGER GENERATED ALWAYS AS IDENTITY,
	private String userId;  //  U3UID      CHAR(10) NOT NULL,
	private String trxDate;  // U3TXDT     CHAR(14),
	private String session;  //  U3SESS     CHAR(25),
	private String trxType;  // U3TXTY     CHAR(20),
	private String trxAction;  // U3TXAC     CHAR(20),
	private String message;  //  U3COMM     VARCHAR(160)
	
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTrxDate() {
		return trxDate;
	}
	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}
	public Date getTrxDateApps() {
		if (trxDate == null)
			return null;
		try {
			return sdfDateTime.parse(trxDate);
		} catch (Exception e) {
			LOGGER.warn("Invalid statusDate: " + trxDate, e);
			return null;
		}
	}
	public void setTrxDateApps(Date trxDate) {
		if (trxDate == null)
			this.trxDate = null;
		else
			this.trxDate = sdfDateTime.format(trxDate);
	}
	
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}

	public String getTrxType() {
		return trxType;
	}
	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	public String getTrxAction() {
		return trxAction;
	}
	public void setTrxAction(String trxAction) {
		this.trxAction = trxAction;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
