package com.rab3tech.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class LocationVO {
	private int id;
	private String lcode;
	private String name;
	private Timestamp doe;
	private Timestamp dom;
	private LoginVO login;
}
