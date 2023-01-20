package com.juliocfleal.pessoacidade.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.data.domain.Pageable;

import com.juliocfleal.pessoacidade.DTO.PersonDTO;
import com.juliocfleal.pessoacidade.DTO.PersonInsertDTO;
import com.juliocfleal.pessoacidade.entities.Person;
import com.juliocfleal.pessoacidade.repositories.PersonRepository;
import com.juliocfleal.pessoacidade.services.exceptions.ResourceNotFoundException;

@WebMvcTest(SpringExtension.class)
public class PersonServiceTests {
	
	@InjectMocks
	private PersonService service;
	
	@Mock
	private PersonRepository repository;
	
	private Long validId;
	private Long invalidId;
	private PersonInsertDTO insertDTO = new PersonInsertDTO("Julio", LocalDate.of(1989, 02, 20)); 
	private Person person = new Person(null, "Julio", LocalDate.of(1989, 02, 20));
	private PersonDTO dto = new PersonDTO(person);
	private PersonDTO updateDTO = new PersonDTO(1L,"Julio", LocalDate.of(1989, 02, 20));
	private Pageable pageable = PageRequest.of(1, 20);
	private Page<Person> pagePersonTest = new PageImpl<>(List.of(person, person), pageable, 0);
	@BeforeEach
	void setUp() throws Exception{
		validId = 1L;
		invalidId = 9999L;
		Mockito.when(repository.save(person)).thenReturn(person);
		Mockito.when(repository.findById(validId)).thenReturn(Optional.of(person));
		Mockito.when(repository.findById(invalidId)).thenThrow(ResourceNotFoundException.class);
		Mockito.when(repository.findAll(pageable)).thenReturn(pagePersonTest);
	}
	
	@Test
	public void insertNewPersonShouldReturnPerson() {
		PersonDTO personTest = service.createPerson(insertDTO);
		Assertions.assertNotNull(personTest);
		Mockito.verify(repository, Mockito.times(1)).save(person);
		
	}
	
	@Test
	public void updatePersonWithValidIdShouldReturnUpdatedPerson() {
		PersonDTO personTest = service.updatePerson(validId, updateDTO);
		Assertions.assertNotNull(personTest);
		Mockito.verify(repository, Mockito.times(1)).save(person);
	}

	
	@Test
	public void updatePersonWithInvalidIdShouldReturnException() {
		Assertions.assertThrows(com.juliocfleal.pessoacidade.services.exceptions.ResourceNotFoundException.class, ()->{
			service.updatePerson(invalidId, dto);
	});
	}
	
	@Test
	public void findByIdShouldReturnValidPersonWhenIdIsValid() {
		PersonDTO personTest = service.findById(validId);
		Assertions.assertNotNull(personTest);
		Mockito.verify(repository, Mockito.times(1)).findById(validId);
		
	}
	
	@Test
	public void findByIdShouldReturnExceptionWhenIdIsInvalid() {
		Assertions.assertThrows(com.juliocfleal.pessoacidade.services.exceptions.ResourceNotFoundException.class, ()->{
			service.findById(invalidId);
	});
	}
	
	@Test
	public void findAllPagedShouldReturnPageOfPersons() {
		Page<PersonDTO> listTest = service.findAllPaged(pageable);
		Assertions.assertNotNull(listTest);
		Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
	}
	
}
