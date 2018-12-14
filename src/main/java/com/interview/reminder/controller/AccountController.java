package com.interview.reminder.controller;

import com.interview.reminder.config.JwtTokenProvider;
import com.interview.reminder.exception.CustomException;
import com.interview.reminder.model.Account;
import com.interview.reminder.model.Log;
import com.interview.reminder.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class AccountController extends BaseController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository userRepository;
    @Value("${security.jwt.validityInMilliseconds}")
    private long validityInMilliseconds;
    @PostMapping("/api/login")
    public Object login(@RequestBody Account account) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword()));
            String token = jwtTokenProvider.createToken(account.getUsername(), userRepository.findByUsername(account.getUsername()).getType());
            SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
            Log last = logRepository.findFirstByActionAndCreatedByOrderByModifiedAtDesc("LOGIN", getUser().getAccount_id());
            if (last != null && last.getAsset() == null) {
                throw new CustomException("This account has already login", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("token", token);
            log("LOGIN", null);
            return map;
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/api/logout")
    public void logout() {
        Log last = logRepository.findFirstByActionAndCreatedByOrderByModifiedAtDesc("LOGIN", getUser().getAccount_id());
        last.setAsset("LOGOUT");
        logRepository.save(last);
    }

    @PostMapping("/api/sign-up")
    public Object signUp(@RequestBody Account user) {
        if (userRepository.countByUsername(user.getUsername()) == 0) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            HashMap<String, String> map = new HashMap<>();
            String token = jwtTokenProvider.createToken(user.getUsername(), user.getType());
            map.put("token", token);
            SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
            log("SIGNUP", null);
            return map;
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
