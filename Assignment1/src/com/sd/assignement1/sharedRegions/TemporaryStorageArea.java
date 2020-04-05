package com.sd.assignement1.sharedRegions;

import com.sd.assignement1.entities.Bag;
import com.sd.assignement1.entities.Passenger;

import java.util.ArrayList;

public class TemporaryStorageArea {

    private  Repository repo;

    private ArrayList<Bag> bags;

    private int numBags;



    public TemporaryStorageArea(Repository repo) {
        this.repo = repo;
        this.bags = new ArrayList<>();
        this.numBags = 0;
    }

    public synchronized void addBag(Bag b){
        bags.add(b);
        numBags++;
    }

    public synchronized int getNumBags(){
        return numBags;
    }
}
