package com.interview.reminder.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.interview.reminder.model.Account;

public interface UserBaseRepository<T extends Account> extends CrudRepository<T, UUID> {
	T findByUsername(String username);
}
