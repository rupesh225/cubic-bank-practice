package com.rab3tech.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.customer.dao.repository.CustomerTransactionRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{
	
	
	@Autowired
	private CustomerTransactionRepository customerTransactionRepository;
	
	

}
