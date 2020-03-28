package com.trax.ems.security.datasource.ems.domain;

import java.math.BigInteger;
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

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "EmsInstrumentProduct")
@Table(name = "EMS_INSTRUMENT_PRODUCT")
@Getter
@Setter
@Audited
public class EmsProduct {

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

	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "MARKETSEGMENTID")
	@JsonBackReference(value = "EmsMarketSegment")
	private EmsMarketSegment marketSegment;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "CALENDARID", referencedColumnName = "CALENDARID")
	private EmsUtilCalendar utilCalendar;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "instrumentProduct", fetch = FetchType.EAGER)
	@JsonManagedReference(value = "EmsProductInstrument")
	private Set<EmsProductInstruments> instrumentProductInstruments;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "instrumentProducts", cascade = CascadeType.ALL)
	@JsonManagedReference(value = "EmsProductProtocols")
	private Set<EmsProductProtocols> instrumentProductProtocols;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fxStlDays == null) ? 0 : fxStlDays.hashCode());
		result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result + ((marketSegment == null) ? 0 : marketSegment.hashCode());
		result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
		result = prime * result + ((productDesc == null) ? 0 : productDesc.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((stlDays == null) ? 0 : stlDays.hashCode());
		result = prime * result + ((utilCalendar == null) ? 0 : utilCalendar.hashCode());
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
		EmsProduct other = (EmsProduct) obj;
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
		if (marketSegment == null) {
			if (other.marketSegment != null)
				return false;
		} else if (!marketSegment.equals(other.marketSegment))
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
		if (utilCalendar == null) {
			if (other.utilCalendar != null)
				return false;
		} else if (!utilCalendar.equals(other.utilCalendar))
			return false;
		return true;
	}

}
