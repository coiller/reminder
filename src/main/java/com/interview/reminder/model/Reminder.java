package com.interview.reminder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"pair"})
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
	private Date start_time;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pair_id")
	private Pair pair;

	public Reminder() {
	}

	public Reminder(Pair pair, String description, byte duration, String priority) {
		super();
		this.pair = pair;
		this.description = description;
		this.duration = duration;
		this.priority = Priority.valueOf(priority);

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

	public String getPriority() {
		return priority.name();
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

	public Date getStart_time() {
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
