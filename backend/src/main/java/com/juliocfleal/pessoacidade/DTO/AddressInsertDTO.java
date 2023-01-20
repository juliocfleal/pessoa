package com.juliocfleal.pessoacidade.DTO;

import jakarta.validation.constraints.NotBlank;

public class AddressInsertDTO {

	@NotBlank(message = "Campo Obrigatório")
	private String streetAddress;
	
	@NotBlank(message = "Campo Obrigatório")
	private String CEP;
	
	@NotBlank(message = "Campo Obrigatório")
	private String number;
	private String city;	
	private boolean isMain;
	private Long userId;
	
	public AddressInsertDTO() {
		
	}

	public AddressInsertDTO(String streetAddress, String cEP, String number, String city, boolean isMain, Long userId) {
		this.streetAddress = streetAddress;
		this.CEP = cEP;
		this.number = number;
		this.city = city;
		this.isMain = isMain;
		this.userId = userId;
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

	public void setCEP(String cEP) {
		this.CEP = cEP;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	

}
