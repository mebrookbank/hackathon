package com.hackathon.mred.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Horse.
 */
@Entity
@Table(name = "horse")
public class Horse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "horsename")
    private String horsename;

    @Column(name = "phone")
    private String phone;

    @Column(name = "ownername")
    private String ownername;

    @NotNull
    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    @Column(name = "locationx", nullable = false)
    private Double locationx;

    @NotNull
    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    @Column(name = "locationy", nullable = false)
    private Double locationy;

    @Column(name = "time")
    private ZonedDateTime time;

    @Column(name = "point")
    private String point;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHorsename() {
        return horsename;
    }

    public Horse horsename(String horsename) {
        this.horsename = horsename;
        return this;
    }

    public void setHorsename(String horsename) {
        this.horsename = horsename;
    }

    public String getPhone() {
        return phone;
    }

    public Horse phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOwnername() {
        return ownername;
    }

    public Horse ownername(String ownername) {
        this.ownername = ownername;
        return this;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public Double getLocationx() {
        return locationx;
    }

    public Horse locationx(Double locationx) {
        this.locationx = locationx;
        return this;
    }

    public void setLocationx(Double locationx) {
        this.locationx = locationx;
    }

    public Double getLocationy() {
        return locationy;
    }

    public Horse locationy(Double locationy) {
        this.locationy = locationy;
        return this;
    }

    public void setLocationy(Double locationy) {
        this.locationy = locationy;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Horse time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getPoint() {
        return point;
    }

    public Horse point(String point) {
        this.point = point;
        return this;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Horse horse = (Horse) o;
        if (horse.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, horse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Horse{" +
            "id=" + id +
            ", horsename='" + horsename + "'" +
            ", phone='" + phone + "'" +
            ", ownername='" + ownername + "'" +
            ", locationx='" + locationx + "'" +
            ", locationy='" + locationy + "'" +
            ", time='" + time + "'" +
            ", point='" + point + "'" +
            '}';
    }
}
