package com.rab3tech;

import java.io.IOException;

public class Note28 {

	public static void main(String[] args) throws IOException, InterruptedException {
	
		int result=Runtime.getRuntime().availableProcessors();
		System.out.println("availableProcessors = "+result);
		
		/*for(int x=1;x<=28;x++){
			Runtime.getRuntime().exec("notepad");
			Thread.sleep(3000);
		}*/
		
	}
	
}
