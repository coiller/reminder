package com.interview.reminder.model;

import javax.persistence.Entity;

@Entity
public class Patient extends Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = -394216745175781428L;
	private String name;

	public Patient() {
	}

	public Patient(String username, String password, String name) {
		super(username, password);
		this.setType(Type.PATIENT);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
