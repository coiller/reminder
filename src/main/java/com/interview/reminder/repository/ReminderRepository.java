package com.interview.reminder.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.interview.reminder.model.Reminder;

public interface ReminderRepository extends CrudRepository<Reminder, UUID> {
	@Query(value = "select * from reminder where patient_id=?1 order by priority,date_add(start_time,interval duration hour);", nativeQuery = true)
	List<Reminder> findReminders(UUID patient_id);

	@Query(value = "select * from reminder where doctor_id=?1 and patient_id=?2 order by start_time;", nativeQuery = true)
	List<Reminder> findHistoryReminders(UUID doctor_id, UUID patient_id);
}
