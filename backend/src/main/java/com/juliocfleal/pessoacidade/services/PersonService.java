package com.juliocfleal.pessoacidade.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.juliocfleal.pessoacidade.DTO.PersonDTO;
import com.juliocfleal.pessoacidade.DTO.PersonInsertDTO;
import com.juliocfleal.pessoacidade.entities.Person;
import com.juliocfleal.pessoacidade.repositories.PersonRepository;
import com.juliocfleal.pessoacidade.services.exceptions.ResourceNotFoundException;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository repository;
	

	public PersonDTO createPerson(PersonInsertDTO dto) {
		Person person = new Person();
		person.setName(dto.getName());
		person.setBirthDate(dto.getBirthDate());
		person = repository.save(person);
		return new PersonDTO(person);
	}
	
	public PersonDTO updatePerson(Long id, PersonDTO dto) {
		Person person = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Esta pessoa não esta cadastrada no nosso banco de dados"));
		person.setName(dto.getName());
		person.setBirthDate(dto.getBirthDate());
		person = repository.save(person);
		return new PersonDTO(person);
		
	}
	
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public PersonDTO findById(Long id) {
		Person person = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Esta pessoa não esta cadastrada no nosso banco de dados"));
		return new PersonDTO(person);
	}
	
	
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public Page<PersonDTO> findAllPaged(Pageable pageable) {
		Page<Person> persons = repository.findAll(pageable);
		Page<PersonDTO> listDTO = persons.map(x -> new PersonDTO(x));
		return listDTO;
	}
	
	
	

}
