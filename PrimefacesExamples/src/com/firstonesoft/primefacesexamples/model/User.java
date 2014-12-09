package com.firstonesoft.primefacesexamples.model;

import java.io.Serializable;

/**
 * 
 * @author Bismarck Villca Soliz
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String firstname;
	private String lastname;
	private Integer age;
	private String street;
	private String city;
	private String postalCode;
	private String info;
	private String email;
	private String phone;
	
	private int cantidad = 0;
	
	public int getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public User() {
		age = 0;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname="
				+ lastname + ", age=" + age + ", street=" + street + ", city="
				+ city + ", postalCode=" + postalCode + ", info=" + info
				+ ", email=" + email + ", phone=" + phone + "]";
	}
	
}