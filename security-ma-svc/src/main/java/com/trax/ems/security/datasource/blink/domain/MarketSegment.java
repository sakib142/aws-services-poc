package com.trax.ems.security.datasource.blink.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "MarketSegment")
@Table(name = "INSTRUMENT_MARKETSEGMENT")
@Getter
@Setter
public class MarketSegment implements Serializable {

	private static final long serialVersionUID = 6939062779958962756L;

	@Id
	@Column(name = "ID")
	private long id;

	@Column(name = "MARKETSEGMENT")
	private String marketSegment;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "POSTTRADEMARKETSEGMENT")
	private String postTradeMarketSegment;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "marketSegments")
	@JsonManagedReference
	private Set<InstrumentProduct> instrumentProducts = new HashSet<>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((marketSegment == null) ? 0 : marketSegment.hashCode());
		result = prime * result + ((postTradeMarketSegment == null) ? 0 : postTradeMarketSegment.hashCode());
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
		MarketSegment other = (MarketSegment) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
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
}
