package com.rab3tech.vo;

import lombok.Data;

@Data
public class ChangePasswordRequestVO {

	private String loginid;
	private String newpassword;
	private String passcode;
	
}
