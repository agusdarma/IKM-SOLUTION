package com.myproject.parking.lib.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class TransactionDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long transactionId; // isinya id dari si header
	private String nameItem;
	private Long priceEachIdr;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getNameItem() {
		return nameItem;
	}

	public void setNameItem(String nameItem) {
		this.nameItem = nameItem;
	}

	public Long getPriceEachIdr() {
		return priceEachIdr;
	}

	public void setPriceEachIdr(Long priceEachIdr) {
		this.priceEachIdr = priceEachIdr;
	}

	
}
