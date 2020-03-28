package com.trax.ems.security.datasource.ems.domain;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class EmsProductInstrumentEmbedded implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "INSTRUMENTID")
	@Setter
	@Getter
	private BigInteger instrumentId;

	@Column(name = "PRODUCTID")
	@Setter
	@Getter
	private BigInteger productId;

	public EmsProductInstrumentEmbedded() {
	}

	public EmsProductInstrumentEmbedded(BigInteger instrumentId, BigInteger productId) {
		this.instrumentId = instrumentId;
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instrumentId == null) ? 0 : instrumentId.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
		EmsProductInstrumentEmbedded other = (EmsProductInstrumentEmbedded) obj;
		if (instrumentId == null) {
			if (other.instrumentId != null)
				return false;
		} else if (!instrumentId.equals(other.instrumentId))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}

}
