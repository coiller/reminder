package com.interview.reminder.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","doctor"})
public class Pair implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3436339031487970227L;
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID pair_id;
	private short unfinished_high = 0;
	private short unfinished_middle = 0;
	private short unfinished_low = 0;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name = "patient_id")
	private Patient patient;
//	@OneToMany(mappedBy = "pair", fetch = FetchType.LAZY)
//	private Set<Reminder> reminders;

	public Pair() {
	}

	public Pair(UUID pair_id, short unfinished_high, short unfinished_middle, short unfinished_low,Patient patient) {
		super();
		this.pair_id = pair_id;
		this.unfinished_high = unfinished_high;
		this.unfinished_middle = unfinished_middle;
		this.unfinished_low = unfinished_low;
		this.patient=patient;
	}

	public Pair(Doctor doctor, Patient patient) {
		super();
		this.doctor = doctor;
		this.patient = patient;
//		reminders = new HashSet<>();
	}

	public UUID getPair_id() {
		return pair_id;
	}

	public void setPair_id(UUID pair_id) {
		this.pair_id = pair_id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public short getUnfinished_high() {
		return unfinished_high;
	}

	public void setUnfinished_high(short unfinished_high) {
		this.unfinished_high = unfinished_high;
	}

	public short getUnfinished_middle() {
		return unfinished_middle;
	}

	public void setUnfinished_middle(short unfinished_middle) {
		this.unfinished_middle = unfinished_middle;
	}

	public short getUnfinished_low() {
		return unfinished_low;
	}

	public void setUnfinished_low(short unfinished_low) {
		this.unfinished_low = unfinished_low;
	}
}
