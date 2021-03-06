package com.rab3tech.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(value=Include.NON_NULL)
@Data
public class ApplicationResponseVO {

	private int code;
	private String status;
	private String message;
	private int id;
	private String userid;
	
	
}
