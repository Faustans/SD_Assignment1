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
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {}
                state = States.WPTL;
                break;
            case APLH:
                try{
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {}
                state = States.APLH;
                break;
            case ALCB:
                try{
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {}
                state = States.ALCB;
                break;
            case ASTR:
                try{
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {}
                state = States.ASTR;
                break;
        }
    }

    /**
     * Life cycle of Porter
     */
    @Override
    public void run(){
        boolean ended = false;
        while(!ended){
            switch (state){
                case WPTL:
                    System.out.println("wptl");
                    if(this.plane.landed()){
                        state = States.APLH;
                    }
                    else{
                        try{
                            Thread.sleep(2500);
                            plane.setLanded(true);
                        }
                        catch (InterruptedException e) {}

                    }
                    break;
                case APLH:
                    System.out.println("aplh");
                    if(plane.empty()){
                        if(Plane.hasBags()){
                            bag = Plane.getBag();
                            if(bag.getSituation().equals("TRT")){
                                goTo(States.ASTR);
                            }
                            else{
                                goTo(States.ALCB);
                            }
                        }
                    }
                    else{
                        try{
                            Thread.sleep(50);
                        }
                        catch (InterruptedException e) {}

                    }
                    break;
                case ALCB:
                    System.out.println("alcb");
                    baggageCollectionPoint.addBag(bag);
                    goTo(States.APLH);
                    break;
                case ASTR:
                    temporaryStorageArea.addBag(bag);
                    goTo(States.APLH);
                    break;
            }


        }

    }

}
