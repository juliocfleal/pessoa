package com.juliocfleal.pessoacidade.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.juliocfleal.pessoacidade.DTO.AddressDTO;
import com.juliocfleal.pessoacidade.DTO.AddressInsertDTO;
import com.juliocfleal.pessoacidade.services.AddressService;

@RestController
@RequestMapping(value = "/addresses")
public class AddressResource {
	
	@Autowired
	AddressService service;
	
	@PostMapping
	public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressInsertDTO dto) {
		AddressDTO addressDTO = service.createAddress(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(addressDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(addressDTO);
	}
	
	@GetMapping(value = "/{personId}")
	public ResponseEntity<List<AddressDTO>> getAllPersonsAddresses(@PathVariable Long personId){
		List<AddressDTO> listDTO = service.getAllPersonsAddresses(personId);
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<AddressDTO> defineMainAddress(@PathVariable Long id){
		AddressDTO dto = service.defineMainAddress(id);
		return ResponseEntity.ok().body(dto);
	}

}
