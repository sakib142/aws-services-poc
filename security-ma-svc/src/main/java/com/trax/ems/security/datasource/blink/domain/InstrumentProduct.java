package com.trax.ems.security.datasource.blink.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "InstrumentProduct")
@Table(name = "INSTRUMENT_PRODUCT")
@Getter
@Setter
public class InstrumentProduct implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 2995943720765071007L;

	@Id
	@Column(name = "ID")
	private BigInteger productId;

	@Column(name = "DESCRIPTION")
	private String productDesc;

	@Column(name = "PRODUCT_CD")
	private String productCode;

	@Column(name = "STLDAYS")
	private BigInteger stlDays;

	@Column(name = "FXSTLDAYS")
	private BigInteger fxStlDays;

	@Column(name = "ISDELETED")
	private String isDeleted;

	@ManyToOne
	@JoinColumn(name = "MARKETSEGMENTID")
	@JsonBackReference(value = "user-marketsegment")
	private MarketSegment marketSegments;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "instrumentProduct", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<InstrumentProductInstruments> instrumentProductInstruments = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "CALENDARID", referencedColumnName = "CALENDARID")
	private UtilCalendar utilCalendar;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "instrumentProducts", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<InstrumentProductProtocols> instrumentProductProtocols = new HashSet<>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fxStlDays == null) ? 0 : fxStlDays.hashCode());
		result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result + ((marketSegments == null) ? 0 : marketSegments.hashCode());
		result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
		result = prime * result + ((productDesc == null) ? 0 : productDesc.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((stlDays == null) ? 0 : stlDays.hashCode());
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
		InstrumentProduct other = (InstrumentProduct) obj;
		if (fxStlDays == null) {
			if (other.fxStlDays != null)
				return false;
		} else if (!fxStlDays.equals(other.fxStlDays))
			return false;
		if (isDeleted == null) {
			if (other.isDeleted != null)
				return false;
		} else if (!isDeleted.equals(other.isDeleted))
			return false;
		if (marketSegments == null) {
			if (other.marketSegments != null)
				return false;
		} else if (!marketSegments.equals(other.marketSegments))
			return false;
		if (productCode == null) {
			if (other.productCode != null)
				return false;
		} else if (!productCode.equals(other.productCode))
			return false;
		if (productDesc == null) {
			if (other.productDesc != null)
				return false;
		} else if (!productDesc.equals(other.productDesc))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (stlDays == null) {
			if (other.stlDays != null)
				return false;
		} else if (!stlDays.equals(other.stlDays))
			return false;
		return true;
	}
}
