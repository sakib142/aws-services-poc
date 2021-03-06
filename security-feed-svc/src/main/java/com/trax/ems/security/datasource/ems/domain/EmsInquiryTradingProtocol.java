package com.trax.ems.security.datasource.ems.domain;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "EmsInquiryTradingProtocol")
@Table(name = "EMS_INQUIRY_TRADINGPROTOCOL")
@Getter
@Setter
@Audited
@AuditTable(value = "EMS_INQUIRY_TRADPROTOCOL_AUD")
public class EmsInquiryTradingProtocol {

	@Id
	@Column(name = "TRADINGPROTOCOLID")
	private BigInteger tradingProtocolId;

	@Column(name = "DESCRIPTION")
	private String protocolDesc;

	@Column(name = "SHORTNAME")
	private String protocolShortName;

	@Column(name = "APINAME")
	private String apiName;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apiName == null) ? 0 : apiName.hashCode());
		result = prime * result + ((protocolDesc == null) ? 0 : protocolDesc.hashCode());
		result = prime * result + ((protocolShortName == null) ? 0 : protocolShortName.hashCode());
		result = prime * result + ((tradingProtocolId == null) ? 0 : tradingProtocolId.hashCode());
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
		EmsInquiryTradingProtocol other = (EmsInquiryTradingProtocol) obj;
		if (apiName == null) {
			if (other.apiName != null)
				return false;
		} else if (!apiName.equals(other.apiName))
			return false;
		if (protocolDesc == null) {
			if (other.protocolDesc != null)
				return false;
		} else if (!protocolDesc.equals(other.protocolDesc))
			return false;
		if (protocolShortName == null) {
			if (other.protocolShortName != null)
				return false;
		} else if (!protocolShortName.equals(other.protocolShortName))
			return false;
		if (tradingProtocolId == null) {
			if (other.tradingProtocolId != null)
				return false;
		} else if (!tradingProtocolId.equals(other.tradingProtocolId))
			return false;
		return true;
	}

}
