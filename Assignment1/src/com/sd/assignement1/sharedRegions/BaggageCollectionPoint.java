package com.sd.assignement1.sharedRegions;

import com.sd.assignement1.entities.Bag;
import com.sd.assignement1.entities.Passenger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;

public class BaggageCollectionPoint {

    private  Repository repo;

    private ArrayList<Bag> bags;

    private int numPassengersInside;

    public BaggageCollectionPoint(Repository repo){
        this.repo = repo;
        this.bags = new ArrayList<>();
        this.numPassengersInside = 0;
    }

    public synchronized void enter(Passenger p){
        numPassengersInside++;
    }

    public synchronized void leave(Passenger p){
        numPassengersInside--;
    }

    public synchronized void addBag(Bag b){
        bags.add(b);
    }

    public synchronized Bag getBag(int id){
        Bag b = new Bag(-1, -1);
        for(int i = 0; i<bags.size();i++){
            if(bags.get(i).getOwner()==id){
                b = bags.remove(i);
            }
        }
        return b;
    }
}
