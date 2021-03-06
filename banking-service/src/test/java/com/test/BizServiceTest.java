package com.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BizServiceTest {
	
	@Mock
	private Utils utils;
	
	@InjectMocks
	private BizService bizService=new BizService();
	
	@Before
	public void init() {
		 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testMagicWhenBlankInput() {
		//Mocking the method
		when(utils.genString(any(String.class))).thenReturn("FOOL");
		String result=bizService.magic("Hmm");
		assertEquals("FOOL THANK YOU", result);
	}
	
	
	@Test
	public void testMagic() {
		//Mocking the method
		when(utils.genString("Nagendra")).thenReturn("AHA");
		String result=bizService.magic("Nagendra");
		assertEquals("AHA THANK YOU", result);
	}
}
