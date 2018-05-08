package com.interview.reminder.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Reminder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6113823989122397313L;

	private enum Priority {
		high, middle, low;
	}

	// use uuid2 & remove dash + convert to binary for optimization
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID reminder_id;
	private String description;
	private byte duration;
	@Enumerated(EnumType.STRING)
	private Priority priority;
	private boolean finished;
	private Timestamp start_time;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pair_id")
	private Pair pair;

	public Reminder() {
	}

	public Reminder(String description, byte duration, String priority, boolean finished) {
		super();
		this.description = description;
		this.duration = duration;
		this.priority = Priority.valueOf(priority);
		this.finished = finished;
	}

	public UUID getReminder_id() {
		return reminder_id;
	}

	public void setReminder_id(UUID reminder_id) {
		this.reminder_id = reminder_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(byte duration) {
		this.duration = duration;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Pair getPair() {
		return pair;
	}

	public void setPair(Pair pair) {
		this.pair = pair;
	}
}
