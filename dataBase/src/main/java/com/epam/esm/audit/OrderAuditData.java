package com.epam.esm.audit;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * The type Order audit data.
 *
 * @param <U> the type parameter
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class OrderAuditData<U> {
    /**
     * The Crated by.
     */
    @CreatedBy
    protected U cratedBy;

    /**
     * The Local date time.
     */
    @CreatedDate
    @Column(name = "time_stamp")
    protected LocalDateTime localDateTime;
}
