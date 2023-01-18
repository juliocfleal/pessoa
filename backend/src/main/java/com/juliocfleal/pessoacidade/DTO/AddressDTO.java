package com.juliocfleal.pessoacidade.DTO;

import com.juliocfleal.pessoacidade.entities.Address;

public class AddressDTO {

	private Long id;
	private String streetAddress;
	private String CEP;
	private String number;
	private String city;
	private boolean isMain; 
	
	public AddressDTO() {
		
	}

	public AddressDTO(Long id, String streetAddress, String CEP, String number, String city, boolean isMain) {
		this.id = id;
		this.streetAddress = streetAddress;
		this.CEP = CEP;
		this.number = number;
		this.city = city;
		this.isMain = isMain;
	}
	
	public AddressDTO(Address address) {
		this.id = address.getId();
		this.streetAddress = address.getStreetAddress();
		CEP = address.getCEP();
		this.number = address.getNumber();
		this.city = address.getCity();
		this.isMain = address.isMain();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String CEP) {
		this.CEP = CEP;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isMain() {
		return isMain;
	}

	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}
	
	

}
