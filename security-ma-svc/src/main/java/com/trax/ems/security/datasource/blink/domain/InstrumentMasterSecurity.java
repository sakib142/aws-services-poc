package com.trax.ems.security.datasource.blink.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "InstrumentMasterSecurity")
@Table(name = "INSTRUMENT_MASTERSECURITY")
@Getter
@Setter
public class InstrumentMasterSecurity implements Serializable {

	private static final long serialVersionUID = -3879185097602879277L;

	@Id
	@Column(name = "ID")
	private BigInteger instrumentId;

	@Column(name = "ISIN")
	private String isIn;

	@Column(name = "CUSIP")
	private String cusIp;

	@Column(name = "CINS")
	private String cins;

	@Column(name = "BONDTYPE")
	private String bondType;

	@Column(name = "TRACE_CODE")
	private String tracecode;

	@Column(name = "SHORTNAME")
	private String shortName;

	@Column(name = "DEFAULTTICKER")
	private String ticker;

	@Column(name = "COUPON")
	private BigInteger coupon;

	@Column(name = "PAYMENTFREQUENCY")
	private String couponFrequency;

	@Column(name = "INTERESTRATETYPE")
	private String couponType;

	@Column(name = "MATURITY")
	private Date maturityDate;

	@Column(name = "ISSUEDATE")
	private Date issueDate;

	@Column(name = "ACCRUALSTART")
	private Date accrualStartDate;

	@Column(name = "NEXTPAYDATE")
	private Date nextPaymentDate;

	@Column(name = "ISSUEAMOUNT")
	private BigDecimal issueamount;

	@Column(name = "AMOUNTOUTSTANDING")
	private BigDecimal amountoutStanding;

	@Column(name = "MINLOTSIZE")
	private BigInteger denomMinimum;

	@Column(name = "DENOMINCR")
	private BigDecimal DenomIncrement;

	@Column(name = "COUNTRYCODE")
	private String country;

	@Column(name = "ISSUER")
	private String issuer;

	@Column(name = "DEBT_TYPE_CD")
	private String debtClassification;

	@Column(name = "MOODYRATING")
	private String moodyRating;

	@Column(name = "SNPRATING")
	private String snprating;

	@Column(name = "NEXTCALLDATE")
	private Date nextCallDate;

	@Column(name = "DISPLAY_CALL_PRICE")
	private BigDecimal nextCallPrice;

	@Column(name = "NEXTPUTDATE")
	private Date nextPutDate;

	// @Column(name = "BENCHMARKID")
	// Number benchmarkid;

	@Column(name = "CALC_ISSUE")
	private String calcFlag;

	@Column(name = "DV01_WORST")
	private BigInteger dv01Worst;

	@Column(name = "MOD_DUR_WORST")
	private BigInteger modifiedDurationWorst;

	@Column(name = "ISDELETED")
	private String isDeleted;

	@Column(name = "TIMESTAMP")
	private Instant updatets;

	@Column(name = "ON_MAEL")
	private String onMAELVenue;

	@Column(name = "ON_MTXX")
	private String onMTXXVenue;

	@Column(name = "ON_MASG")
	private String onMASGVenue;

	@Column(name = "ON_MANL")
	private String onMANLVenue;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "MARKETSEGMENT")
	private MarketSegment marketSegment;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ISOCURRENCYCODE", referencedColumnName = "ISOCODE")
	private UtilCurrency utilCurrency;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "SECTORID", referencedColumnName = "ID")
	private InstrumentSector instrumentSector;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DenomIncrement == null) ? 0 : DenomIncrement.hashCode());
		result = prime * result + ((accrualStartDate == null) ? 0 : accrualStartDate.hashCode());
		result = prime * result + ((amountoutStanding == null) ? 0 : amountoutStanding.hashCode());
		result = prime * result + ((bondType == null) ? 0 : bondType.hashCode());
		result = prime * result + ((calcFlag == null) ? 0 : calcFlag.hashCode());
		result = prime * result + ((cins == null) ? 0 : cins.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((coupon == null) ? 0 : coupon.hashCode());
		result = prime * result + ((couponFrequency == null) ? 0 : couponFrequency.hashCode());
		result = prime * result + ((couponType == null) ? 0 : couponType.hashCode());
		result = prime * result + ((cusIp == null) ? 0 : cusIp.hashCode());
		result = prime * result + ((debtClassification == null) ? 0 : debtClassification.hashCode());
		result = prime * result + ((denomMinimum == null) ? 0 : denomMinimum.hashCode());
		result = prime * result + ((dv01Worst == null) ? 0 : dv01Worst.hashCode());
		result = prime * result + ((instrumentId == null) ? 0 : instrumentId.hashCode());
		result = prime * result + ((instrumentSector == null) ? 0 : instrumentSector.hashCode());
		result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result + ((isIn == null) ? 0 : isIn.hashCode());
		result = prime * result + ((issueDate == null) ? 0 : issueDate.hashCode());
		result = prime * result + ((issueamount == null) ? 0 : issueamount.hashCode());
		result = prime * result + ((issuer == null) ? 0 : issuer.hashCode());
		result = prime * result + ((marketSegment == null) ? 0 : marketSegment.hashCode());
		result = prime * result + ((maturityDate == null) ? 0 : maturityDate.hashCode());
		result = prime * result + ((modifiedDurationWorst == null) ? 0 : modifiedDurationWorst.hashCode());
		result = prime * result + ((moodyRating == null) ? 0 : moodyRating.hashCode());
		result = prime * result + ((nextCallDate == null) ? 0 : nextCallDate.hashCode());
		result = prime * result + ((nextCallPrice == null) ? 0 : nextCallPrice.hashCode());
		result = prime * result + ((nextPaymentDate == null) ? 0 : nextPaymentDate.hashCode());
		result = prime * result + ((nextPutDate == null) ? 0 : nextPutDate.hashCode());
		result = prime * result + ((onMAELVenue == null) ? 0 : onMAELVenue.hashCode());
		result = prime * result + ((onMANLVenue == null) ? 0 : onMANLVenue.hashCode());
		result = prime * result + ((onMASGVenue == null) ? 0 : onMASGVenue.hashCode());
		result = prime * result + ((onMTXXVenue == null) ? 0 : onMTXXVenue.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		result = prime * result + ((snprating == null) ? 0 : snprating.hashCode());
		result = prime * result + ((ticker == null) ? 0 : ticker.hashCode());
		result = prime * result + ((tracecode == null) ? 0 : tracecode.hashCode());
		result = prime * result + ((updatets == null) ? 0 : updatets.hashCode());
		result = prime * result + ((utilCurrency == null) ? 0 : utilCurrency.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstrumentMasterSecurity other = (InstrumentMasterSecurity) obj;
		if (DenomIncrement == null) {
			if (other.DenomIncrement != null)
				return false;
		} else if (!DenomIncrement.equals(other.DenomIncrement))
			return false;
		if (accrualStartDate == null) {
			if (other.accrualStartDate != null)
				return false;
		} else if (!accrualStartDate.equals(other.accrualStartDate))
			return false;
		if (amountoutStanding == null) {
			if (other.amountoutStanding != null)
				return false;
		} else if (!amountoutStanding.equals(other.amountoutStanding))
			return false;
		if (bondType == null) {
			if (other.bondType != null)
				return false;
		} else if (!bondType.equals(other.bondType))
			return false;
		if (calcFlag == null) {
			if (other.calcFlag != null)
				return false;
		} else if (!calcFlag.equals(other.calcFlag))
			return false;
		if (cins == null) {
			if (other.cins != null)
				return false;
		} else if (!cins.equals(other.cins))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (coupon == null) {
			if (other.coupon != null)
				return false;
		} else if (!coupon.equals(other.coupon))
			return false;
		if (couponFrequency == null) {
			if (other.couponFrequency != null)
				return false;
		} else if (!couponFrequency.equals(other.couponFrequency))
			return false;
		if (couponType == null) {
			if (other.couponType != null)
				return false;
		} else if (!couponType.equals(other.couponType))
			return false;
		if (cusIp == null) {
			if (other.cusIp != null)
				return false;
		} else if (!cusIp.equals(other.cusIp))
			return false;
		if (debtClassification == null) {
			if (other.debtClassification != null)
				return false;
		} else if (!debtClassification.equals(other.debtClassification))
			return false;
		if (denomMinimum == null) {
			if (other.denomMinimum != null)
				return false;
		} else if (!denomMinimum.equals(other.denomMinimum))
			return false;
		if (dv01Worst == null) {
			if (other.dv01Worst != null)
				return false;
		} else if (!dv01Worst.equals(other.dv01Worst))
			return false;
		if (instrumentId == null) {
			if (other.instrumentId != null)
				return false;
		} else if (!instrumentId.equals(other.instrumentId))
			return false;
		if (instrumentSector == null) {
			if (other.instrumentSector != null)
				return false;
		} else if (!instrumentSector.equals(other.instrumentSector))
			return false;
		if (isDeleted == null) {
			if (other.isDeleted != null)
				return false;
		} else if (!isDeleted.equals(other.isDeleted))
			return false;
		if (isIn == null) {
			if (other.isIn != null)
				return false;
		} else if (!isIn.equals(other.isIn))
			return false;
		if (issueDate == null) {
			if (other.issueDate != null)
				return false;
		} else if (!issueDate.equals(other.issueDate))
			return false;
		if (issueamount == null) {
			if (other.issueamount != null)
				return false;
		} else if (!issueamount.equals(other.issueamount))
			return false;
		if (issuer == null) {
			if (other.issuer != null)
				return false;
		} else if (!issuer.equals(other.issuer))
			return false;
		if (marketSegment == null) {
			if (other.marketSegment != null)
				return false;
		} else if (!marketSegment.equals(other.marketSegment))
			return false;
		if (maturityDate == null) {
			if (other.maturityDate != null)
				return false;
		} else if (!maturityDate.equals(other.maturityDate))
			return false;
		if (modifiedDurationWorst == null) {
			if (other.modifiedDurationWorst != null)
				return false;
		} else if (!modifiedDurationWorst.equals(other.modifiedDurationWorst))
			return false;
		if (moodyRating == null) {
			if (other.moodyRating != null)
				return false;
		} else if (!moodyRating.equals(other.moodyRating))
			return false;
		if (nextCallDate == null) {
			if (other.nextCallDate != null)
				return false;
		} else if (!nextCallDate.equals(other.nextCallDate))
			return false;
		if (nextCallPrice == null) {
			if (other.nextCallPrice != null)
				return false;
		} else if (!nextCallPrice.equals(other.nextCallPrice))
			return false;
		if (nextPaymentDate == null) {
			if (other.nextPaymentDate != null)
				return false;
		} else if (!nextPaymentDate.equals(other.nextPaymentDate))
			return false;
		if (nextPutDate == null) {
			if (other.nextPutDate != null)
				return false;
		} else if (!nextPutDate.equals(other.nextPutDate))
			return false;
		if (onMAELVenue == null) {
			if (other.onMAELVenue != null)
				return false;
		} else if (!onMAELVenue.equals(other.onMAELVenue))
			return false;
		if (onMANLVenue == null) {
			if (other.onMANLVenue != null)
				return false;
		} else if (!onMANLVenue.equals(other.onMANLVenue))
			return false;
		if (onMASGVenue == null) {
			if (other.onMASGVenue != null)
				return false;
		} else if (!onMASGVenue.equals(other.onMASGVenue))
			return false;
		if (onMTXXVenue == null) {
			if (other.onMTXXVenue != null)
				return false;
		} else if (!onMTXXVenue.equals(other.onMTXXVenue))
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		if (snprating == null) {
			if (other.snprating != null)
				return false;
		} else if (!snprating.equals(other.snprating))
			return false;
		if (ticker == null) {
			if (other.ticker != null)
				return false;
		} else if (!ticker.equals(other.ticker))
			return false;
		if (tracecode == null) {
			if (other.tracecode != null)
				return false;
		} else if (!tracecode.equals(other.tracecode))
			return false;
		if (updatets == null) {
			if (other.updatets != null)
				return false;
		} else if (!updatets.equals(other.updatets))
			return false;
		if (utilCurrency == null) {
			if (other.utilCurrency != null)
				return false;
		} else if (!utilCurrency.equals(other.utilCurrency))
			return false;
		return true;
	}
}
