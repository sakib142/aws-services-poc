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

import lombok.Getter;
import lombok.Setter;

@Entity(name = "EmsVenue")
@Table(name = "EMS_VENUE")
@Getter
@Setter
@Audited
public class EmsVenue {

	@Id
	@Column(name = "ID")
	private BigInteger id;

	@Column(name = "NAME")
	private String name;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "emsVenue", cascade = CascadeType.ALL)
	private Set<EmsInstrumentVenue> emsInstrumentVenue = new HashSet<>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		EmsVenue other = (EmsVenue) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
