package com.rab3tech.admin.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rab3tech.customer.service.LoginService;
import com.rab3tech.test.TestUtil;
import com.rab3tech.vo.ApplicationResponseVO;
import com.rab3tech.vo.RoleVO;
import com.rab3tech.vo.RolesUpdateRequest;

@RunWith(MockitoJUnitRunner.class)
public class RoleControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private LoginService loginService;
	
	@InjectMocks
	private RoleController roleController;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(roleController)
                .build();
	}
	
	
	@Test
	public void testGetRoles() throws Exception {
		RoleVO roleVO=new RoleVO();
		roleVO.setId(100);	
		roleVO.setName("ADMIN_ROLE");
		roleVO.setDescription("This super power role!");
		
		RoleVO roleVO1=new RoleVO();
		roleVO1.setId(200);	
		roleVO1.setName("CUSTOMER_ROLE");
		roleVO1.setDescription("This customer power role!");
		List<RoleVO> roleVOs=new ArrayList<RoleVO>();
		roleVOs.add(roleVO);
		roleVOs.add(roleVO1);
		when(loginService.findRoles()).thenReturn(roleVOs);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/v3/roles")).andExpect(status().isOk());
		
		verify(loginService, times(1)).findRoles();
	    verifyNoMoreInteractions(loginService);
	}
	
	@Test
	public void testGetCustomerRole() throws Exception {
		RoleVO roleVO=new RoleVO();
		roleVO.setId(100);	
		roleVO.setName("ADMIN_ROLE");
		roleVO.setDescription("This super power role!");
		
		RoleVO roleVO1=new RoleVO();
		roleVO1.setId(200);	
		roleVO1.setName("CUSTOMER_ROLE");
		roleVO1.setDescription("This customer power role!");
		List<RoleVO> roleVOs=new ArrayList<RoleVO>();
		roleVOs.add(roleVO);
		roleVOs.add(roleVO1);
		
		List<RoleVO> croleVOs=new ArrayList<RoleVO>();
		croleVOs.add(roleVO1);
		when(loginService.findRoles()).thenReturn(roleVOs);
		when(loginService.findRolesByUserid("nagen@gmail.com")).thenReturn(croleVOs);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/v3/customer/roles").param("userid","nagen@gmail.com")).andExpect(status().isOk());
		
		verify(loginService, times(1)).findRoles();
		verify(loginService, times(1)).findRolesByUserid("nagen@gmail.com");
		
        verifyNoMoreInteractions(loginService);
	}
	
	
	/*@PutMapping("/customer/roles")
	public ApplicationResponseVO updateCustomerRoles(@RequestBody RolesUpdateRequest rolesUpdateRequest) {
		loginService.updateCustomerRoles(rolesUpdateRequest);
		ApplicationResponseVO applicationResponseVO=new ApplicationResponseVO();
		applicationResponseVO.setCode(201);
		applicationResponseVO.setStatus("success");
		applicationResponseVO.setMessage("Roles is updated successfully.");
		return applicationResponseVO;
	}*/
	
	@Test
	public void testUpdateCustomerRole() throws Exception {
		
		RolesUpdateRequest rolesUpdateRequest=new RolesUpdateRequest(); 
		rolesUpdateRequest.setCid(1);
		rolesUpdateRequest.setRolesid(Arrays.asList(100,200));
		
		mockMvc.perform(MockMvcRequestBuilders.put("/v3/customer/roles")
		 	        .contentType(MediaType.APPLICATION_JSON)
		 	        .content(TestUtil.convertObjectToJsonBytes(rolesUpdateRequest))
		 			.accept(MediaType.APPLICATION_JSON))
		 			.andExpect(jsonPath("$.code").exists())
		 			.andExpect(jsonPath("$.status").exists())
		 			.andExpect(jsonPath("$.code").value(201))
		 			.andExpect(jsonPath("$.status").value("success"))
		 			.andDo(print());
		
		verify(loginService, times(1)).updateCustomerRoles(any(RolesUpdateRequest.class));
        verifyNoMoreInteractions(loginService);
	}
}
