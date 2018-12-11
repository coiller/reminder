package com.interview.reminder.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.interview.reminder.model.Account;
import com.interview.reminder.repository.AccountRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUsername(username);
		// grant authority based on its account type(doctor||patient)
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + account.getType());
		UserDetails userDetails = (UserDetails) new MyUser(account.getUsername(), account.getPassword(),
				Arrays.asList(authority), account.getAccount_id());
		return userDetails;
	}
}
