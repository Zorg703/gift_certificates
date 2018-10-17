package com.epam.esm.audit;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

/**
 * The type Gift certificate audit data.
 *
 * @param <U> the type parameter
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class GiftCertificateAuditData<U> {
    /**
     * The Crated by.
     */
    @CreatedBy
    protected U cratedBy;

    /**
     * The Local date.
     */
    @CreatedDate
    @Column(name = "creation_date")
    protected LocalDate localDate;

    /**
     * The Modificated by.
     */
    @LastModifiedBy
    protected U modificatedBy;

    /**
     * The Modificated date.
     */
    @LastModifiedDate
    @Column(name = "modification_date")
    protected LocalDate modificatedDate;
}
