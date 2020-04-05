package com.sd.assignement1.sharedRegions;

import com.sd.assignement1.entities.Passenger;
import com.sd.assignement1.mainProgram.Airport;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

public class ArrivalTerminalTQuay {

    private static Queue<Passenger> queue = new ArrayDeque<>();

    private static Repository repo;

    public ArrivalTerminalTQuay(Repository repo){
        this.repo = repo;
    }

    public void enter(Passenger p){

    }

    public synchronized Passenger getPassenger(){
        return queue.remove();
    }

    public synchronized void enterQueue(){
        Passenger p = (Passenger) Thread.currentThread();
        queue.add(p);
        synchronized (this){
            this.notifyAll();
        }


    }
    public synchronized boolean queueEmpty(){
        if(queue.isEmpty()){
            return true;
        }
        else {
            return false;
        }
    }
}
