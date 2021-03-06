package com.rab3tech.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UpdateLocationVO {
	
	private int id;
	private String owner;
	private String lcode;
	private String location;


}
