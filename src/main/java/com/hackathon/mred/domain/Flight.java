package com.hackathon.mred.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Flight.
 */
@Entity
@Table(name = "flight")
public class Flight implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tailno")
    private String tailno;

    @Column(name = "pilotname")
    private String pilotname;

    @Column(name = "startlocx")
    private Float startlocx;

    @Column(name = "startlocy")
    private Float startlocy;

    @Column(name = "endlocx")
    private Float endlocx;

    @Column(name = "endlocy")
    private Float endlocy;

    @Column(name = "starttime")
    private ZonedDateTime starttime;

    @Column(name = "endtime")
    private ZonedDateTime endtime;

    @Column(name = "phone")
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTailno() {
        return tailno;
    }

    public Flight tailno(String tailno) {
        this.tailno = tailno;
        return this;
    }

    public void setTailno(String tailno) {
        this.tailno = tailno;
    }

    public String getPilotname() {
        return pilotname;
    }

    public Flight pilotname(String pilotname) {
        this.pilotname = pilotname;
        return this;
    }

    public void setPilotname(String pilotname) {
        this.pilotname = pilotname;
    }

    public Float getStartlocx() {
        return startlocx;
    }

    public Flight startlocx(Float startlocx) {
        this.startlocx = startlocx;
        return this;
    }

    public void setStartlocx(Float startlocx) {
        this.startlocx = startlocx;
    }

    public Float getStartlocy() {
        return startlocy;
    }

    public Flight startlocy(Float startlocy) {
        this.startlocy = startlocy;
        return this;
    }

    public void setStartlocy(Float startlocy) {
        this.startlocy = startlocy;
    }

    public Float getEndlocx() {
        return endlocx;
    }

    public Flight endlocx(Float endlocx) {
        this.endlocx = endlocx;
        return this;
    }

    public void setEndlocx(Float endlocx) {
        this.endlocx = endlocx;
    }

    public Float getEndlocy() {
        return endlocy;
    }

    public Flight endlocy(Float endlocy) {
        this.endlocy = endlocy;
        return this;
    }

    public void setEndlocy(Float endlocy) {
        this.endlocy = endlocy;
    }

    public ZonedDateTime getStarttime() {
        return starttime;
    }

    public Flight starttime(ZonedDateTime starttime) {
        this.starttime = starttime;
        return this;
    }

    public void setStarttime(ZonedDateTime starttime) {
        this.starttime = starttime;
    }

    public ZonedDateTime getEndtime() {
        return endtime;
    }

    public Flight endtime(ZonedDateTime endtime) {
        this.endtime = endtime;
        return this;
    }

    public void setEndtime(ZonedDateTime endtime) {
        this.endtime = endtime;
    }

    public String getPhone() {
        return phone;
    }

    public Flight phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Flight flight = (Flight) o;
        if (flight.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, flight.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Flight{" +
            "id=" + id +
            ", tailno='" + tailno + "'" +
            ", pilotname='" + pilotname + "'" +
            ", startlocx='" + startlocx + "'" +
            ", startlocy='" + startlocy + "'" +
            ", endlocx='" + endlocx + "'" +
            ", endlocy='" + endlocy + "'" +
            ", starttime='" + starttime + "'" +
            ", endtime='" + endtime + "'" +
            ", phone='" + phone + "'" +
            '}';
    }
}
