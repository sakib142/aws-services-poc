package com.trax.ems.security.datasource.blink.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity(name = "UtilCalendar")
@Table(name = "UTIL_CALENDAR")
@Getter
@Setter
public class UtilCalendar implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5385968608840916205L;

	@Id
    @Column(name = "CALENDARID")
    private BigInteger calendarId;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DISPLAYNAME")
    private String displayName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilCalendar that = (UtilCalendar) o;
        return Objects.equals(calendarId, that.calendarId) &&
                Objects.equals(code, that.code) &&
                Objects.equals(description, that.description) &&
                Objects.equals(displayName, that.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calendarId, code, description, displayName);
    }
}
