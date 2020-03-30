package com.sd.assignement1.sharedRegions;

import com.sd.assignement1.entities.Passenger;

public class TemporaryStorageArea {

    private static Repository repo;

    public TemporaryStorageArea(Repository repo){
        this.repo = repo;
    }

    public void enter(Passenger p){

    }
}
