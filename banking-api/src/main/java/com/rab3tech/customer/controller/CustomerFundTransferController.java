package com.rab3tech.customer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.utils.Utils;
import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.FromToAccountsVO;
import com.rab3tech.vo.PayeeInfoVO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v3")
public class CustomerFundTransferController {
	
	@Autowired
	private CustomerService customerService;
	
	//in REST API ,We cannot use session , since it is always stateless
	@GetMapping("/customer/from-to-accounts")
	public FromToAccountsVO getCustomerFromToAccounts(@RequestParam("loginid") String loginid) {
		List<PayeeInfoVO> payeeInfoVOs=customerService.registeredPayeeList(loginid);
		List<String> toAccounts=new ArrayList<String>();
		for(PayeeInfoVO payeeInfoVO:payeeInfoVOs){
			toAccounts.add(payeeInfoVO.getPayeeAccountNo()+" - "+payeeInfoVO.getPayeeName());
		}
		
		CustomerAccountInfoVO accountInfoVO=customerService.findCustomerAccountInfo(loginid);
		FromToAccountsVO fromToAccountsVO=new FromToAccountsVO();
		fromToAccountsVO.setToAccounts(toAccounts);
		fromToAccountsVO.setFromAccount(accountInfoVO.getAccountNumber() +"-"+ accountInfoVO.getAcccountType()+ "-"+ accountInfoVO.getName());
		fromToAccountsVO.setCurrentBalance(String.valueOf(accountInfoVO.getAvBalance()));
		
		return fromToAccountsVO;
	}
	
	@GetMapping("/transaction/otp")
	public String transactionOtp(@RequestParam("username") String username) {
		 System.out.println("String.valueOf(Utils.generateURN() = "+Utils.generateURN());
         return "generated"; 	
	}	
	
}
