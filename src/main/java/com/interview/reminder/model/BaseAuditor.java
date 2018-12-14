package com.interview.reminder.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;


@EntityListeners(AuditingEntityListener.class)
@Data
@MappedSuperclass
public class BaseAuditor {
    @CreatedDate
    private Date createdAt;
    @CreatedBy
    private UUID createdBy;
    @LastModifiedBy
    private UUID modifiedBy;
    @LastModifiedDate
    private Date modifiedDate;
}
