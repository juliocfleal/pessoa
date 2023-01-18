package com.juliocfleal.pessoacidade.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.juliocfleal.pessoacidade.DTO.AddressDTO;
import com.juliocfleal.pessoacidade.DTO.AddressInsertDTO;
import com.juliocfleal.pessoacidade.entities.Address;
import com.juliocfleal.pessoacidade.entities.Person;
import com.juliocfleal.pessoacidade.repositories.AddressRepository;
import com.juliocfleal.pessoacidade.repositories.PersonRepository;
import com.juliocfleal.pessoacidade.services.exceptions.ResourceNotFoundException;

@Service
public class AddressService {

	
	@Autowired
	AddressRepository repository;
	
	@Autowired
	PersonRepository personRepository;
	
	@Transactional(propagation=Propagation.REQUIRED, noRollbackFor=Exception.class)
	public AddressDTO createAddress(AddressInsertDTO dto) {
		Address address = new Address();
		address.setStreetAddress(dto.getStreetAddress());
		address.setNumber(dto.getNumber());
		address.setCEP(dto.getCEP());
		address.setCity(dto.getCity());
		if(dto.isMain() == true) {
			setAllPersonsAddressNotMain(dto.getUserId());
			address.setMain(true);
		}else {
			address.setMain(false);
		}
		Person person = personRepository.findById(dto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Esta pessoa não esta cadastrada no nosso banco de dados"));
		address.setPerson(person);
		address = repository.save(address);	
		return new AddressDTO(address);
	}
	
	public List<AddressDTO> getAllPersonsAddresses(Long userId){
		List<Address> list = repository.findAllPersonsAddresses(userId);
		List<AddressDTO> listDTO = list.stream().map(x -> new AddressDTO(x)).collect(Collectors.toList());
		return listDTO;
	}
	
	public AddressDTO defineMainAddress(Long addressId) {
		Address address = repository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Este endereco não esta cadastrada no nosso banco de dados"));
		setAllPersonsAddressNotMain(address.getPerson().getId());
		address.setMain(true);
		address = repository.save(address);
		return new AddressDTO(address);
	}
	
	
	private void setAllPersonsAddressNotMain(Long userId) {
		List<Address> list = repository.findAllPersonsMainAddress(userId);
		if(list.isEmpty() == false) {
			for(Address address : list) {
			address.setMain(false);
			repository.save(address);
			}
		}
	}
}
