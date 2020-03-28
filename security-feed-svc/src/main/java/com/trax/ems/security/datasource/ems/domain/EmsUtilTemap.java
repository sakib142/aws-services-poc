package com.trax.ems.security.datasource.ems.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.trax.ems.security.datasource.ems.domain.EmsUtilTemap;
import com.trax.ems.security.datasource.ems.domain.EmsUtilTemapEmbedded;

import lombok.Data;

@Entity(name = "EmsUtilTemap")
@Table(name = "EMS_UTIL_TEMAP")
@Data
public class EmsUtilTemap {

	@EmbeddedId
	private EmsUtilTemapEmbedded id;

	@Column(name = "SHORTSTRING")
	private String shortString;

	@Column(name = "LONGSTRING")
	private String longString;

	@Column(name = "VISIBLE")
	private String visible;

	@Column(name = "ISDELETED")
	private String isDeleted;

}
