package com.interview.reminder.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Patient extends Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = -394216745175781428L;
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
	private Set<Pair> patient_doctors;

	public Patient() {
	}

	public Patient(String username, String password) {
		super(username, password);
		this.setType(Type.patient);
		patient_doctors = new HashSet<>();
	}

	public Set<Pair> getPatient_doctors() {
		return patient_doctors;
	}

	public void setPatient_doctors(Set<Pair> patient_doctors) {
		this.patient_doctors = patient_doctors;
	}
}
