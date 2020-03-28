package com.trax.ems.security.datasource.blink.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity(name = "InstrumentProductInstruments")
@Table(name = "INSTRUMENT_PRODUCTINSTRUMENTS")
@Getter
@Setter
public class InstrumentProductInstruments implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8677576961619315540L;

    @EmbeddedId
    private InstrumentProductEmbedded id;


    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("instrumentId")
    @JoinColumn(name = "INSTRUMENTID", referencedColumnName = "ID")
    @JsonBackReference
    private InstrumentMasterSecurity instrumentMasterSecurity;


    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("productId")
    @JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
    @JsonBackReference
    private InstrumentProduct instrumentProduct;

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
        InstrumentProductInstruments other = (InstrumentProductInstruments) obj;
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

}
