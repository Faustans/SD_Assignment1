package com.sd.assignement1.sharedRegions;

import com.sd.assignement1.entities.Bag;
import com.sd.assignement1.entities.Passenger;

/**
 * his datatype  implements the Baggage Reclaim Office shared memory region
 * In this region the passenger can post their complain in don't find is bag
 */
public class BaggageReclaimOffice {
    /**
     * Repository
     */
    private static Repository repo;
    /**
     * Number of bags that Reclaimed
     */
    private static int numBagsReclaimed;

    /**
     * Baggage Reclaim Office instantiation
     * @param repo
     */
    public BaggageReclaimOffice(Repository repo){
        this.repo = repo;
        this.numBagsReclaimed = 0;
    }

    public void enter(Passenger p){

    }

    /**
     * Reclaim of a bag with identifier b
     * @param b
     */
    public void reclaim(Bag b){
        numBagsReclaimed++;
    }

}
