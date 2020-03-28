package com.trax.ems.marketdata.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
public class RequestDTO implements Serializable {

    private static final long serialVersionUID = -3087019874909425825L;

    private String method;

    private MarketDepth data;
    
    private String connectionID;
    
    private List<String> existingConnectionIDs;
}
