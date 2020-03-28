package com.trax.ems.security.datasource.ems.domain;

import java.math.BigInteger;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.Data;

@Entity(name = "EmsBatchExecution")
@Table(name = "EMS_BATCH_EXECUTION")
@Data
@Audited
public class EmsBatchExecution {

	@Id
	@Column(name = "BATCH_NAME")
	private String batchName;

	@Column(name = "EXECUTION_START_TIME")
	private Instant executionStartTime;

	@Column(name = "EXECUTION_END_TIME")
	private Instant executionEndTime;

	@Column(name = "ROWS_PROCESSED")
	private BigInteger noOfInstrumentsProcessed;
}
