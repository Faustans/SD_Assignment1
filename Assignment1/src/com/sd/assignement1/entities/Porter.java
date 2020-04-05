package com.sd.assignement1.entities;


import com.sd.assignement1.mainProgram.*;
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

public class Porter extends Thread{

    private States state;

    private Repository repo;

    private Bag bag;

    private Plane plane;

    private BaggageCollectionPoint baggageCollectionPoint;

    private TemporaryStorageArea temporaryStorageArea;

    public Porter(Repository repo, States state, Plane p, BaggageCollectionPoint baggageCollectionPoint, TemporaryStorageArea temporaryStorageArea){
        this.state = state;
        this.repo = repo;
        this.plane = p;
        this.baggageCollectionPoint = baggageCollectionPoint;
        this.temporaryStorageArea = temporaryStorageArea;

    }

    public void setPorterState(States state){
        this.state = state;
    }

    public States getPorterState(){
        return state;
    }

    public void setPlane(Plane p){
        this.plane = p;
    }

    private void goTo(States s) {
        switch (s) {
            case WPTL:
                try{
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {}
                state = States.WPTL;
                break;
            case APLH:

                state = States.APLH;
                break;
            case ALCB:

                state = States.ALCB;
                break;
            case ASTR:

                state = States.ASTR;
                break;
        }
    }

    public synchronized void waitForEmptyPlane(){
        synchronized (plane){
            while(!plane.empty()){
                try {
                    plane.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run(){
        boolean ended = false;
        while(!ended){
            switch (state){
                case WPTL:
                    System.out.println("wptl");
                    if(plane.landed()){
                        state = States.APLH;
                    }
                    else{
                        try{
                            Thread.sleep(250);
                            plane.setLanded(true);
                        }
                        catch (InterruptedException e) {}

                    }
                    break;
                case APLH:
                    System.out.println("APLH");
                    while(!plane.empty()){
                        waitForEmptyPlane();
                    }
                    if(plane.hasBags()){
                        bag = plane.getBag();
                        if(bag.getSituation().equals("TRT")){
                            goTo(States.ASTR);
                        }
                        else{
                            goTo(States.ALCB);
                        }
                    }else {
                        goTo(States.WPTL);
                    }

                    break;
                case ALCB:
                    System.out.println("ALCB");
                    baggageCollectionPoint.addBag(bag);
                    goTo(States.APLH);
                    break;
                case ASTR:
                    System.out.println("ASTR");
                    temporaryStorageArea.addBag(bag);
                    goTo(States.APLH);
                    break;
            }


        }

    }

}
