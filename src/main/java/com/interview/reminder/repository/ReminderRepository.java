package com.interview.reminder.repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.reminder.model.Reminder;

public interface ReminderRepository extends CrudRepository<Reminder, UUID> {

	// patient api
	// select reminders that have start for current patient ordered by start time
	// near to far
	@Query(value = "select * from reminder where start_time<now() and pair_id in ?1 "
			+ "order by start_time desc;", nativeQuery = true)
	List<Reminder> findReminders(List<UUID> pair_id);

	// marked as finished
	@Modifying
	@Transactional
	@Query(value = "update reminder set finished=1 where reminder_id=?1", nativeQuery = true)
	void updateAsFinished(UUID reminder_id);

	// doctor api
	// select history report of reminders that posted in last 7 days
	@Query(value = "select date(start_time) as date,count(*) as num from reminder "
			+ "where finished=0 and pair_id=?1 and date(start_time)>date_sub(date(now()),interval 7 day) and start_time<now() "
			+ "group by date(start_time);", nativeQuery = true)
	List<Map<Date, Integer>> findHistoryReminders(UUID p);

}
