package com.sd.assignement1.sharedRegions;

import com.sd.assignement1.entities.Passenger;
import com.sd.assignement1.mainProgram.Airport;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *  This datatype  implements the Arrival Terminal Transfer Quay shared memory region
 * On the terminal transfer quay, the passengers wait for the bus arrival, which will take them to the
 * departure terminal for the next leg of the journey;
 */
public class ArrivalTerminalTQuay {

<<<<<<< HEAD
    /**
     * Queue of passenger waiting to enter in the bus
     */
    private static Queue<Passenger> queue = new PriorityQueue<>();
=======
    private static Queue<Passenger> queue = new ArrayDeque<>();
>>>>>>> 902cd58e893d4c56c6e4a7ca0f3b3ac796346df8

    /**
     * Repository
     */
    private static Repository repo;

    public ArrivalTerminalTQuay(Repository repo){
        this.repo = repo;
    }

    public void enter(Passenger p){

    }

    /**
     * Get passenger of the queue
     * @return
     */
    public synchronized Passenger getPassenger(){
        return queue.remove();
    }

    /**
     * Enter a passenger in the queue
     */
    public synchronized void enterQueue(){
        Passenger p = (Passenger) Thread.currentThread();
        queue.add(p);
        synchronized (this){
            this.notifyAll();
        }


    }

    /**
     * Queue is empty
     * @return
     */
    public synchronized boolean queueEmpty(){
        if(queue.isEmpty()){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Return the size of the queue that is equal the number of persons waiting
     * @return
     */
    public static int waitingPassenger(){
        return queue.size();
    }

}
