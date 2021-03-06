package com.test;

public class Utils {
	
	/**
	 * What ever input you
	 * give it's convert into uppercase
	 * with salutation!
	 * 
	 * @param name
	 * @return
	 */
	public String genString(String name){
		//Assume this is making connection with database
		return "MR. "+name.toUpperCase();
	}

}
