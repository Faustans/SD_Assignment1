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

    public int myId(){
        return id;
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
