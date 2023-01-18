package com.juliocfleal.pessoacidade.DTO;

import java.time.LocalDate;
import java.util.Date;

import com.juliocfleal.pessoacidade.entities.Person;

public class PersonInsertDTO {
	
	private String name;
	private LocalDate birthDate;
	

	public PersonInsertDTO() {
		
	}


	public PersonInsertDTO(String name, LocalDate birthDate) {
		this.name = name;
		this.birthDate = birthDate;
	}

	public PersonInsertDTO(Person person) {

		this.name = person.getName();
		this.birthDate = person.getBirthDate();
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
