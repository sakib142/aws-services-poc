package com.trax.ems.security.datasource.ems.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.Data;

@Entity(name = "EmsInstrumentVenue")
@Table(name = "EMS_INST_SEC_VENUE")
@Data
@Audited
public class EmsInstrumentVenue {

	@EmbeddedId
	private EmsInstrumentVenueEmbedded id;

	@ManyToOne
	@MapsId("emsInstrumentId")
	@JoinColumn(name = "INST_ID", referencedColumnName = "ID")
	private EmsInstrument emsInstrument;

	@ManyToOne
	@MapsId("emsVenueId")
	@JoinColumn(name = "VENUE_ID", referencedColumnName = "ID")
	private EmsVenue emsVenue;
}
