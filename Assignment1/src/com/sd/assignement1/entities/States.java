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
     * Porter At the Luggage Conveyor Belt.
     */
    ALCB ("At the Luggage Conveyor Belt"),

    /**
     * Porter At the Storage Room.
     */
    ASTR ("At the Storage Room"),

    /**
     * Passenger at the arrival transfer terminal.
     */
    ATT ("At the arrival transfer terminal"),

    /**
     * Passenger inside the bus.
     */
    TRT ("Inside the bus"),

    /**
     * Passenger at the departure transfer terminal.
     */
    DTT ("At the departure transfer terminal"),

    /**
     * Passenger entering the departure terminal.
     */
    EDT ("entering the departure terminal."),

    /**
     * Passenger at the luggage collection point.
     */
    LCP ("at the luggage collection point."),

    /**
     * Passenger exiting the arrival terminal.
     */
    EAT ("exiting the arrival terminal"),

    /**
     * Passenger at the baggage reclaiming office.
     */
    BRO ("at the baggage reclaiming office"),

    /**
     * Passenger What should i do?.
     */
    WSD ("What should I do?"),

    /**
     * BusDriver Parking at the Arrival terminal
     */
    PKAT("Parking at the Arrival terminal"),

    /**
     * BusDriver Driving Forward
     */
    DF("Driving Forward"),

    /**
     * BusDriver Parking at the departure Terminal
     */
    PKDT("Parking at the departure Terminal"),

    /**
     * BusDriver Driving Back
     */
    DB("Driving Back");

    private final String description;

    /**
     *
     * @param description
     */
    private States(String description){
        this.description = description;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return this.description;
    }
}
