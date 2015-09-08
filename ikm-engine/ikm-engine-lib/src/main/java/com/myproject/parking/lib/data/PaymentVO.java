package com.myproject.parking.lib.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class PaymentVO extends LoginData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Address address;
	private CreditCard creditCard;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
