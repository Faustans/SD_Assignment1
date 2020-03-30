package com.sd.assignement1.sharedRegions;

import com.sd.assignement1.entities.Passenger;

public class BaggageCollectionPoint {

    private static Repository repo;

    public BaggageCollectionPoint(Repository repo){
        this.repo = repo;
    }

    public void enter(Passenger p){

    }
}
