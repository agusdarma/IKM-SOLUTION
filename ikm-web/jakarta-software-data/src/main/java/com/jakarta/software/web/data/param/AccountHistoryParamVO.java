package com.jakarta.software.web.data.param;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.jakarta.software.web.entity.Account;

public class AccountHistoryParamVO extends Account {

	private static final long serialVersionUID = 1L;

	private int id;
	private String accountNoOld;
	private String cardNoOld;

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

	public String getAccountNoOld() {
		return accountNoOld;
	}

	public void setAccountNoOld(String accountNoOld) {
		this.accountNoOld = accountNoOld;
	}

	public String getCardNoOld() {
		return cardNoOld;
	}

	public void setCardNoOld(String cardNoOld) {
		this.cardNoOld = cardNoOld;
	}

}
