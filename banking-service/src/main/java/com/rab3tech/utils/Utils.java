package com.rab3tech.utils;

import java.util.Random;

public class Utils {
	
	public static String genRandomAlphaNum() {
		// pseudo code
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "-");
		for (int i = 0; i < 5; i++)
		    sb.append(chars[rnd.nextInt(chars.length)]);
		return sb.toString();
	}
	
	public static String generateCustomerAccount() {
	    Random r = new Random(System.currentTimeMillis());
	    return "00"+(1000000000 + r.nextInt(2000000000))+"";
	}
	
	public static void main(String[] args) {
		System.out.println(generateCustomerAccount());
	}
	
	public static int generateURN() {
		Random random = new Random();
		StringBuilder number = new StringBuilder();
		int three=random.nextInt(1000);
		if(three<=99){
			number.append("9"+three);
		}else{
			number.append(three);
		}
		number.append(String.format("%03d", random.nextInt(1000)));
		return Integer.parseInt(number.toString());
	}
	

}
