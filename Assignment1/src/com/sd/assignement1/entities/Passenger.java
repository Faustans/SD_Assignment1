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

<<<<<<< HEAD
    /**
     * Get the bags of the passenger
     * @return
     */
=======
    public int myId(){
        return id;
    }
>>>>>>> 902cd58e893d4c56c6e4a7ca0f3b3ac796346df8

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

<<<<<<< HEAD
    /**
     * Decide where the passenger need to go depend of the atual state
     * @param s
     */
=======
    public synchronized void waitToArrive(){
        while (!DQuay.busArrived()){
            try {
                synchronized (DQuay){
                    DQuay.wait();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

>>>>>>> 902cd58e893d4c56c6e4a7ca0f3b3ac796346df8
    private void goTo(States s){
        switch (s){
            case WSD:
                System.out.println("Wsd");
                setState(States.WSD);
                break;
            case ATT:
                System.out.println("att");
                setState(States.ATT);
                break;
            case TRT:
                System.out.println("trt");
                waitToArrive();
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
                setState(States.LCP);
                break;
            case BRO:
                System.out.println("bro");
                baggageReclaim.enter(this);
                setState(States.BRO);
                break;
            case EAT:
                System.out.println("eat");
                arrivalExit.enter(this);
                setState(States.EAT);
                break;
            case EDT:
                System.out.println("edt");
                departureEntrance.enter(this);
                ended = true;
                break;
        }
    }

<<<<<<< HEAD
    /**
     * Passenger lifecycle
     */
=======
    public synchronized void callWaitPlane() throws InterruptedException {
        while(!plane.landed()) {
            System.out.println("Waiting");
            synchronized (plane){
                plane.wait();
            }

        }
    }
    public synchronized void waitILeft() {
        while (!DQuay.haveILeft(this)) {
            try {
                synchronized (DQuay) {
                    DQuay.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


>>>>>>> 902cd58e893d4c56c6e4a7ca0f3b3ac796346df8
    @Override
    public void run(){
        System.out.println("Passenger running");
        boolean leftPlane = false;
        boolean skip = false;

        while(!ended){

            if (!leftPlane){
                System.out.println("waiting for plane to land");
                try {
                    callWaitPlane();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                plane.leave();
                leftPlane = true;
                goTo(States.WSD);

            }


            switch (state){
                case WSD:
                    System.out.println("Wsd");
                    if(situation.equalsIgnoreCase("TRF")){
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
                case ATT:System.out.println("ATT");
                    arrivalQuay.enterQueue();
                    goTo(States.TRT);
                    break;
                case TRT:
                    ///
                    waitILeft();
                    goTo(States.DTT);
                    break;
                case DTT:
                    goTo(States.EDT);
                    break;
                case LCP:
                    int counter = 0;
                    while (currBags!=numBags) {
                        counter++;
                        skip = false;
                        if(counter == 100)
                            break;
                        for (Bag b : bags) {
                            Bag abc = baggageCollection.getBag(b.getId());
                            if (abc.getOwner() > 0) {
                                currBags++;
                                b.setCollected();
                            } else {
                                skip = true;
                            }
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(counter == 1000 && skip){
                        goTo(States.BRO);
                    }else{
                        goTo(States.EAT);
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
