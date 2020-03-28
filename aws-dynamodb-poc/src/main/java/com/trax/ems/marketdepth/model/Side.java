/**
 * 
 */
package com.trax.ems.marketdepth.model;

/**
 * @author asonu
 *
 */
public enum Side {

    BID("B"),
    OFFER("O"),
    SELL("B"),
    BUY("O");

    private final String name;

    private Side(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
