package com.rab3tech.customer.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rab3tech.customer.service.LoginService;
import com.rab3tech.customer.service.impl.SecurityQuestionService;
import com.rab3tech.vo.ResetPasswordVO;


@Controller
public class PasswordController {

	@Autowired
	   private LoginService loginService;	
	@Autowired
	private SecurityQuestionService securityQuestionService;
	
	@GetMapping("/customer/forget/password")
	public String goToForgetPassPage() {
		return "/customer/forgetPass";
	}
	
	@PostMapping("/customer/forget/password")
	public String goToSecuirityQnPage(@RequestParam("email") String email, Model model ) {
		List<String> qns = securityQuestionService.findQuestionByEmail(email);
		model.addAttribute("questions", qns);
		
		//return to a page with the questions and take answers as input
		return "/customer/validateSecurityQuestion";
	}
	
	@PostMapping("customer/validate/question")
	public String validateAnswers(@RequestParam("email") String email, @RequestParam("securityAns1") String securityAns1, @RequestParam("securityAns2") String securityAns2, Model model ){
		
		List<String> answers = securityQuestionService.findAnswerByEmail(email);
		
		if(securityAns1.equals(answers.get(0)) && securityAns2.equals(answers.get(1))) {
//			model.addAttribute("email", email);
			
			return "/customer/resetPassword";
		}
		else {
			List<String> qns = securityQuestionService.findQuestionByEmail(email);
			model.addAttribute("questions", qns);
			model.addAttribute("error", "Answers don't match! Please re-validate");
			return "/customer/validateSecurityQuestion";
		}
		
		
	}
	
	
	@PostMapping("/customer/resetPassword")
	public String resetPassword(@RequestParam("email") String email, @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword, Model model){
		
		if(!newPassword.equals(confirmPassword)) {
			model.addAttribute("error", "New and Confirm Password don't match! Please check");
			return "/customer/resetPassword";
		}
		
		ResetPasswordVO resetPasswordVO = new ResetPasswordVO(email, newPassword, confirmPassword);
		
	 loginService.resetPassword(resetPasswordVO);
		
		return "/customer/login";
	}
	
}
