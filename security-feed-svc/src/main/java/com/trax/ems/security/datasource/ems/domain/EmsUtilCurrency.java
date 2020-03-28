package com.trax.ems.security.datasource.ems.domain;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "EmsUtilCurrency")
@Table(name = "EMS_UTIL_CURRENCY")
@Data
@EqualsAndHashCode(of = { "isoCode" })
@Audited
public class EmsUtilCurrency {

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

}
