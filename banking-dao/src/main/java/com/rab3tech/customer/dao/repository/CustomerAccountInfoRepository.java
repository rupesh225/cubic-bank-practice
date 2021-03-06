package com.rab3tech.customer.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rab3tech.dao.entity.CustomerAccountInfo;

public interface CustomerAccountInfoRepository extends JpaRepository<CustomerAccountInfo, Long> {
	
	public Optional<CustomerAccountInfo>  findByAccountNumber(String accountNumber);
	
	@Query("select t from CustomerAccountInfo t where t.customerId.loginid=?1")
	public Optional<CustomerAccountInfo>  findByLoginId(String loginid);
	
	@Modifying
    @Query("DELETE CustomerAccountInfo cai WHERE cai.customerId.loginid = ?1")
	public void deleteByUserid(String  userid);

}
