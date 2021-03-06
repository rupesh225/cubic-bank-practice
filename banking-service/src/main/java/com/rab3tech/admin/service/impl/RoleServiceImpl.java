package com.rab3tech.admin.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.customer.dao.repository.RoleRepository;
import com.rab3tech.dao.entity.Login;
import com.rab3tech.dao.entity.Role;
import com.rab3tech.vo.RoleVO;


@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public List<RoleVO> findRoles(){
		List<Role> roles=roleRepository.findAll();
		return roles.stream().map(st->{
			RoleVO roleVO=new RoleVO();
			BeanUtils.copyProperties(st, roleVO);
			roleVO.setId(st.getRid());
			return roleVO;
		}).collect(Collectors.toList());
	}
	
	@Override
	public RoleVO findById(int rid){
		Role  role=roleRepository.findById(rid).orElse(new Role());
		RoleVO roleVO=new RoleVO();
		if(role.getRid()!=0){
			BeanUtils.copyProperties(role, roleVO);
			roleVO.setId(role.getRid());
		}
		return roleVO;
	}
	
	@Override
	@Transactional
	public void saveOrUpdate(RoleVO roleVO){
		Role  role=roleRepository.findById(roleVO.getId()).orElse(new Role());
		role.setDescription(roleVO.getDescription());
	}
	
	@Override
	public boolean delete(int rid) {
		boolean deleted=false;
		Role role = roleRepository.findById(rid).get();
		Set<Login> logins = role.getLogins();
		if(logins.size()==0){
			roleRepository.delete(role);	
			deleted=true;
		}
		return deleted;
	}


}
