package com.interview.reminder.config;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MyUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3164909722424346622L;
	private UUID account_id;

	public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
			UUID account_id) {
		super(username, password, authorities);
		this.setAccount_id(account_id);
	}

	public UUID getAccount_id() {
		return account_id;
	}

	public void setAccount_id(UUID account_id) {
		this.account_id = account_id;
	}

}
