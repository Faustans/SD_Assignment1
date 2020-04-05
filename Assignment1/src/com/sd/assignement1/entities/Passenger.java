package com.sd.assignement1.entities;

import com.sd.assignement1.sharedRegions.*;

/**
 * This datatype implements the Passenger thread.
 * In his lifecycle, the passenger breaks his car,
 * gets (or not) a replacement car, and gets his
 * car fixed. <p>
 * He follows the following order:
 * <ul>
 * <li> Uses his car until it breaks down;
 * <li> Goes to repair shop and queues in;
 * <li> Asks (or not) the manager for a replacement car;
 * <li> Goes back to work with a replacement car or by bus;
 * <li> After the manager calls, the customer goes back to the shop;
 * <li> Finally the customer pays for the service, and gets the car back.
 * </ul>
 */

public class Passenger extends Thread{

    private boolean ended;

    private int id;

    private Repository repo;

    private Bag[] bags;

    private String situation;

    private States state;

    private int numBags;

    private int currBags;

    private Plane plane;

    private ArrivalLounge lounge;
    private ArrivalTerminalExit arrivalExit;
    private BaggageCollectionPoint baggageCollection;

    private BaggageReclaimOffice baggageReclaim;
    private DepartureTerminalEntrance departureEntrance;
    private DepartureTerminalTQuay DQuay;
    private ArrivalTerminalTQuay arrivalQuay;


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


    public void setState(States state){
        this.state = state;
    }

    public Bag[] getBags(){
        return bags;
    }

    public int getNumBags(){
        return numBags;
    }

    public String getSituation(){
        return situation;
    }

    public void setPlane(Plane plane){
        this.plane = plane;
    }

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
