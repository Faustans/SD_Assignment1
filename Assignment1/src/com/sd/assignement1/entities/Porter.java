package com.sd.assignement1.entities;


import com.sd.assignement1.mainProgram.*;
import com.sd.assignement1.sharedRegions.*;

/**
 * This datatype implements the Porter thread.
 * 1 option
 *
 * 1 - waiting for a plane to land ( Can be repeated many times)
 * 1.2 - at the plane's hold
 * 1.3 at the luggage conveyor belt
 *
 *
 * 2 option
 *  * 1 - waiting for a plane to land ( Can be repeated many times)
 *  * 1.2 - at the plane's hold
 *  * 1.3 at the storeroom
 */

public class Porter extends Thread{

    /**
     * state of Porter
     */
    private States state;

    /**
     * Repository
     */
    private Repository repo;

    /**
     * Bag
     */
    private Bag bag;

    /**
     * Plane
     */
    private Plane plane;
    /**
     * baggage Collection Point
     */
    private BaggageCollectionPoint baggageCollectionPoint;

    /**
     * Temporary Stroge Area
     */
    private TemporaryStorageArea temporaryStorageArea;

    /**
     * Porter instantiation
     * @param repo Repository
     * @param state State of Porter
     * @param p Plane that land
     * @param baggageCollectionPoint Baggage Collection Point
     * @param temporaryStorageArea Temporary Storage area
     */
    public Porter(Repository repo, States state, Plane p, BaggageCollectionPoint baggageCollectionPoint, TemporaryStorageArea temporaryStorageArea){
        this.state = state;
        this.repo = repo;
        this.plane = p;
        this.baggageCollectionPoint = baggageCollectionPoint;
        this.temporaryStorageArea = temporaryStorageArea;

    }

    /**
     * Set Porter State
     * @param state
     */

    public void setPorterState(States state){
        this.state = state;
    }

    /**
     * Get Porter State
     * @return
     */
    public States getPorterState(){
        return state;
    }

    /**
     * Set Plane tha land
     * @param p
     */
    public void setPlane(Plane p){
        this.plane = p;
    }

    /**
     * Guide of Porter
     * @param s
     */
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

<<<<<<< HEAD
    /**
     * Life cycle of Porter
     */
=======
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

>>>>>>> 902cd58e893d4c56c6e4a7ca0f3b3ac796346df8
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
