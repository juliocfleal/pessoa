package com.juliocfleal.pessoacidade.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.juliocfleal.pessoacidade.entities.Address;
import com.juliocfleal.pessoacidade.entities.Person;
import com.juliocfleal.pessoacidade.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@DataJpaTest
public class AddressRepositoryTests {

	
	@Autowired
	private AddressRepository repository;
	
	private Long validId;
	private Long invalidId;
	private Long totalAddress = 4L;
	private Address address = new Address(null, "Rua Pernambuco", "37701021", "543", "Pocos de Caldas", false);
	
	
	@BeforeEach
	void setUp() throws Exception{
		validId = 1L;
		invalidId = 9999L;
		
	}
	
	@Test
	public void saveShouldPersistNewAddress() {
		address = repository.save(address);
		Assertions.assertNotNull(address.getId());
		Assertions.assertEquals((totalAddress + 1L), address.getId());
	}
	
	
	
	@Test
	public void findByIdShouldReturnAddressWhenIdIsValid() {
		Optional<Address> addressTest = repository.findById(validId);
		Assertions.assertNotNull(addressTest.get().getId());
		Assertions.assertEquals(addressTest.get().getId(), validId);
		Assertions.assertEquals(addressTest.get().getCEP(), "37701110");
		Assertions.assertEquals(addressTest.get().getCity(), "Pocos de Caldas");
		Assertions.assertEquals(addressTest.get().getStreetAddress(), "Rua Monteiro Lobato");
		Assertions.assertEquals(addressTest.get().getPerson().getId(), 1);
	}

	@Test
	public void findAllReturnListWithAddress() {
		List<Address> addressTest = repository.findAll();
		Assertions.assertNotNull(addressTest);
		Assertions.assertEquals(addressTest.size(), totalAddress);
	}
	
	
	
}
