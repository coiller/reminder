package com.interview.reminder.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.interview.reminder.model.Patient;
import com.interview.reminder.model.Reminder;

@RestController
@RequestMapping("/api/patient") // This means URL's start with /patient
public class PatientController extends BaseController {

	@PostMapping("/create")
	public @ResponseBody Patient addNewPatient(@RequestBody Map<String, String> body) {
		// @ResponseBody means the returned patient is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		Patient patient = new Patient(body.get("username"), body.get("password"), body.get("username"));
		patientRepository.save(patient);
		return patient;
	}

	@GetMapping("/reminder_list")
	public @ResponseBody List<Reminder> getReminders() {
		List<UUID> doctors = pairRepository.findDoctors(getUser().getAccount_id());
		return reminderRepository.findReminders(doctors);
	}

	@PutMapping("/reminder")
	public void markFinished(@RequestBody Map<String, UUID> body, HttpServletResponse res) {
		UUID reminder_id = body.get("reminder_id");
		Reminder reminder = reminderRepository.findById(reminder_id).orElse(null);
		if (reminder == null) {// no such reminder
			res.setStatus(404);
			return;
		}
		if (reminder.isFinished()
				|| time(reminder.getStart_time(), reminder.getDuration() * 3600 * 1000L).before(now())) {
			// has already finished or past the dead line
			res.setStatus(406);
			return;
		}
		reminderRepository.updateAsFinished(reminder_id);
		updateReport(reminder.getPair().getPair_id(), reminder.getPriority(), -1);
	}
}
