package com.interview.reminder.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Doctor extends Account {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5565672523649886938L;
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
	private Set<Pair> doctor_patients;

	public Doctor() {
	}

	public Doctor(String username, String password) {
		super(username, password);
		this.setType(Type.doctor);
		doctor_patients = new HashSet<>();
	}

	public Set<Pair> getPatients() {
		return doctor_patients;
	}

	public void setPatients(Set<Pair> doctor_patients) {
		this.doctor_patients = doctor_patients;
	}

}
