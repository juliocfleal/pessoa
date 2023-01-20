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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.juliocfleal.pessoacidade.DTO.AddressDTO;
import com.juliocfleal.pessoacidade.DTO.AddressInsertDTO;
import com.juliocfleal.pessoacidade.entities.Address;
import com.juliocfleal.pessoacidade.entities.Person;
import com.juliocfleal.pessoacidade.repositories.AddressRepository;
import com.juliocfleal.pessoacidade.repositories.PersonRepository;
import com.juliocfleal.pessoacidade.services.exceptions.ResourceNotFoundException;

@WebMvcTest(SpringExtension.class)
public class AddressServiceTests {
	
	@InjectMocks
	private AddressService service;
	
	@Mock
	private AddressRepository repository;
	
	@Mock
	private PersonRepository personRepository;
	
	private Long validId;
	private Long addressMainValidId;
	private Long invalidId;
	private Address address = new Address(null, "Rua Pernambuco", "37701021", "543", "Pocos de Caldas", false);
	private Address addressWhithPerson = new Address(null, "Rua Pernambuco", "37701021", "543", "Pocos de Caldas", false);
	private AddressInsertDTO inserDTO= new AddressInsertDTO("Rua Pernambuco", "37701021", "543", "Pocos de Caldas", false, 1L);
	private AddressInsertDTO inserInvalidDTO= new AddressInsertDTO("Rua Pernambuco", "37701021", "543", "Pocos de Caldas", false, 9999L);
	private Person person = new Person(null, "Julio", LocalDate.of(1989, 02, 20));
	private Person personWithId = new Person(222L, "Julio", LocalDate.of(1989, 02, 20));
	private List<Address> listAddress = new ArrayList<>();
	
	
	
	@BeforeEach
	void setUp() throws Exception{
		addressWhithPerson.setPerson(personWithId);
		validId = 1L;
		addressMainValidId = 222L;
		invalidId = 9999L;
		Mockito.when(repository.save(address)).thenReturn(address);
		Mockito.when(personRepository.findById(validId)).thenReturn(Optional.of(person));
		Mockito.when(personRepository.findById(invalidId)).thenThrow(ResourceNotFoundException.class);
		Mockito.when(repository.findAllPersonsAddresses(validId)).thenReturn(listAddress);
		Mockito.when(repository.findById(addressMainValidId)).thenReturn(Optional.of(addressWhithPerson));
		Mockito.when(repository.findById(invalidId)).thenThrow(ResourceNotFoundException.class);


	}

	
	@Test
	public void createAddressShouldReturnNewAddress() {
		AddressDTO addressDTOTest = service.createAddress(inserDTO);
		Assertions.assertNotNull(addressDTOTest);
		Mockito.verify(repository, Mockito.times(1)).save(address);	
		Mockito.verify(personRepository, Mockito.times(1)).findById(validId);	
	}
	
	@Test
	public void createdAddressShouldReturnExceptionWhenIdIsInvalid() {
		Assertions.assertThrows(com.juliocfleal.pessoacidade.services.exceptions.ResourceNotFoundException.class, ()->{
			service.createAddress(inserInvalidDTO);
	});
	}
	
	@Test
	public void getAllPersonsAddressesShouldReturnListOfAddress() {
		List<AddressDTO> listAddressDTOTest = service.getAllPersonsAddresses(validId);
		Assertions.assertNotNull(listAddressDTOTest);
		Mockito.verify(repository, Mockito.times(1)).findAllPersonsAddresses(validId);
	}
	
	@Test
	public void defineMainAddresShouldChangeMainAddressWhenIdIsValid() {
		AddressDTO addressDTOTest = service.defineMainAddress(addressMainValidId);
		Assertions.assertNotNull(addressDTOTest);
		Mockito.verify(repository, Mockito.times(1)).findById(addressMainValidId);
		Mockito.verify(repository, Mockito.times(1)).save(addressWhithPerson);
	}

	@Test
	public void defineMainAddresShouldThrowExceptionWhenIdIsInvalid() {
		Assertions.assertThrows(com.juliocfleal.pessoacidade.services.exceptions.ResourceNotFoundException.class, ()->{
			service.defineMainAddress(invalidId);
	});
}
	
	
}
