package com.interview.reminder.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.interview.reminder.model.Pair;

public interface PairRepository extends CrudRepository<Pair, UUID> {
	// doctor api
	// find patients list
	@Query(value = "select * from pair where doctor_id=?1 "
			+ "order by unfinished_high desc, unfinished_middle desc, unfinished_low desc", nativeQuery = true)
	List<Pair> findPatients(UUID doctor_id);

	// patient api
	// find doctors list
	@Query(value = "select pair_id from pair where patient_id=?1", nativeQuery = true)
	List<UUID> findDoctors(UUID patient_id);

	// update reports
	@Modifying
	@Transactional
	@Query(value = ("update pair set unfinished_high=unfinished_high+?2 where pair_id=?1"), nativeQuery = true)
	void updateHigh(UUID pair_id, int finished);

	@Modifying
	@Transactional
	@Query(value = ("update pair set unfinished_middle=unfinished_middle+?2 where pair_id=?1"), nativeQuery = true)
	void updateMiddle(UUID pair_id, int finished);

	@Modifying
	@Transactional
	@Query(value = ("update pair set unfinished_low=unfinished_low+?2 where pair_id=?1"), nativeQuery = true)
	void updateLow(UUID pair_id, int finished);

}
