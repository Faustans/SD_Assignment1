package com.sd.assignement1.sharedRegions;

import com.sd.assignement1.entities.Bag;
import com.sd.assignement1.entities.Passenger;

public class BaggageReclaimOffice {

    private static Repository repo;
    private static int numBagsReclaimed;

    public BaggageReclaimOffice(Repository repo){
        this.repo = repo;
        this.numBagsReclaimed = 0;
    }

    public void enter(Passenger p){

    }

    public void reclaim(Bag b){
        numBagsReclaimed++;
    }

}
