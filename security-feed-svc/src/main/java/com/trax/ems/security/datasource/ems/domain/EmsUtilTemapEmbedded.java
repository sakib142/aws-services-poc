package com.trax.ems.security.datasource.ems.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class EmsUtilTemapEmbedded implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7326595267902013460L;

	@Column(name = "ID")
	@Getter
	@Setter
	private BigInteger utilId;

	@Column(name = "TYPE")
	@Getter
	@Setter
	private BigInteger type;

	@SuppressWarnings("unused")
	public EmsUtilTemapEmbedded() {
	}

	public EmsUtilTemapEmbedded(BigInteger utilId, BigInteger type) {
		this.utilId = utilId;
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		EmsUtilTemapEmbedded that = (EmsUtilTemapEmbedded) o;
		return Objects.equals(utilId, that.utilId) && Objects.equals(type, that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(utilId, type);
	}

}
