package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

public class BizService {
	private Utils utils=new Utils();
	
	public String magic(String name){
		ApplicationContext applicationContext=ContextLoader.getCurrentWebApplicationContext();
		String result=utils.genString(name);
		result=result+" THANK YOU";
		return result;
	}

}
