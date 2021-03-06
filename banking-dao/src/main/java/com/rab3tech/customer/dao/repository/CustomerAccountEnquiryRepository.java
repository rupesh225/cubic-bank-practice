package com.rab3tech.customer.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rab3tech.dao.entity.CustomerSaving;

/**
 * 
 * @author nagendra
 * comment
 * 
 * Spring Data JPA repository
 *
 */
public interface CustomerAccountEnquiryRepository extends JpaRepository<CustomerSaving, Integer> {
	
	Optional<CustomerSaving> findByEmail(String email);
	Optional<CustomerSaving> findByUcrid(String ucrid);
	
	@Query("SELECT tt FROM CustomerSaving tt where tt.status.name = :name") 
	List<CustomerSaving> findPendingEnquiries(@Param("name") String name);
	

	Optional<CustomerSaving> findByAppref(String appref);

	Optional<CustomerSaving> findByMobile(String mobile);
	
	@Query("SELECT t FROM CustomerSaving t where t.email = ?1 OR t.appref = ?1")
    public Optional<CustomerSaving> findAppStatus(String searchText);
	
	
	@Modifying
    @Query("DELETE CustomerSaving cs WHERE cs.email = ?1")
	public void deleteByUserid(String  userid);
	
}

