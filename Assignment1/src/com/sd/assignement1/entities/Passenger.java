package com.sd.assignement1.entities;

import com.sd.assignement1.sharedRegions.*;

/**
 * This datatype implements the Passenger thread.
 * Have 4 options
 * 1 option
 * 1 - at the disembarking zone
 * 1.2 at the arrival transfer terminal
 * 1.3 terminal transfer
 * 1.4 at the departure transfer terminal
 * 1.5 entering the departure terminal
 *
 * 2 option
 * 2 - at the disembarking zone
 * 2.1 - at the luggage collection point
 * 2.2 - at the baggage reclaim office
 * 2.3 - exiting the arrival terminal
 *
 * 3  option
 * 3 - at the disembarking zone
 * 3.1 - at the luggage collection point
 * 3.2 - exiting the arrival terminal
 *
 *  * 4  option
 *  * 4 - at the disembarking zone
 *  * 4.1 - exiting the arrival terminal
 */

public class Passenger extends Thread{

    /**
     * if ended and exit of Airport
     */
    private boolean ended;

    /**
     * Passenger id
     */
    private int id;

    /**
     * Repository
     */
    private Repository repo;

    /**
     * Array of bags
     */
    private Bag[] bags;

    /**
     * Situation if the passenger going to exit or anothe fly
     */
    private String situation;

    /**
     * Passenger state
     */
    private States state;

    /**
     * Number of Bags
     */
    private int numBags;

    /**
     * Number of current bags with the passenger
     */
    private int currBags;

    /**
     * Plane of Passenger
     */
    private Plane plane;

    /**
     * Arrival Lounge
     */
    private ArrivalLounge lounge;
    /**
     * Arrival Terminal of Exit
     */
    private ArrivalTerminalExit arrivalExit;
    /**
     * Baggage Collection Point
     */
    private BaggageCollectionPoint baggageCollection;
    /**
     * Baggage Reclaim Office
     */
    private BaggageReclaimOffice baggageReclaim;
    /**
     * Departure Terminal Entrance
     */
    private DepartureTerminalEntrance departureEntrance;
    /**
     * Departure Termminal Quay
     */
    private DepartureTerminalTQuay DQuay;
    /**
     * Arrival Terminal Quay
     */
    private ArrivalTerminalTQuay arrivalQuay;

    /**
     *  Passenger instantiation
     * @param id  Identifier of passenger
     * @param repo Repositoru
     * @param bags  Array of Bags
     * @param situation Situation of the passenger
     * @param state State of the passenger
     * @param numBags Number of bags that passenger have
     * @param lounge Arrival Lounge
     * @param arrivalExit Arrival Terminal Exit
     * @param baggageCollection Baggage Collection Point
     * @param baggageReclaim Baggage Reclaim Office
     * @param departureEntrance Departure Terminal Entrance
     * @param DQuay Departure Terminal Quay
     * @param arrivalQuay Arrival Terminal Quay
     */
    public Passenger(int id, Repository repo, Bag[] bags, String situation, States state, int numBags,
                     ArrivalLounge lounge,
                     ArrivalTerminalExit arrivalExit, BaggageCollectionPoint baggageCollection,
                     BaggageReclaimOffice baggageReclaim, DepartureTerminalEntrance departureEntrance,
                     DepartureTerminalTQuay DQuay, ArrivalTerminalTQuay arrivalQuay){
        this.id = id;
        this.repo = repo;
        this.bags = bags;
        this.situation = situation;
        this.state = state;
        this.numBags = numBags;
        this.currBags = 0;
        this.lounge = lounge;
        this.arrivalExit = arrivalExit;
        this.baggageCollection = baggageCollection;
        this.baggageReclaim = baggageReclaim;
        this.departureEntrance = departureEntrance;
        this.DQuay = DQuay;
        this.arrivalQuay = arrivalQuay;
        this.ended = false;


    }

    /**
     * Set the state of Passenger
     * @param state
     */
    public void setState(States state){
        this.state = state;
    }

    /**
     * Get the bags of the passenger
     * @return
     */

    public Bag[] getBags(){
        return bags;
    }

    /**
     * Get the number of bags that passenger have
     * @return
     */

    public int getNumBags(){
        return numBags;
    }

    /**
     * Get the Situation of passenger if need to take another plane of is going to the exit terminal
     * @return
     */

    public String getSituation(){
        return situation;
    }

    /**
     * Set plane of the passenger
     * @param plane
     */
    public void setPlane(Plane plane){
        this.plane = plane;
    }

    /**
     * Decide where the passenger need to go depend of the atual state
     * @param s
     */
    private void goTo(States s){
        switch (state){
            case WSD:
                System.out.println("Wsd");
                try{
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {}
                lounge.enter(this);
                break;
            case ATT:
                System.out.println("att");
                arrivalQuay.enter(this);
                break;
            case TRT:
                System.out.println("trt");
                while (!DQuay.busArrived()){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                goTo(States.DTT);
                break;
            case DTT:
                System.out.println("dtt");
                //DQuay.enter(this);
                goTo(States.EDT);
                break;
            case LCP:
                System.out.println("lcp");
                baggageCollection.enter(this);
                break;
            case BRO:
                System.out.println("bro");
                baggageReclaim.enter(this);
                break;
            case EAT:
                System.out.println("eat");
                arrivalExit.enter(this);
                break;
            case EDT:
                System.out.println("edt");
                departureEntrance.enter(this);
                ended = true;
                break;
        }
    }

    /**
     * Passenger lifecycle
     */
    @Override
    public void run(){
        System.out.println("Passenger running");
        while(!ended){

            while(!plane.landed()){
                System.out.println("waiting for plane to land");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };

            plane.leave();

            goTo(States.WSD);
            boolean skip = false;

            switch (state){
                case WSD:
                    if(situation.equalsIgnoreCase("TRT")){
                        goTo(States.ATT);
                    }
                    else{
                        if(numBags>0){
                            goTo(States.LCP);
                        }
                        else{
                            goTo(States.EAT);
                        }
                    }
                    break;
                case ATT:
                    arrivalQuay.enterQueue();
                    goTo(States.TRT);
                    break;
                case TRT:
                    ///
                    while(!DQuay.haveILeft(this)){
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case DTT:
                    goTo(States.EDT);
                    break;
                case LCP:
                    while (currBags!=numBags || skip)
                    for(Bag b : bags){
                        Bag abc = baggageCollection.getBag(b.getId());
                        if(abc.getOwner()>0){
                            currBags++;
                            b.setCollected();
                        }
                        else{
                            goTo(States.BRO);
                            skip = true;

                        }
                    }
                    //verify if any bag did not get collected;
                    break;
                case BRO:
                    for (Bag b: bags){
                        if(!b.getCollected()){
                            baggageReclaim.reclaim(b);
                        }
                    }
                    goTo(States.EAT);
                    break;
                case EAT:
                    ended = true;
                    break;
            }

        }
    }
}
