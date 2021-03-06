package com.rab3tech.admin.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rab3tech.admin.service.impl.RoleService;


@Controller
@RequestMapping("/admin")
//http:localhost:4055
//httt://www.kuebikobank.com/admin/security/questions
@Scope("singleton")
public class AdminUIController {
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/roles")
	public String showRoles(Model model) {
		model.addAttribute("roles",roleService.findRoles());
		return "admin/roles";
	}
	
	@GetMapping("/deleteRole")
	public String deleteRole(int rid, Model model) {
		boolean deleted=roleService.delete(rid);
		if(!deleted){
			model.addAttribute("message","This role is associated with customer");
		}else{
			model.addAttribute("message","This role is deleted success");
		}
		model.addAttribute("roles",roleService.findRoles());
		return "admin/roles";
	}

}
