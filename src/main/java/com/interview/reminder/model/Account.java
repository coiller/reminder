package com.interview.reminder.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","password","username"})
@Inheritance(strategy = InheritanceType.JOINED)
public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2778332461094730876L;

	public enum Type {
		DOCTOR, PATIENT,ADMIN;
	}

	// for security
	// use uuid2
	// for performance
	// remove dash + convert to binary for optimization
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID account_id;
	private String username;
	// for security
	// use BCrypt to hash password
	private String password;
	@Enumerated(EnumType.STRING)
	private Type type;

	public String getType() {
		return type.name();
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Account() {
	}

	public Account(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Account(String username, String password, Type type) {
		this.username = username;
		this.password = password;
		this.type = type;
	}
//
//	public UUID getAccount_id() {
//		return account_id;
//	}
//
//	public void setAccount_id(UUID account_id) {
//		this.account_id = account_id;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
//	}
}
