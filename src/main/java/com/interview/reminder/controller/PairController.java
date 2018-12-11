package com.interview.reminder.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.interview.reminder.model.Doctor;
import com.interview.reminder.model.Pair;
import com.interview.reminder.model.Patient;

@RestController
@RequestMapping("/api")
public class PairController extends BaseController {

	@PostMapping("/pair/create")
	public @ResponseBody Pair addNewPair(@RequestBody Doctor doctor, @RequestBody Patient patient) {
		// @ResponseBody means the returned UUID is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		Pair pair = new Pair(doctor, patient);
		pairRepository.save(pair);
		return pair;
	}
}
