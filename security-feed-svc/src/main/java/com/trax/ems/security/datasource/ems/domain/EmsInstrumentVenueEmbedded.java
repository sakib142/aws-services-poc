package com.trax.ems.security.datasource.ems.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class EmsInstrumentVenueEmbedded implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "INST_ID")
	private BigInteger emsInstrumentId;

	@Column(name = "VENUE_ID")
	private BigInteger emsVenueId;

	public EmsInstrumentVenueEmbedded() {
	}

	public EmsInstrumentVenueEmbedded(BigInteger instrumentId, BigInteger venueId) {
		this.emsInstrumentId = instrumentId;
		this.emsVenueId = venueId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		EmsInstrumentVenueEmbedded that = (EmsInstrumentVenueEmbedded) o;
		return Objects.equals(emsInstrumentId, that.emsInstrumentId) && Objects.equals(emsVenueId, that.emsVenueId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(emsInstrumentId, emsVenueId);
	}
}
