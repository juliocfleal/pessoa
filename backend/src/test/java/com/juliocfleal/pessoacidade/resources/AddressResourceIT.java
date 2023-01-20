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
public class AddressResourceIT {

	
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
	public void createAddressShouldCreateNewAddress() throws Exception {
		AddressInsertDTO inserDTO= new AddressInsertDTO("Rua Pernambuco", "37701021", "543", "Pocos de Caldas", false, 1L);
		String jsonBody = objectMapper.writeValueAsString(inserDTO);
		ResultActions result = mockMvc.perform(post("/addresses")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.streetAddress").exists());
		result.andExpect(jsonPath("$.streetAddress").value("Rua Pernambuco"));
		result.andExpect(jsonPath("$.cep").exists());
		result.andExpect(jsonPath("$.cep").value("37701021"));
		result.andExpect(jsonPath("$.city").exists());
		result.andExpect(jsonPath("$.city").value("Pocos de Caldas"));
		result.andExpect(jsonPath("$.main").exists());
		result.andExpect(jsonPath("$.main").value(false));

	}
	
	@Test
	public void getAllPersonsAddressesShouldReturnAllPersonsAddresses() throws Exception {
		ResultActions result = mockMvc.perform(get("/addresses/{id}", validId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$[0].id").exists());
		result.andExpect(jsonPath("$[0].id").value(1));
		result.andExpect(jsonPath("$[0].streetAddress").exists());
		result.andExpect(jsonPath("$[0].streetAddress").value("Rua Monteiro Lobato"));
		result.andExpect(jsonPath("$[0].number").exists());
		result.andExpect(jsonPath("$[0].number").value("622"));
		result.andExpect(jsonPath("$[0].cep").exists());
		result.andExpect(jsonPath("$[0].cep").value("37701110"));
		result.andExpect(jsonPath("$[0].city").exists());
		result.andExpect(jsonPath("$[0].city").value("Pocos de Caldas"));
		result.andExpect(jsonPath("$[0].main").exists());
		result.andExpect(jsonPath("$[0].main").value(false));
	}
	
	@Test
	public void defineMainAddressShouldDefineNewMainAddress() throws Exception {

		ResultActions result = mockMvc.perform(put("/addresses/{id}", validId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.streetAddress").exists());
		result.andExpect(jsonPath("$.streetAddress").value("Rua Monteiro Lobato"));
		result.andExpect(jsonPath("$.number").exists());
		result.andExpect(jsonPath("$.number").value("622"));
		result.andExpect(jsonPath("$.cep").exists());
		result.andExpect(jsonPath("$.cep").value("37701110"));
		result.andExpect(jsonPath("$.city").exists());
		result.andExpect(jsonPath("$.city").value("Pocos de Caldas"));
		result.andExpect(jsonPath("$.main").exists());
		result.andExpect(jsonPath("$.main").value(true));
	}
	

	
	@Test
	public void createAddressShouldReturnUnprocessableEntityWhenIncompletData() throws Exception {
		AddressInsertDTO inserDTO= new AddressInsertDTO("", "37701021", "543", "Pocos de Caldas", false, 1L);
		String jsonBody = objectMapper.writeValueAsString(inserDTO);
		ResultActions result = mockMvc.perform(post("/addresses")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void defineMainAddressShouldReturnNotFoundWhenIdIsInvalid() throws Exception {
		ResultActions result = mockMvc.perform(put("/addresses/{id}", invalidId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
		result.andExpect(jsonPath("$.message").value("Este endereco não esta cadastrada no nosso banco de dados"));
	}
}
