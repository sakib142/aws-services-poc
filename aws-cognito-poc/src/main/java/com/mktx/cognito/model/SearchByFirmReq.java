package com.mktx.cognito.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchByFirmReq {
    
    private String poolId;
    private String firmName;

}
