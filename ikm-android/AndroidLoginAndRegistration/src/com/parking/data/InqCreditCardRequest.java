package com.parking.data;


public class InqCreditCardRequest implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String password;
	private String newPassword;
	private String sessionKey;
	
	/**
	 * CC data
	 */
	private String noCC;
	private int cardExpireMonth;
	private int cardExpireYear;
	private String cardCvv;	
    private boolean secure;
    private String bank = null;
    private String gross_amount;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getNoCC() {
		return noCC;
	}

	public void setNoCC(String noCC) {
		this.noCC = noCC;
	}

	public int getCardExpireMonth() {
		return cardExpireMonth;
	}

	public void setCardExpireMonth(int cardExpireMonth) {
		this.cardExpireMonth = cardExpireMonth;
	}

	public int getCardExpireYear() {
		return cardExpireYear;
	}

	public void setCardExpireYear(int cardExpireYear) {
		this.cardExpireYear = cardExpireYear;
	}

	public String getCardCvv() {
		return cardCvv;
	}

	public void setCardCvv(String cardCvv) {
		this.cardCvv = cardCvv;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getGross_amount() {
		return gross_amount;
	}

	public void setGross_amount(String gross_amount) {
		this.gross_amount = gross_amount;
	}

}
