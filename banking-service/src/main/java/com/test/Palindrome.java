package com.test;

public class Palindrome {
	
	//a
	public boolean isPal(String name) {
		if (name == null || name.length() == 0) {
			throw new RuntimeException("Sorry input length cannot be zero!");
		}
		if(name.length()==1){
			throw new RuntimeException("Sorry input length 1 is not valid palindome!");	
		}
		boolean status = true;
		// madam
		for (int x = 0, j = name.length() - 1; x < name.length() / 2; x++, j--) {
			if (name.charAt(x) != name.charAt(j)) {
				status = false;
				break;
			}
		}
		return status;
	}
	
	/*public static void main(String[] args) {
		System.out.println(new Palindrome().isPal("madam"));
		System.out.println(new Palindrome().isPal("nagen"));
		System.out.println(new Palindrome().isPal("n"));
	}*/

}
