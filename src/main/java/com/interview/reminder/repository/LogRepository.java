package com.interview.reminder.repository;

import com.interview.reminder.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @Author mike
 * @Date 12/13/18
 * @Last Modified by: mike
 * @Last Modified time:12/13/18 9:36 PM
 */
public interface LogRepository extends JpaRepository<Log, UUID> {
}
