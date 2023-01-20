package com.juliocfleal.pessoacidade.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.juliocfleal.pessoacidade.entities.Person;
import com.juliocfleal.pessoacidade.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@DataJpaTest
public class PersonRepositoryTests {

	
	@Autowired
	private PersonRepository repository;
	
	private Long validId;
	private Long invalidId;
	private String validName;
	private Long totalPersons = 10L;
	private Pageable pageable = PageRequest.of(1, 10);
	private Person person = new Person(null, "Julio", LocalDate.of(1989, 02, 20));

	@BeforeEach
	void setUp() throws Exception{
		validId = 1L;
		invalidId = 9999L;
		validName = "Leal";
		
	}
	
	@Test
	public void saveShouldPersistNewPerson() {
		person = repository.save(person);
		Assertions.assertNotNull(person.getId());
		Assertions.assertEquals((totalPersons + 1L), person.getId());
	}
	
	@Test
	public void findByIdShouldReturnPersonWhenIdIsValid() {
		Optional<Person> personTest = repository.findById(validId);
		Assertions.assertNotNull(personTest.get().getId());
		Assertions.assertEquals(personTest.get().getId(), validId);
		Assertions.assertNotNull(personTest.get().getBirthDate());
		Assertions.assertEquals(personTest.get().getName(), validName);	
		Assertions.assertNotNull(personTest.get().getAdrresses());	
	}

	@Test
	public void findAllReturnPageWithPersons() {
		Page<Person> personTest = repository.findAll(pageable);
		Assertions.assertNotNull(personTest);
		Assertions.assertEquals(personTest.getSize(), 10);
	}
	
	
	
}
