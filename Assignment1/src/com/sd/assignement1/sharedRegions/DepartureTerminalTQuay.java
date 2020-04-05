package com.sd.assignement1.sharedRegions;

import com.sd.assignement1.entities.Passenger;
import com.sd.assignement1.entities.States;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

public class DepartureTerminalTQuay {

    private static Repository repo;
    private static boolean busHere;
    private static Queue<Passenger> queue = new ArrayDeque<>(100);

    public DepartureTerminalTQuay(Repository repo){
        this.repo = repo;
        this.busHere = false;
    }


    public synchronized void queueIn(Passenger p){
        synchronized (this){
            queue.add(p);
        }
    }

    public synchronized void setArrived(boolean b){
        synchronized (this){
            this.busHere = b;
            this.notifyAll();
        }

    }

    public synchronized boolean busArrived(){
        if(this.busHere){
            return true;
        }
        else{
            return false;
        }
    }
    public synchronized boolean haveILeft(Passenger p){
        return queue.contains(p);
    }
}
