package com.myproject.parking.lib.data;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class VeriTransVO extends LoginData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String tokenId;
	private String bookingId;
	private Long TotalPriceIdr;
	
	private CustomerDetail customerDetail;
	private TransactionDetails transactionDetails;
	private List<Product> listProducts;
	private String paymentMethod;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public CustomerDetail getCustomerDetail() {
		return customerDetail;
	}

	public void setCustomerDetail(CustomerDetail customerDetail) {
		this.customerDetail = customerDetail;
	}

	

	public List<Product> getListProducts() {
		return listProducts;
	}

	public void setListProducts(List<Product> listProducts) {
		this.listProducts = listProducts;
	}

	public TransactionDetails getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(TransactionDetails transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Long getTotalPriceIdr() {
		return TotalPriceIdr;
	}

	public void setTotalPriceIdr(Long totalPriceIdr) {
		TotalPriceIdr = totalPriceIdr;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
}
