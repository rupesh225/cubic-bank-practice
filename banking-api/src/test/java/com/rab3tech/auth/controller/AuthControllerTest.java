package com.rab3tech.auth.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rab3tech.customer.service.LoginService;
import com.rab3tech.vo.LoginVO;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private LoginService loginService;
	
	@InjectMocks
	private AuthController authController;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .build();
	}
	
	
	@Test
	public void testAuthUserWhenNotExist() throws Exception{
		//Stubbing the method
		when(loginService.findUserByUsername("testing100")).thenReturn(Optional.empty());
		mockMvc.perform(MockMvcRequestBuilders.get("/v3/user/auth/testing100").
			 accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
		verify(loginService, times(1)).findUserByUsername("testing100");
	    verifyNoMoreInteractions(loginService);
		
	}
	
	@Test
	public void testAuthUserWhenExist() throws Exception{
		
		LoginVO loginVO=new LoginVO();
		loginVO.setEmail("maoao@gmail.com");
		loginVO.setName("Mocha");
		loginVO.setUsername("testing100");
		Optional<LoginVO> ologinvo=Optional.of(loginVO);
		//Stubbing the method
		when(loginService.findUserByUsername("testing100")).thenReturn(ologinvo);
		//Now call the endpoint
		//{"email":"maoao@gmail.com","name":"Mocha","username":"testing100"}
		mockMvc.perform(MockMvcRequestBuilders.get("/v3/user/auth/testing100").
			 accept(MediaType.APPLICATION_JSON)).
		     andExpect(jsonPath("$.email").exists())
			.andExpect(jsonPath("$.name").exists())
			.andExpect(jsonPath("$.username").exists())
			.andExpect(jsonPath("$.email").value("maoao@gmail.com"))
			.andExpect(jsonPath("$.name").value("Mocha"))
			.andExpect(jsonPath("$.username").value("testing100")).andExpect(status().isOk())
			.andDo(print());
		
		verify(loginService, times(1)).findUserByUsername("testing100");
	    verifyNoMoreInteractions(loginService);
		
	}

}
