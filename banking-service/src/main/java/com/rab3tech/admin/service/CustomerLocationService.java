package com.rab3tech.admin.service;

import java.util.List;
import java.util.Optional;

import com.rab3tech.vo.LocationVO;

public interface CustomerLocationService {
	public List<LocationVO> findLocation();

	String save(LocationVO locationVO);

	Optional<LocationVO> findById(int lid);

	void update(LocationVO locationVO);
}
