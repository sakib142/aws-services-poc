package com.trax.ems.security.datasource.blink.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class InstrumentProductEmbedded implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3737527764298984185L;


	@JoinColumn(name = "INSTRUMENTID")
	@Getter
	@Setter
	private BigInteger instrumentId;

	@JoinColumn(name = "PRODUCTID")
	@Getter
	@Setter
	private BigInteger productId;

	@SuppressWarnings("unused")
	private InstrumentProductEmbedded() {
	}

	public InstrumentProductEmbedded(BigInteger instrumentId, BigInteger productId) {
		this.instrumentId = instrumentId;
		this.productId = productId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		InstrumentProductEmbedded that = (InstrumentProductEmbedded) o;
		return Objects.equals(instrumentId, that.instrumentId) && Objects.equals(productId, that.productId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(instrumentId, productId);
	}

}
