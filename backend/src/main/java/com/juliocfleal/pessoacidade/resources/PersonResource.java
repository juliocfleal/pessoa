package com.juliocfleal.pessoacidade.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.juliocfleal.pessoacidade.DTO.PersonDTO;
import com.juliocfleal.pessoacidade.DTO.PersonInsertDTO;
import com.juliocfleal.pessoacidade.services.PersonService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/persons")
public class PersonResource {
	
	@Autowired
	private PersonService service;
	
	@PostMapping
	public ResponseEntity<PersonDTO> createPerson(@Valid  @RequestBody PersonInsertDTO dto){
		PersonDTO personDTO = service.createPerson(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(personDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(personDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id, @Valid @RequestBody PersonDTO dto){
		PersonDTO personDTO = service.updatePerson(id, dto);
		return ResponseEntity.ok().body(personDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<PersonDTO> findById(@PathVariable Long id){
		PersonDTO personDTO = service.findById(id);
		return ResponseEntity.ok().body(personDTO);
	}
	
	@GetMapping
	public ResponseEntity<Page<PersonDTO>> findAllPageable(Pageable pageable){
		Page<PersonDTO> listDTO = service.findAllPaged(pageable); 
		return ResponseEntity.ok().body(listDTO);
	}
}
