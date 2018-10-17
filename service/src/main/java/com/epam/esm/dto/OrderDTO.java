package com.epam.esm.dto;

import com.epam.esm.util.LocalDateTimeDesiarializer;
import com.epam.esm.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * The type Order dto.
 */
public class OrderDTO {
    private long id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDesiarializer.class)
    private LocalDateTime timeStamp;
    private String userName;
    private GiftCertificateDTO certificate;
    private BigDecimal cost;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets time stamp.
     *
     * @return the time stamp
     */
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets time stamp.
     *
     * @param timeStamp the time stamp
     */
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public GiftCertificateDTO getCertificate() {
        return certificate;
    }

    public void setCertificate(GiftCertificateDTO certificate) {
        this.certificate = certificate;
    }

    /**
     * Gets cost.
     *
     * @return the cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Sets cost.
     *
     * @param cost the cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", timeStamp=" + timeStamp +
                ", userName='" + userName + '\'' +
                ", certificate=" + certificate +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return id == orderDTO.id &&
                Objects.equals(timeStamp, orderDTO.timeStamp) &&
                Objects.equals(userName, orderDTO.userName) &&
                Objects.equals(certificate, orderDTO.certificate) &&
                Objects.equals(cost, orderDTO.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeStamp, userName, certificate, cost);
    }
}
