package com.rab3tech.admin.service.impl;

import java.util.List;

import com.rab3tech.vo.RoleVO;

public interface RoleService {

	List<RoleVO> findRoles();

	RoleVO findById(int rid);

	void saveOrUpdate(RoleVO roleVO);

	boolean delete(int rid);

}
