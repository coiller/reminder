package com.interview.reminder.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.interview.reminder.model.Doctor;
import com.interview.reminder.model.Pair;
import com.interview.reminder.model.Reminder;

@RestController
@RequestMapping("/api/doctor") // This means URL's start with /doctor
public class DoctorController extends BaseController {

	@PostMapping("/create")
	public @ResponseBody Doctor addNewDoctor(@RequestBody Map<String, String> body) {
		// @ResponseBody means the returned doctor is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		Doctor doctor = new Doctor(body.get("username"), body.get("password"), body.get("username"));
		doctorRepository.save(doctor);
		return doctor;
	}

	@GetMapping("/reminder_list")
	public @ResponseBody List<Pair> getPatients() {
		UUID doctor_id = getUser().getAccount_id();
		List<Pair> patients = pairRepository.findPatients(doctor_id);
		return patients;
	}

	@PostMapping("/reminder_list/history_reminder")
	public @ResponseBody List<Map<Date, Integer>> getHistory(@RequestBody Map<String, Object> body) {
		return reminderRepository.findHistoryReminders(UUID.fromString((String) body.get("pair_id")));
	}

	@PostMapping("/reminder")
	public @ResponseBody void newReminder(@RequestBody Map<String, Object> body, HttpServletResponse res) {
		Pair pair = pairRepository.findById(UUID.fromString((String) body.get("pair_id"))).orElse(null);
		if (pair == null) {
			res.setStatus(404);
			return;
		}
		Reminder reminder = new Reminder(pair, (String) body.get("description"), (byte) (int) body.get("duration"),
				(String) body.get("priority"));
		Timestamp start_time = (Timestamp) body.get("start_time");
		if (start_time == null) {
			start_time = now();
		}
		reminder.setStart_time(start_time);
		reminderRepository.save(reminder);
		updateReport(pair.getPair_id(), reminder.getPriority(), 1);
		res.setStatus(200);
	}
}
