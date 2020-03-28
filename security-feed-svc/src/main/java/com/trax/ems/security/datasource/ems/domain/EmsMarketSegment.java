package com.trax.ems.security.datasource.ems.domain;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "EmsMarketSegment")
@Table(name = "EMS_MARKETSEGMENT")
@Getter
@Setter
@Audited
public class EmsMarketSegment {

	@Id
	@Column(name = "ID")
	private BigInteger id;

	@Column(name = "MARKETSEGMENT")
	private String marketSegment;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "POSTTRADEMARKETSEGMENT")
	private String postTradeMarketSegment;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "marketSegment", fetch = FetchType.EAGER)
	@JsonManagedReference(value = "EmsMarketSegment")
	private Set<EmsProduct> instrumentProducts = new HashSet<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmsMarketSegment other = (EmsMarketSegment) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (marketSegment == null) {
			if (other.marketSegment != null)
				return false;
		} else if (!marketSegment.equals(other.marketSegment))
			return false;
		if (postTradeMarketSegment == null) {
			if (other.postTradeMarketSegment != null)
				return false;
		} else if (!postTradeMarketSegment.equals(other.postTradeMarketSegment))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((marketSegment == null) ? 0 : marketSegment.hashCode());
		result = prime * result + ((postTradeMarketSegment == null) ? 0 : postTradeMarketSegment.hashCode());
		return result;
	}

}
