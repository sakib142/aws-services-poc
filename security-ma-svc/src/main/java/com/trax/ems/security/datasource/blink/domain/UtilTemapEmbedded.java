package com.trax.ems.security.datasource.blink.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class UtilTemapEmbedded implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1733051239712004455L;

	@Column(name = "ID")
    @Getter
    @Setter
    private BigInteger utilId;

    @Column(name = "TYPE")
    @Getter
    @Setter
    private BigInteger type;

    @SuppressWarnings("unused")
    public UtilTemapEmbedded() {
    }

    public UtilTemapEmbedded(BigInteger utilId, BigInteger type) {
        this.utilId = utilId;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilTemapEmbedded that = (UtilTemapEmbedded) o;
        return Objects.equals(utilId, that.utilId) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utilId, type);
    }
}
