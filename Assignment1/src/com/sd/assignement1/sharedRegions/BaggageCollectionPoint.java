package com.sd.assignement1.sharedRegions;

import com.sd.assignement1.entities.Bag;
import com.sd.assignement1.entities.Passenger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;

/**
 *  This datatype  implements the Baggage Collection Point shared memory region
 *  In this region the passengers wait for the arrival of their bags; upon taking
 *  possession of them, they walk to the arrival terminal exit and leave the airport; those with missed
 *  bags go first to the baggage reclaim office to post their complaint, before walking to the arrival
 *  terminal exit and leave the airport;
 */
public class BaggageCollectionPoint {
    /**
     * Repository
     */
    private  Repository repo;

    /**
     * Array with the bags in the collection Point
     */
    private ArrayList<Bag> bags;

    /**
     * Number of passenger in the colleciton point
     */
    private int numPassengersInside;

    /**
     * Baggage Collection Point instantiation
     * @param repo Repository
     */
    public BaggageCollectionPoint(Repository repo){
        this.repo = repo;
        this.bags = new ArrayList<>();
        this.numPassengersInside = 0;
    }

    /**
     *  A passanger enter in baggage collection point
     * @param p
     */
    public synchronized void enter(Passenger p){
        numPassengersInside++;
    }
    /**
     *  A passanger exit of baggage collection point
     * @param p
     */

    public synchronized void leave(Passenger p){
        numPassengersInside--;
    }

    /**
     * A bag enter in the collection point
     * @param b
     */
    public synchronized void addBag(Bag b){
        bags.add(b);
    }

    /**
     * Get a bed from the collect point with a identifier
     * @param id
     * @return
     */
    public synchronized Bag getBag(int id){
        Bag b = new Bag(-1, -1);
        for(int i = 0; i<bags.size();i++){
            if(bags.get(i).getOwner()==id){
                b = bags.remove(i);
            }
        }
        return b;
    }
    public int numbOfBag(){
        return bags.size();
    }
}
