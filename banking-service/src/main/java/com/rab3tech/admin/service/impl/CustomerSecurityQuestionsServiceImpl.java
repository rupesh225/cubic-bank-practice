package com.rab3tech.admin.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.admin.dao.repository.CustomerSecurityQuestionsRepository;
import com.rab3tech.admin.service.CustomerSecurityQuestionsService;
import com.rab3tech.dao.entity.SecurityQuestions;
import com.rab3tech.vo.SecurityQuestionsVO;

@Service
@Transactional
public class CustomerSecurityQuestionsServiceImpl  implements CustomerSecurityQuestionsService{
	
	@Autowired
	private CustomerSecurityQuestionsRepository customerSecurityQuestionsDao;
	
	@Override
	public void updateStatus(String status,int qid) {
		//entity is loaded inside session - persistence context
		Optional<SecurityQuestions> securityQuestions=customerSecurityQuestionsDao.findById(qid);
		if("yes".equalsIgnoreCase(status)) {
			status="no";
		}else {
			status="yes";
		}
		if(securityQuestions.isPresent()) {
			securityQuestions.get().setStatus(status);	
		}
	}
	
	@Override
	public List<SecurityQuestionsVO> findSecurityQuestions() {

		return customerSecurityQuestionsDao.findAll().stream().map(sq->{
			SecurityQuestionsVO questionsVO=new SecurityQuestionsVO();
			BeanUtils.copyProperties(sq, questionsVO);
			return questionsVO;
		}).collect(Collectors.toList());
		
		/*List<SecurityQuestionsVO>  questionsVOs=new ArrayList<>();
		for(SecurityQuestions entity:securityQuestions) {
			SecurityQuestionsVO questionsVO=new SecurityQuestionsVO();
			BeanUtils.copyProperties(entity, questionsVO);
			questionsVOs.add(questionsVO);
		}
		return questionsVOs;*/
	}

	@Override
	public void addSecurityQuestion(String question, String loginid) {
		SecurityQuestions securityQuestions=new SecurityQuestions (0,question,"yes",loginid,new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()));
		customerSecurityQuestionsDao.save(securityQuestions);
	}

}
