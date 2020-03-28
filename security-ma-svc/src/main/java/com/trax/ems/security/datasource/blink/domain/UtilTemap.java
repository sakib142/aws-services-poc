package com.trax.ems.security.datasource.blink.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity(name = "UtilTemap")
@Table(name = "UTIL_TEMAP")
@Data
public class UtilTemap {

	@EmbeddedId
	private UtilTemapEmbedded id;

	@Column(name = "SHORTSTRING")
	private String shortString;

	@Column(name = "LONGSTRING")
	private String longString;

	@Column(name = "VISIBLE")
	private String visible;

	@Column(name = "ISDELETED")
	private String isDeleted;

}
