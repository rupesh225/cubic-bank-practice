package com.rab3tech.admin.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.rab3tech.admin.dao.repository.CustomerSecurityQuestionsRepository;
import com.rab3tech.dao.entity.SecurityQuestions;
import com.rab3tech.vo.SecurityQuestionsVO;

@RunWith(MockitoJUnitRunner.class)
public class CustomerSecurityQuestionsServiceImplTest {
	
	@Mock
	private CustomerSecurityQuestionsRepository customerSecurityQuestionsDao;
	
	@InjectMocks
	private CustomerSecurityQuestionsServiceImpl customerSecurityQuestionsService;
	
	@Before
	public void zee(){
		 MockitoAnnotations.initMocks(this); //Initializing mocking for each test cases
	}

	@Test
	public void testUpdateStatusWhenSecurityQuestionsExist() {
		
		SecurityQuestions sq=new SecurityQuestions();
		sq.setOwner("nagen@gmail.com");
		sq.setQid(100);
		sq.setQuestions("What's your pet name?");
		sq.setStatus("yes");
		Optional<SecurityQuestions> osecurityQues=Optional.of(sq);
		//stubbing
		when(customerSecurityQuestionsDao.findById(100)).thenReturn(osecurityQues);
		
		customerSecurityQuestionsService.updateStatus("yes", 100);
		
		verify(customerSecurityQuestionsDao, times(1)).findById(100);
	    verifyNoMoreInteractions(customerSecurityQuestionsDao);		
	}
	
	@Test
	public void testUpdateStatusWhenSecurityQuestionsExistStatusNo() {
		
		SecurityQuestions sq=new SecurityQuestions();
		sq.setOwner("nagen@gmail.com");
		sq.setQid(100);
		sq.setQuestions("What's your pet name?");
		sq.setStatus("yes");
		Optional<SecurityQuestions> osecurityQues=Optional.of(sq);
		//stubbing
		when(customerSecurityQuestionsDao.findById(100)).thenReturn(osecurityQues);
		
		customerSecurityQuestionsService.updateStatus("no", 100);
		
		verify(customerSecurityQuestionsDao, times(1)).findById(100);
	    verifyNoMoreInteractions(customerSecurityQuestionsDao);		
	}
	
	/*@Test(expected=NoSuchElementException.class)
	public void testUpdateStatusWhenSecurityQuestionsNotExist() {
		Optional<SecurityQuestions> osecurityQues=Optional.empty();
		when(customerSecurityQuestionsDao.findById(100)).thenReturn(osecurityQues);
		customerSecurityQuestionsService.updateStatus("yes", 100);
	}*/
	
	/*List<SecurityQuestionsVO>  questionsVOs=new ArrayList<>();
	for(SecurityQuestions entity:securityQuestions) {
		SecurityQuestionsVO questionsVO=new SecurityQuestionsVO();
		BeanUtils.copyProperties(entity, questionsVO);
		questionsVOs.add(questionsVO);
	}
	return questionsVOs;*/


	@Test
	public void testFindSecurityQuestionsWhenExist() {
		List<SecurityQuestions> securityQuestions=new ArrayList<SecurityQuestions>();
		SecurityQuestions sq1=new SecurityQuestions();
		sq1.setOwner("nagen@gmail.com");
		sq1.setQid(100);
		sq1.setQuestions("What's your pet name?");
		sq1.setStatus("yes");
		
		SecurityQuestions sq2=new SecurityQuestions();
		sq2.setOwner("nagen@gmail.com");
		sq2.setQid(200);
		sq2.setQuestions("What's your mother name?");
		sq2.setStatus("yes");
		securityQuestions.add(sq1);
		securityQuestions.add(sq2);
		
		when(customerSecurityQuestionsDao.findAll()).thenReturn(securityQuestions);
		List<SecurityQuestionsVO> questionsVOs=customerSecurityQuestionsService.findSecurityQuestions();
		assertNotNull(questionsVOs);
		assertEquals(2, questionsVOs.size());
		assertEquals(questionsVOs.get(0).getQid(), 100);
		assertEquals(questionsVOs.get(1).getQuestions(), "What's your mother name?");

		verify(customerSecurityQuestionsDao, times(1)).findAll();
		verifyNoMoreInteractions(customerSecurityQuestionsDao);
		
	}
	
	@Test
	public void testFindSecurityQuestionsWhenNotExist() {
		
		List<SecurityQuestions> securityQuestions=new ArrayList<SecurityQuestions>();
		when(customerSecurityQuestionsDao.findAll()).thenReturn(securityQuestions);

		List<SecurityQuestionsVO> questionsVOs=customerSecurityQuestionsService.findSecurityQuestions();
		assertNotNull(questionsVOs);
		assertEquals(0, questionsVOs.size());

		verify(customerSecurityQuestionsDao, times(1)).findAll();
		verifyNoMoreInteractions(customerSecurityQuestionsDao);
	}
	

	@Test
	public void testAddSecurityQuestion() {
		customerSecurityQuestionsService.addSecurityQuestion("Hey who is good friend?", "nagen@gmail.com");
		verify(customerSecurityQuestionsDao, times(1)).save(any(SecurityQuestions.class));
	}

}
