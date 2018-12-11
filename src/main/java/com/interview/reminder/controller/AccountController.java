package com.interview.reminder.controller;

import com.interview.reminder.model.Account;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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

	@PostMapping("/api/sign-up")
	public void signUp(@RequestBody Account account, HttpServletResponse response){
		Boolean exist= accountRepository.countByUsername(account.getUsername())!=0;
		if (exist){
			response.setStatus(400);
			return;
		}else {
			accountRepository.save(account);
			response.setStatus(200);
			return;
		}
	}
}
