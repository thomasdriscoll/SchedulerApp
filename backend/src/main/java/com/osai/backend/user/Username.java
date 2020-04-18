package com.osai.backend.user;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data 
@Entity
public class Username {

	private @GeneratedValue @Id Long id;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String[] addresses;
	private int activeTasks;

	Username(){}

	Username(
		String firstName,
		String lastName,
		String password,
		String email,
		String[] addresses,
		int activeTasks
	) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.addresses = addresses;
		this.activeTasks = activeTasks;

	};

	// Getters
	public String getFirstName() {
		return this.firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public String getEmail() {
		return this.email;
	}
	public String getPassword() {
		return this.password;
	}
	public int getActiveTasks() {
		return this.activeTasks;
	}
	public String[] getAddresses() {
		return this.addresses;
	}
	public Long getId() {
		return this.id;
	} 


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setActiveTasks(int activeTasks) {
		this.activeTasks = activeTasks;
	}
	public void setAddress(String[] addresses) {
		this.addresses = addresses;
	}
	public void setId(Long id) {
		this.id = id;
	}
}