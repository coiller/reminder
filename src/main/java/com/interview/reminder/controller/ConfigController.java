package com.interview.reminder.controller;

import com.interview.reminder.model.Doctor;
import com.interview.reminder.model.Pair;
import com.interview.reminder.model.Patient;
import com.interview.reminder.model.Reminder;
import com.interview.reminder.repository.DoctorRepository;
import com.interview.reminder.repository.PairRepository;
import com.interview.reminder.repository.PatientRepository;
import com.interview.reminder.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ConfigController extends BaseController {
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PairRepository pairRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ReminderRepository reminderRepository;

    @PutMapping("/config/init_db")
    public String init_db() {
        Set<Doctor> doctors = new HashSet<Doctor>();
        Set<Patient> patients = new HashSet<Patient>();
        Set<Pair> pairs = new HashSet<Pair>();
        Set<Reminder> reminders = new HashSet<Reminder>();
        for (int i = 2; i <= 500; i++) {
            // new 500 doctors with username:"doctor_i", password:"doctor_i"
            Doctor doctor = new Doctor("doctor" + String.valueOf(i), "doctor" + String.valueOf(i),
                    "doctor" + String.valueOf(i));
            doctors.add(doctor);
            for (int j = (i - 1) * 40 + 1; j <= i * 40; j++) {
                // new 40 patients for each doctor with username:"patient_i",
                // password:"patient_i"
                Patient patient = new Patient("patient" + String.valueOf(j), "patient" + String.valueOf(j),
                        "patient" + String.valueOf(j));
                patients.add(patient);
                Pair pair = new Pair(doctor, patient);
                pairs.add(pair);
                if (i == 1) {
                    String description = String.format("Reminder posted by %s to %s.", doctor.getUsername(),
                            patient.getUsername());
                    String[] Priority = {"high", "middle", "low"};
                    short[] unfinished = new short[3];
                    Random rand = new Random();
                    // new reminders for each patient of doctor_1
                    for (int day = -7; day < 7; day++) {
                        int reminderNum = rand.nextInt(11) + 10;
                        for (int r = 0; r < reminderNum; r++) {
                            byte duration = (byte) (rand.nextInt(48) + 1);
                            int priority = rand.nextInt(3);
                            boolean finished = rand.nextBoolean();
                            Reminder reminder = new Reminder(pair, description, duration, Priority[priority]);
                            Timestamp start_time = time(now(), day * 24 * 3600 * 1000);
                            reminder.setStart_time(start_time);
                            reminder.setFinished(finished);
                            reminders.add(reminder);
                            if (!finished) {// update summary
                                unfinished[priority]++;
                            }
                        }
                    }
                    pair.setUnfinished_high(unfinished[0]);
                    pair.setUnfinished_middle(unfinished[1]);
                    pair.setUnfinished_low(unfinished[2]);
                }
            }
        }
        doctorRepository.save(doctors);
        patientRepository.save(patients);
        pairRepository.save(pairs);
        reminderRepository.save(reminders);
        return "done!";
    }
}
