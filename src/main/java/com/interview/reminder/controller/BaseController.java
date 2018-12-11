package com.interview.reminder.controller;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.interview.reminder.config.MyUser;
import com.interview.reminder.repository.AccountRepository;
import com.interview.reminder.repository.DoctorRepository;
import com.interview.reminder.repository.PairRepository;
import com.interview.reminder.repository.PatientRepository;
import com.interview.reminder.repository.ReminderRepository;

@CrossOrigin(origins = "*", maxAge = 3600) // for test only
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class BaseController {
	@Autowired // This means to get the bean called doctorRepository
	// Which is auto-generated by Spring, we will use it to handle the data
	protected AccountRepository accountRepository;
	@Autowired
	protected DoctorRepository doctorRepository;
	@Autowired
	protected PatientRepository patientRepository;
	@Autowired
	protected PairRepository pairRepository;
	@Autowired
	protected ReminderRepository reminderRepository;

	protected MyUser getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (MyUser) auth.getPrincipal();
	}

	protected void updateReport(UUID pair_id, String priority, int finished) {
		switch (priority) {
		case "high":
			pairRepository.updateHigh(pair_id, finished);
			break;
		case "middle":
			pairRepository.updateMiddle(pair_id, finished);
			break;
		case "low":
			pairRepository.updateLow(pair_id, finished);
			break;
		default:
			return;
		}
	}

	protected Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}

	protected Timestamp time(Timestamp base, long baise) {
		return new Timestamp(base.getTime() + baise);
	}
}
