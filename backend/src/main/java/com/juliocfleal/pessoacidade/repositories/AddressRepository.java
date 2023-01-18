package com.juliocfleal.pessoacidade.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.juliocfleal.pessoacidade.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

	@Query(nativeQuery = true,value =  "SELECT * FROM tb_address WHERE is_main = true AND person_id = :userId")
	List<Address> findAllPersonsMainAddress(Long userId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM tb_address WHERE person_id = :userId")
	List<Address> findAllPersonsAddresses(Long userId);

}
