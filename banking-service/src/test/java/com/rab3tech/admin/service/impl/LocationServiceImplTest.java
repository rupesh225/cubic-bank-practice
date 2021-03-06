package com.rab3tech.admin.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import com.rab3tech.customer.dao.repository.CustomerLocationRepository;
import com.rab3tech.dao.entity.Location;
import com.rab3tech.vo.LocationVO;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceImplTest {
	
	@Mock
	private CustomerLocationRepository customerLocationRepository;
	
	//creating instance of CustomerLocationServiceImpl
	@InjectMocks
	private CustomerLocationServiceImpl customerLocationService;
	
	@Before
	public void anything(){
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void testSave() {
		LocationVO location=new LocationVO();
		location.setId(100);
		location.setLcode("L019");
		location.setName("Fremont");
		//This important
		when(customerLocationRepository.save(any(Location.class))).thenReturn(any(Location.class));
		String status=customerLocationService.save(location);
		assertEquals("success", status);
		verify(customerLocationRepository, times(1)).save(any(Location.class));
		verifyNoMoreInteractions(customerLocationRepository);
	}
	
	
	@Test
	public void testFindByIdWhenExist() {
		Location location=new Location();
		location.setId(100);
		location.setLcode("L019");
		location.setLocation("Fremont");
		
		//Stubing or mocking the behavior for repository
		when(customerLocationRepository.findById(100)).thenReturn(Optional.of(location));
	
		Optional<LocationVO> optional=customerLocationService.findById(100);
		LocationVO locationVO=optional.get();
		assertEquals("L019", locationVO.getLcode());
		assertEquals("Fremont", locationVO.getName());
		verify(customerLocationRepository, times(1)).findById(100);
		verifyNoMoreInteractions(customerLocationRepository);
	}
	
	@Test
	public void testFindByIdWhenNotExist() {
		//Stubing or mocking the behavior for repository
		when(customerLocationRepository.findById(100)).thenReturn(Optional.empty());
		Optional<LocationVO> optional=customerLocationService.findById(100);
		assertEquals(false,optional.isPresent());
		
		verify(customerLocationRepository, times(1)).findById(100);
		verifyNoMoreInteractions(customerLocationRepository);
	}
	

}
