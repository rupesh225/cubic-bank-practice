package com.rab3tech.customer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.CustomerTransactionRepository;
import com.rab3tech.customer.service.CustomerTransactionService;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.CustomerTransaction;
import com.rab3tech.vo.CustomerTransactionVO;


@Service
public class CustomerTransactionServiceImpl implements CustomerTransactionService {

	@Autowired
	private CustomerTransactionRepository customerTransactionRepository;
	
	@Autowired
	private CustomerAccountInfoRepository customerAccountInfoRepository;
	
	
	@Override
	public List<CustomerTransactionVO> findCustomerTransaction(String username){
		
		//now fetch account for username
		Optional<CustomerAccountInfo> fromAccountOptional=customerAccountInfoRepository.findByLoginId(username);
		CustomerAccountInfo accountInfo=null;
		if(fromAccountOptional.isPresent()){
			 accountInfo=fromAccountOptional.get();
		}
		
		List<CustomerTransaction> customerTransactions=customerTransactionRepository.findByFromAccount(accountInfo.getAccountNumber());
		List<CustomerTransactionVO> customerTransactionVOs=new ArrayList<CustomerTransactionVO>();
		for(CustomerTransaction customerTransaction:customerTransactions){
			CustomerTransactionVO customerTransactionVO=new CustomerTransactionVO();
			BeanUtils.copyProperties(customerTransaction, customerTransactionVO);
			customerTransactionVOs.add(customerTransactionVO);
		}
		return customerTransactionVOs;
	}
}
