package com.juliocfleal.pessoacidade.DTO;

import java.time.LocalDate;
import java.util.Date;

import com.juliocfleal.pessoacidade.entities.Person;

import jakarta.validation.constraints.NotBlank;

public class PersonDTO {
	
	private Long id;
	
	@NotBlank(message = "Campo Obrigatório")
	private String name;
	
	private LocalDate birthDate;
	

	public PersonDTO() {
		
	}


	public PersonDTO(Long id, String name, LocalDate birthDate) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public PersonDTO(Person person) {
		this.id = person.getId();
		this.name = person.getName();
		this.birthDate = person.getBirthDate();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public LocalDate getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	
	
	
}
