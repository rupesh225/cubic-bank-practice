package com.rab3tech.auth.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rab3tech.customer.service.LoginService;
import com.rab3tech.vo.LoginVO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v3")
public class AuthController {

	@Autowired
	private LoginService loginService;
	
	@PostMapping("/user/auth")
	public LoginVO authUser(@RequestBody LoginVO loginVO){
		Optional<LoginVO> optional=loginService.findUserByUsername(loginVO.getUsername());
		if(optional.isPresent()){
		   return optional.get(); 	
		}
		return null;
	}
	
	//@PathVariable means u are sending input
	//through URI
	@GetMapping("/user/auth/{username}")
	public LoginVO authUser(@PathVariable String username){
		Optional<LoginVO> optional=loginService.findUserByUsername(username);
		if(optional.isPresent()){
		   return optional.get(); 	
		}
		return null;
	} 
	
}
