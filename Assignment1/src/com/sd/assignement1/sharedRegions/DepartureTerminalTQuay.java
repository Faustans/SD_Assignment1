package com.sd.assignement1.sharedRegions;

import com.sd.assignement1.entities.Passenger;
import com.sd.assignement1.entities.States;

import java.util.PriorityQueue;
import java.util.Queue;
/**
 *  This datatype  implements the Departure Terminal Transfer Quay shared memory region
 * On the terminal transfer quay, the passengers leave the bus arrival, which leave them in
 * departure terminal for the next leg of the journey;
 */

public class DepartureTerminalTQuay {
    /**
     * Repository
     */
    private static Repository repo;
    /**
     * Boolean that confirm is in the Departure Terminal Quay
     */
    private static boolean busHere;
    /**
     * Queue of the person that leave the bus to Departure Terminal Quay
     */
    private static Queue<Passenger> queue = new PriorityQueue<>(100);

    /**
     * Departure Terminal Quay instantion
     * @param repo
     */
    public DepartureTerminalTQuay(Repository repo){
        this.repo = repo;
        this.busHere = false;
    }

    /**
     * Passenger that enter in Departure Terminal Transfer Quay
     * @param p
     */
    public synchronized void queueIn(Passenger p){
        queue.add(p);
        notifyAll();
    }

    /**
     *  Notifie if the bus arrived or not
     * @return
     */
    public synchronized boolean busArrived(){
        if(this.busHere){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Confirm if the Passenger p leave the bus or not
     * @param p
     * @return
     */
    public synchronized boolean haveILeft(Passenger p){
        return queue.contains(p);
    }
}
