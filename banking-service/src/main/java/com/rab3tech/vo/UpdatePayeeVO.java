package com.rab3tech.vo;


public class UpdatePayeeVO{
	private int payeeid;
	private String payeeNickName;
	public int getPayeeid() {
		return payeeid;
	}
	public void setPayeeid(int payeeid) {
		this.payeeid = payeeid;
	}
	public String getPayeeNickName() {
		return payeeNickName;
	}
	public void setPayeeNickName(String payeeNickName) {
		this.payeeNickName = payeeNickName;
	}
	@Override
	public String toString() {
		return "UpdatePayee [payeeid=" + payeeid + ", payeeNickName=" + payeeNickName + "]";
	}
	
	
}
