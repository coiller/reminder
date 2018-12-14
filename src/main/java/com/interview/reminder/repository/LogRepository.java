package com.interview.reminder.repository;

import com.interview.reminder.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface LogRepository extends JpaRepository<Log, UUID> {
    Log findFirstByActionAndCreatedByOrderByModifiedAtDesc(String action, UUID uuid);
}
