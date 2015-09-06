package com.parking.data;

import java.io.Serializable;

public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String postalCode;
    private String phone;
    private String countryCode;


	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getPostalCode() {
		return postalCode;
	}



	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getCountryCode() {
		return countryCode;
	}



	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
