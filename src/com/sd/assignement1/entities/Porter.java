package com.sd.assignement1.entities;


import com.sd.assignement1.mainProgram.Airport;
import com.sd.assignement1.sharedRegions.Repository;

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

    private Bag bags[];

    private Plane plane[Airport.P];

    public Porter(Repository repo, States state){
        this.state = state;
        this.repo = repo;

    }

    public void setPorterState(States state){
        this.state = state;
    }

    public States getPorterState(){
        return state;
    }

    @Override
    public void run(){
        boolean ended = false;
        while(!ended){
            switch (state){
                case WPTL:
                    if(plane[current].empty()){
                       while(Plane.numBags()>0){

                       }
                    }
                    try{
                        Thread.sleep(50);
                    }
                    catch (InterruptedException e) {}

                    break;
                case APLH:
                    break;
                case ALCB:
                    break;
                case ASTR:
                    break;
            }


        }

    }

}
