package com.rab3tech.vo;

public class ResetPasswordVO {
	
	private String email;
	private String newPassword;
	private String  confirmPassword;

	
	public ResetPasswordVO(String email, String newPassword, String confirmPassword) {
		this.email = email;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "ChangePasswordVO [currentPassword=" + ", newPassword=" + newPassword
				+ ", confirmPassword=" + confirmPassword + "]";
	}
	

}
