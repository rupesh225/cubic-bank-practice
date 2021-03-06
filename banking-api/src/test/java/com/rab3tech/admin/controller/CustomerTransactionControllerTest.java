package com.rab3tech.admin.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.vo.CustomerVO;

@RunWith(MockitoJUnitRunner.class)
public class CustomerTransactionControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private CustomerService customerService;
	
	@InjectMocks
	private CustomerTransactionController customerTransactionController;
	
	@Before
	public void zigzag(){
		MockitoAnnotations.initMocks(this);
		//Here using mock mvc 
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerTransactionController)
                .build();
	}
	
	@Test
	public void testFindCustomersWhenExist() throws Exception {
		CustomerVO customerVO = new CustomerVO();
		customerVO.setEmail("nagen@gmail.com");
		customerVO.setName("nagen");
		
		when(customerService.findCustomers()).thenReturn(Arrays.asList(customerVO));
		mockMvc.perform(get("/v4/customers").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].email", is("nagen@gmail.com")))
				.andExpect(jsonPath("$[0].name", is("nagen"))).andDo(print());
		
		verify(customerService, times(1)).findCustomers();
		verifyNoMoreInteractions(customerService);
	}
	
	@Test
    public void testFindCustomersWhenNotExist() throws Exception{
		when(customerService.findCustomers()).thenReturn(new ArrayList<CustomerVO>());
		mockMvc.perform(get("/v4/customers").
				accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)))
				.andDo(print());
		verify(customerService, times(1)).findCustomers();
		verifyNoMoreInteractions(customerService);
	}

}
