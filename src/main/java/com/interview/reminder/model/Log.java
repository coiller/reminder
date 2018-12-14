package com.interview.reminder.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;


@Data
@Entity
public class Log extends BaseAuditor {
    @Column(nullable = false)
    private String action;
    private String asset;
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID log_id;

    public Log() {
    }

    public Log(String action, String asset) {
        this.action = action;
        this.asset = asset;
    }
}
