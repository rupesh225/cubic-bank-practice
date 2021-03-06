package com.rab3tech.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class SecurityQuestionsVO {
	
	private int qid;
	private String questions;
	private String status;
	private String owner;
	private Timestamp createdate;
	private Timestamp updatedate;
		
	
}
