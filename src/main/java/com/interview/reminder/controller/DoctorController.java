package com.interview.reminder.controller;

import com.interview.reminder.model.Doctor;
import com.interview.reminder.model.Pair;
import com.interview.reminder.model.Profile;
import com.interview.reminder.model.Reminder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/doctor") // This means URL's start with /doctor
public class DoctorController extends BaseController {
    private Doctor cur() {
        Doctor doctor = doctorRepository.findByUsername(getUser().getUsername());
        doctor.setPassword(null);
        return doctor;
    }

	@PostMapping("/create")
	public @ResponseBody Doctor addNewDoctor(@RequestBody Map<String, String> body) {
		Doctor doctor = new Doctor(body.get("username"), body.get("password"), body.get("username"));
		doctorRepository.save(doctor);
		return doctor;
	}

    @GetMapping("/profile")
    public Doctor getProfile() {
        return cur();
    }

    @PostMapping("/profile")
    public void setProfile(Profile profile) {
        Doctor doctor = doctorRepository.findByUsername(getUser().getUsername());
        doctor.setProfile(profile);
        doctorRepository.save(doctor);
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
		Pair pair = pairRepository.findOne(UUID.fromString((String) body.get("pair_id")));
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
