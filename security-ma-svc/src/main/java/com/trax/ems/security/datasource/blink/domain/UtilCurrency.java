package com.trax.ems.security.datasource.blink.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Objects;


@Entity(name = "UtilCurrency")
@Table(name = "UTIL_CURRENCY")
@Getter
@Setter
public class UtilCurrency {

    @Id
    @Column(name = "ISOCODE")
    private String isoCode;

    @Column(name = "LONGNAME")
    private String longName;

    @Column(name = "SHORTNAME")
    private String shortName;

    @Column(name = "PREPENDSYMBOL")
    private String perPendSymbol;

    @Column(name = "APPENDSYMBOL")
    private String appendSymbol;

    @Column(name = "MINORUNIT")
    private BigInteger minorUnit;

    @Column(name = "TEXTSYMBOL")
    private String textSymbol;

    @Column(name = "UNICODESYMBOL")
    private String uniCodeSymbol;

    @Column(name = "POSTFIXSYMBOL")
    private String postFixSymbol;

    @Column(name = "MFD_FXRATE_EUR")
    private BigInteger mfdFxrateEur;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilCurrency that = (UtilCurrency) o;
        return Objects.equals(isoCode, that.isoCode) &&
                Objects.equals(longName, that.longName) &&
                Objects.equals(shortName, that.shortName) &&
                Objects.equals(perPendSymbol, that.perPendSymbol) &&
                Objects.equals(appendSymbol, that.appendSymbol) &&
                Objects.equals(minorUnit, that.minorUnit) &&
                Objects.equals(textSymbol, that.textSymbol) &&
                Objects.equals(uniCodeSymbol, that.uniCodeSymbol) &&
                Objects.equals(postFixSymbol, that.postFixSymbol) &&
                Objects.equals(mfdFxrateEur, that.mfdFxrateEur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isoCode, longName, shortName, perPendSymbol, appendSymbol, minorUnit, textSymbol, uniCodeSymbol, postFixSymbol, mfdFxrateEur);
    }
}
