package com.trax.ems.security.datasource.ems.domain;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "EmsProductInstruments")
@Table(name = "EMS_PRODUCTINSTRUMENTS")
@Getter
@Setter
@Audited
public class EmsProductInstruments implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6330305609956823843L;

	@EmbeddedId
	private EmsProductInstrumentEmbedded id;

	@ManyToOne
	@JoinColumn(name = "INSTRUMENTID", referencedColumnName = "ID")
	@MapsId("instrumentId")
	@JsonBackReference(value = "EmsProductInstruments")
	private EmsInstrument instrumentMasterSecurity;

	@ManyToOne
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	@MapsId("productId")
	@JsonBackReference(value = "EmsProductInstrument")
	private EmsProduct instrumentProduct;

	@Column(name = "ISDEFAULT")
	private Boolean isDefault;

	@Column(name = "PRIORITY")
	private BigInteger priority;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instrumentMasterSecurity == null) ? 0 : instrumentMasterSecurity.hashCode());
		result = prime * result + ((instrumentProduct == null) ? 0 : instrumentProduct.hashCode());
		result = prime * result + ((isDefault == null) ? 0 : isDefault.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
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
		EmsProductInstruments other = (EmsProductInstruments) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instrumentMasterSecurity == null) {
			if (other.instrumentMasterSecurity != null)
				return false;
		} else if (!instrumentMasterSecurity.equals(other.instrumentMasterSecurity))
			return false;
		if (instrumentProduct == null) {
			if (other.instrumentProduct != null)
				return false;
		} else if (!instrumentProduct.equals(other.instrumentProduct))
			return false;
		if (isDefault == null) {
			if (other.isDefault != null)
				return false;
		} else if (!isDefault.equals(other.isDefault))
			return false;
		if (priority == null) {
			if (other.priority != null)
				return false;
		} else if (!priority.equals(other.priority))
			return false;
		return true;
	}

	/*
	 * @OneToMany(fetch = FetchType.EAGER, mappedBy =
	 * "instrumentProductInstruments", cascade = CascadeType.ALL)
	 * 
	 * @JsonManagedReference(value = "ProductInstrument") private
	 * Set<EmsProductProtocols> instrumentProductProtocols = new HashSet<>();
	 */

}
