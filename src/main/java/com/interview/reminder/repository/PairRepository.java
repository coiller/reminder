package com.interview.reminder.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.interview.reminder.model.Pair;

public interface PairRepository extends CrudRepository<Pair, UUID> {
	@Query(value = "select * from pair where doctor_id=?1 order by unfinished_high desc, unfinished_middle desc, unfinished_low desc", nativeQuery = true)
	List<Pair> findPatients(UUID doctor_id);
}
