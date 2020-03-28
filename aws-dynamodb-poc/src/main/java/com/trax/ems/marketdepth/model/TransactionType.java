package com.trax.ems.marketdepth.model;

import java.util.HashMap;
import java.util.Map;

public enum TransactionType {
    
    NEW("N"), 
    UPDATE("U"), 
    DELETE("D"), 
    DONE("DONE"), 
    REPLACE("R"), 
    CANCEL("C"), 
    CORRECTED("CORRECTED");

    private final String name;
    
    private static Map<String, TransactionType> map = new HashMap<String, TransactionType>();

    static {
        for (TransactionType txnType : TransactionType.values()) {
            map.put(txnType.name, txnType);
        }
    }

    private TransactionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TransactionType findByName(String name) {
        return map.get(name);
    }
}
