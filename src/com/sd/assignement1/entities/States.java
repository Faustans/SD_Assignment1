package com.sd.assignement1.entities;

/**
 * State
 */
public enum States {

    /**
     * Porter Waiting for a plane to land.
     */
    WPTL("Waiting for a plane to land"),

    /**
     * Porter At the planes Hold.
     */
    APLH ("At the planes Hold"),

    /**
     * Porter At the planes Hold.
     */
    ALCB ("At the planes Hold"),

    /**
     * Porter At the Storage Room.
     */
    ASTR ("At the Storage Room");

    /**
     *
     */
    PKAT("Parking at the Arrival terminal");

    /**
     *
     */
    DF("Driving Forward");

    /**
     *
     */
    PKDT("Parking at the departure Terminal");

    /**
     *
     */
    DB("Driving Back");

    private final String description;

    private States(String description){
        this.description = description;
    }

    @Override
    public String toString(){
        return this.description;
    }
}
