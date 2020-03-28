package com.trax.ems.security.datasource.blink.domain;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity(name = "InstrumentProductProtocols")
@Table(name = "INSTRUMENT_PRODUCTPROTOCOLS")
@Data
public class InstrumentProductProtocols {

	@Id
	@Column(name = "ID")
	private BigInteger id;

	@Column(name = "INQUIRYTYPE")
	private String inquiryType;

	@Column(name = "PRICETYPE")
	private String priceType;

	@Column(name = "TRANSACTIONTYPE")
	private String transactionType;

	@Column(name = "FORCERANK")
	private BigInteger forceRank;

	@ManyToOne
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	@JsonBackReference
	private InstrumentProduct instrumentProducts;

	@ManyToOne
	@JoinColumn(name = "PROTOCOLID", referencedColumnName = "TRADINGPROTOCOLID")
	private InquiryTradingProtocol inquiryTradingProtocol;
}
