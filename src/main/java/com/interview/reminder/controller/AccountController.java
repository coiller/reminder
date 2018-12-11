package com.interview.reminder.controller;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.interview.reminder.model.Account;

@RestController
public class AccountController extends BaseController {
	@PostMapping("/api/login")
	public @ResponseBody Account login(@RequestBody Map<String, String> body) {
		Account account = accountRepository.findByUsername(body.get("username"));
		if (BCrypt.checkpw(body.get("password"), account.getPassword())) {
			return account;
		}
		return null;
	}
}
