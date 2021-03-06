package com.rab3tech.customer.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rab3tech.dao.entity.CustomerTransaction;

/**
 * 
 * @author nagendra
 * comment
 * 
 * Spring Data JPA repository
 *
 */
public interface CustomerTransactionRepository extends JpaRepository<CustomerTransaction, Integer> {
	//Writing custom query using spring data jpa	
	public List<CustomerTransaction> findByFromAccount(String fromAccount);
	
	@Modifying
    @Query("DELETE CustomerTransaction ct WHERE ct.fromAccount = ?1")
	public void deleteByFromAccount(String  fromAccount);
}

