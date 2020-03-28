package com.trax.ems.marketdata.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
public class ResponseDTO implements Serializable {

    private static final long serialVersionUID = -5096868992780648354L;

    private BigInteger instId;
    
    private List<String> connectionIDs;
}
