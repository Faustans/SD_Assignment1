package com.sd.assignement1.sharedRegions;

import com.sd.assignement1.entities.Bag;
import com.sd.assignement1.entities.Passenger;

import java.util.ArrayList;

/**
 *  This datatype  implements the Temporary Storage Area shared memory region
 *
 *  In this region the baggage is Storage temporary waiting for the next fly of the owner
 */


public class TemporaryStorageArea {
    /**
     * Repository
     */
    private  Repository repo;

    /**
     * Array list with all the bags in the Temporary Storage Area
     */
    private ArrayList<Bag> bags;

    /**
     * Number of bags in the Temporary Storage Area
     */
    private int numBags;


    /**
     *  Temporary Storage area instantiation
     * @param repo
     */
    public TemporaryStorageArea(Repository repo) {
        this.repo = repo;
        this.bags = new ArrayList<>();
        this.numBags = 0;
    }

    /**
     * Add a bag ( b ) in Temporary Storage Area
     * @param b
     */
    public synchronized void addBag(Bag b){
        bags.add(b);
        numBags++;
    }

    /**
     * Get the numbers of bags in Temporary Storage Area
     * @return
     */

    public synchronized int getNumBags(){
        return numBags;
    }


    public int numbOfBag(){
        return bags.size();
    }
}
