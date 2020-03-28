package com.trax.ems.security.datasource.ems.domain;

import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "EmsProductProtocols")
@Table(name = "EMS_PRODUCTPROTOCOLS")
@Getter
@Setter
@Audited
public class EmsProductProtocols {

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

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "PRODUCTID", referencedColumnName = "ID") })
	@JsonBackReference(value = "EmsProductProtocols")
	private EmsProduct instrumentProducts;

	@ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "PROTOCOLID", referencedColumnName = "TRADINGPROTOCOLID")
	private EmsInquiryTradingProtocol inquiryTradingProtocol;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((forceRank == null) ? 0 : forceRank.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inquiryTradingProtocol == null) ? 0 : inquiryTradingProtocol.hashCode());
		result = prime * result + ((inquiryType == null) ? 0 : inquiryType.hashCode());
		// result = prime * result
		// + ((instrumentProductInstruments == null) ? 0 :
		// instrumentProductInstruments.hashCode());
		result = prime * result + ((priceType == null) ? 0 : priceType.hashCode());
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
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
		EmsProductProtocols other = (EmsProductProtocols) obj;
		if (forceRank == null) {
			if (other.forceRank != null)
				return false;
		} else if (!forceRank.equals(other.forceRank))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inquiryTradingProtocol == null) {
			if (other.inquiryTradingProtocol != null)
				return false;
		} else if (!inquiryTradingProtocol.equals(other.inquiryTradingProtocol))
			return false;
		if (inquiryType == null) {
			if (other.inquiryType != null)
				return false;
		} else if (!inquiryType.equals(other.inquiryType))
			return false;
		/*
		 * if (instrumentProductInstruments == null) { if
		 * (other.instrumentProductInstruments != null) return false; } else if
		 * (!instrumentProductInstruments.equals(other.instrumentProductInstruments))
		 * return false;
		 */
		if (priceType == null) {
			if (other.priceType != null)
				return false;
		} else if (!priceType.equals(other.priceType))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}
}
