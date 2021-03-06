package com.rab3tech.admin.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rab3tech.dao.entity.Location;
import com.rab3tech.dao.entity.Login;


public interface LocationRepository  extends JpaRepository<Location, Integer> {
	
	public Optional<Location> findById(int id);
	
	public Optional<Location> findByLcode(String lcode);

}
