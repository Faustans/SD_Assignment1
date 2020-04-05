package com.sd.assignement1.sharedRegions;

import com.sd.assignement1.entities.States;
import com.sd.assignement1.mainProgram.Airport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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

        busDriverState = States.PKAT;
        passengerInTheBus = 0;

        porterState =  States.WPTL;
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


    public synchronized void setPassengerState(int id, States pState, boolean export){
        if (passengerState[id]!=pState){
            passengerState[id]=pState;
            export();
        }
    }

    public synchronized void setPlaneState(int fn, int bn){

    }

    /**
     * Increment number of Bags Passagenger
     */
    public synchronized void inNumberOfBagsPassenger(int numberOfBagsPassenger, States st) {
        numberOfBagsPassenger++;
        //Reavaliar
        setPassengerState(numberOfBagsPassenger, st, true);
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
        String str = stateAbrv[managerState.ordinal()] + "  ";
        for (int i = 0; i < RepairShop.M; i++) {
            str += String.format("%s ", stateAbrv[mechanicsState[i].ordinal()]);
        }

        for (int i = 0; i < RepairShop.N; i++) {
            str += String.format(" %s %02d %s %s ", stateAbrv[customerStates[i].ordinal()], currentCarId[i],
                    booleanToStr(wantRental[i]), booleanToStr(carRepaired[i]));

            if ((i+1) % 10 == 0)
                str += "\n              ";
        }

        str += String.format("  %02d %02d %02d", customerQueue, waitingForKey, finishedJobs);
        str += String.format("                  %02d  %02d", customerCars, replacementCars);
        str += String.format("          %02d ", reqCount);

        for (int i = 0; i < RepairShop.K; i++) {
            str += String.format("  %02d  %02d  %s ", stock[i], pendingVehicles[i], booleanToStr(managerAlerted[i]));
        }

        str += "               ";

        for (int i = 0; i < RepairShop.K; i++) {
            str += String.format("  %02d", soldParts[i]);
        }

        return str + "\n\n";
    }


}
