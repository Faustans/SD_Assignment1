package com.sd.assignement1.entities;

import java.util.ArrayDeque;
import java.util.Deque;

public class Plane {

    private static Deque<Bag> bagStack = new ArrayDeque<Bag>();

    private static int id;

    private static int numPassengers;

    private static int numBags;

    private static int currentPassengers;

    private static int currentBags;

    private static boolean landed;

    private static Passenger[] passengers;

    public Plane(int id, int numPassengers, boolean landed){
        this.id = id;
        this.numPassengers = numPassengers;
        this.currentBags = 0;
        this.currentPassengers = 0;
        this.landed = landed;

    }

    public synchronized void addBag(Bag b){
        bagStack.add(b);
        numBags+=1;
        currentBags+=1;
    }

    public synchronized static Bag getBag(){
        currentBags--;
        return bagStack.getFirst();
    }

    public synchronized void addPassenger(Passenger p){
        numPassengers+=1;
    }

    public synchronized boolean empty(){
        if(currentPassengers == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public synchronized static boolean hasBags(){
        if(currentBags == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public synchronized void leave(){
        if(numPassengers>0 && landed()){
            numPassengers--;
        }
        else{
            System.err.println("Number of passengers leaving the plane incorrect");
        }
    }

    public synchronized void setLanded(boolean landed){
        this.landed = landed;
    }

    public synchronized boolean landed(){
        return this.landed;
    }


}
