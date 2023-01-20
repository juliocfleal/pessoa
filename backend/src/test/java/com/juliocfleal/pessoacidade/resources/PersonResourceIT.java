package com.juliocfleal.pessoacidade.resources;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juliocfleal.pessoacidade.DTO.AddressInsertDTO;
import com.juliocfleal.pessoacidade.DTO.PersonInsertDTO;
import com.juliocfleal.pessoacidade.entities.Person;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PersonResourceIT {

	
	private Long validId;
	private Long invalidId;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception{
		validId = 1L;
		invalidId = 9999L;
	}
	
	@Test
	public void createPersonShouldCreateNewPerson() throws Exception {
		PersonInsertDTO personTest = new PersonInsertDTO("Apolo", LocalDate.of(1989, 02, 20));
		String jsonBody = objectMapper.writeValueAsString(personTest);
		ResultActions result = mockMvc.perform(post("/persons")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.name").value("Apolo"));
		result.andExpect(jsonPath("$.birthDate").exists());
	}
	
	@Test
	public void updatePersonShouldUpdatePersonWhenIdIsValid() throws Exception {
		PersonInsertDTO personTest = new PersonInsertDTO("Roberto", LocalDate.of(1989, 05, 02));
		String jsonBody = objectMapper.writeValueAsString(personTest);
		ResultActions result = mockMvc.perform(put("/persons/{id}", validId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.id").value(validId));
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.name").value("Roberto"));
		result.andExpect(jsonPath("$.birthDate").exists());
	}
	
	@Test
	public void findByIdShouldReturnPersonWhenIdIsValid() throws Exception {

		ResultActions result = mockMvc.perform(get("/persons/{id}", validId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.id").value(validId));
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.birthDate").exists());
	}
	
	@Test
	public void findAllPageableShouldReturnPageOfPersonWhenIdIsValid() throws Exception {

		ResultActions result = mockMvc.perform(get("/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].id").value(1L));
		result.andExpect(jsonPath("$.content[1].name").exists());
		result.andExpect(jsonPath("$.content[3].birthDate").exists());
	}
	
	public void createPersonShouldReturnUnprocessableEntityWhenIncompletData() throws Exception {
		PersonInsertDTO personTest = new PersonInsertDTO("Zeus", LocalDate.of(1989, 02, 20));
		String jsonBody = objectMapper.writeValueAsString(personTest);
		ResultActions result = mockMvc.perform(post("/persons")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
	}
	
	
	@Test
	public void updatePersonShouldReturnNotFoundWhenIdIsInvalid() throws Exception {
		PersonInsertDTO personTest = new PersonInsertDTO("Roberto", LocalDate.of(1989, 05, 02));
		String jsonBody = objectMapper.writeValueAsString(personTest);
		ResultActions result = mockMvc.perform(put("/persons/{id}", invalidId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
		result.andExpect(jsonPath("$.message").value("Esta pessoa não esta cadastrada no nosso banco de dados"));

	}
	
	@Test
	public void updatePersonShouldReturnUnprocessableEntityWhenIncompletData() throws Exception {
		PersonInsertDTO personTest = new PersonInsertDTO("", LocalDate.of(1989, 02, 20));
		String jsonBody = objectMapper.writeValueAsString(personTest);
		ResultActions result = mockMvc.perform(put("/persons/{id}", validId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));	
		result.andExpect(status().isUnprocessableEntity());

	}

	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdIsInvalid() throws Exception {
		ResultActions result = mockMvc.perform(get("/persons/{id}", invalidId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
		result.andExpect(jsonPath("$.message").value("Esta pessoa não esta cadastrada no nosso banco de dados"));

	}
	
	
}
