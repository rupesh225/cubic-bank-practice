package com.rab3tech.vo;

public class PayeeApproveVO {

	private String accountNumber;
	private int urn;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getUrn() {
		return urn;
	}

	public void setUrn(int urn) {
		this.urn = urn;
	}

	@Override
	public String toString() {
		return "PayeeApproveVO [accountNumber=" + accountNumber + ", urn=" + urn + "]";
	}

}
