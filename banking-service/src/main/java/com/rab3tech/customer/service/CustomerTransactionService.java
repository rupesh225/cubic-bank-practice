package com.rab3tech.customer.service;

import java.util.List;

import com.rab3tech.vo.CustomerTransactionVO;

public interface CustomerTransactionService {

	List<CustomerTransactionVO> findCustomerTransaction(String username);

}
