package com.sd.assignement1.sharedRegions;

public class Repository {
    //State abreviations

    private final String[] stateAbrv = {"WPLT", "APLH","ALCB","ASTR","ATT","TRT","DTT","EDT","LCP","EAT","BRO","WSD","PKAT","DF","PKDT","DB"};

    private File f;
    private PrintWriter pw;

    // Passenger
    private States[] passengerState;
    private String situation;
    private int numberOfBagsPassenger;
    private int numberOfCurrentBags;

    //BusDriver
    private States busDriverState;
    private int passengerInTheBus;

    //Porter
    private States porterState;

    //Plane
    private int planeId;
    private int currentBags;

    //ArrivalTerminalTQuay
    private int waitingArrivalTerminalTQuay;

    //BaggageCollectionPoint
    private int numberOfBagsPoint;

    private int numberOfBagsTemp = 0;

    public Repository() throws FileNotFoundException {
        f = new File(Airport.filename);
        pw = new PrintWriter(f);


        passengerState = new States[Airport.N];
        for (int i = 0 ; i < Airport.N; i++)
            passengerState[i] = States.WSD;
        situation = "";
        numberOfBagsPassenger = 0;
        numberOfCurrentBags = 0;

        busDriverState = new States.PKAT;
        passengerInTheBus = 0;

        porterState = new States.WPTL;
        planeId = 0;
        currentBags = 0;
        waitingArrivalTerminalTQuay = 0;
        numberOfBagsPoint = 0;

    }
    public synchronized void setPorterState(States porterState){
        if (this.porterState != porterState) {
            this.porterState = porterState;
            export();
        }
    }

    public synchronized void setBusDriverState(States busDriverState){
        if (this.busDriverState != busDriverState) {
            this.busDriverState = busDriverState;
            export();
        }
    }


    public synchronized void setPassengerState(int id, States passengerState, boolean export){
        if (passengerState[id]!=passengerState){
            passengerState[id]=passengerState;
            if(export)
                export;
        }
    }
    /**
     * Increment number of Bags Passagenger
     */
    public synchronized void inNumberOfBagsPassenger(int numberOfBagsPassenger) {
        numberOfBagsPassenger++;
        //Reavaliar
        setPassengerState(numberOfBagsPassenger, States.RECEPTION, true);
    }


    /**
     * Decrement number of Bags Passagenger
     */
    public synchronized void inNumberOfBagsPassenger(int numberOfBagsPassenger) {
        numberOfBagsPassenger--;
        //Reavaliar
        setPassengerState(numberOfBagsPassenger, States.RECEPTION, true);
    }

    /**
     * Prints the internal state and also saves it to a file.
     */
    private void export() {
        String output = getInternalState();
        System.out.println(output);
        pw.write(output);
        pw.flush();
    }

    /**
     * Builds the string that represents the internal state.
     * @return internal state of the problem as a string
     */

    private String getInternalState(){

    }





}
