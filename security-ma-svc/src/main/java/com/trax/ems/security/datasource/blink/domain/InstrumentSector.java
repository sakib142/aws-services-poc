package com.trax.ems.security.datasource.blink.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Objects;


@Entity(name = "InstrumentSector")
@Table(name = "INSTRUMENT_SECTOR")
@Getter
@Setter
public class InstrumentSector {

    @Id
    @Column(name = "ID")
    private BigInteger id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SEQUENCE")
    private BigInteger sequence;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "SHORTNAME")
    private String shortName;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstrumentSector that = (InstrumentSector) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(sequence, that.sequence) &&
                Objects.equals(type, that.type) &&
                Objects.equals(shortName, that.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, sequence, type, shortName);
    }


}
