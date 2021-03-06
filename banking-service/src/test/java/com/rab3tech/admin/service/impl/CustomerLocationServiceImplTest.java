package com.rab3tech.admin.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rab3tech.customer.dao.repository.CustomerLocationRepository;
import com.rab3tech.dao.entity.Location;
import com.rab3tech.dao.entity.Login;
import com.rab3tech.vo.LocationVO;

public class CustomerLocationServiceImplTest {
	
	@Mock
	private CustomerLocationRepository customerLocationRepository;
	
	@InjectMocks
	private CustomerLocationServiceImpl customerLocationServiceImpl;
	
	@Before
	public void init() {
		 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFindLocationWhenItExist() {
		List<Location> location = new ArrayList<Location>();
		Location location1 = new Location();
		location1.setId(100);
		location1.setLcode("L010");
		location1.setLocation("India");
		
		Login login=new Login();
		login.setEmail("9292@gmail.com");
		login.setLoginid("ame88@gmail.com");
		login.setName("Nagendra");
		login.setNoOfAttempt(12);
		login.setPassword("324");
		location1.setLogin(login);
		
		location.add(location1);
		
		when(customerLocationRepository.findAll()).thenReturn(location);
		List<LocationVO> locationVOs = customerLocationServiceImpl.findLocation();
		assertNotNull(locationVOs);
		assertEquals(1, locationVOs.size());
		assertEquals("L010",locationVOs.get(0).getLcode());

		verify(customerLocationRepository, times(1)).findAll();
		verifyNoMoreInteractions(customerLocationRepository);
	}

}
