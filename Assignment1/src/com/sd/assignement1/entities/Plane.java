package com.sd.assignement1.entities;

import java.util.ArrayDeque;
import java.util.Deque;

public class Plane {

    public static Deque<Bag> bagStack = new ArrayDeque<Bag>();

    public static int id;

    public static int numPassengers;

    public static int numBags;

    public static int currentPassengers;

    public static int currentBags;

    public static boolean landed;

    public static Passenger[] passengers;

    public Plane(int id, int numPassengers, boolean landed){
        this.id = id;
        this.numPassengers = numPassengers;
        this.currentBags = 0;
        this.currentPassengers = 0;
        this.landed = landed;

    }

    public synchronized void addBag(Bag b){
        synchronized (this) {
            bagStack.add(b);
            numBags += 1;
            currentBags += 1;
        }
    }

    public synchronized  Bag getBag(){
        synchronized (this) {
            currentBags--;
            return bagStack.getFirst();
        }
    }

    public synchronized void addPassenger(Passenger p){
        synchronized (this) {
            currentPassengers +=1;
        }
    }

    public synchronized boolean empty(){
        synchronized (this) {
            if (currentPassengers == 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public synchronized  boolean hasBags(){
        synchronized (this) {
            if (currentBags > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public synchronized  void leave(){
        synchronized (this) {
            if (currentPassengers > 0 && landed()) {
                currentPassengers--;

                Passenger p = (Passenger) Thread.currentThread();
                System.out.println("Passenger " + p.myId() +" leaving the plane------- " + " num people on board " + currentPassengers);
                if(currentPassengers == 0){
                    this.notifyAll();
                }
            } else {
                System.err.println("Number of passengers leaving the plane incorrect");
            }
        }


    }

    public  synchronized  void setLanded(boolean landed){
        synchronized (this){
            this.landed = landed;
            this.notifyAll();
        }



    }

    public synchronized boolean landed(){
        synchronized (this) {
            return this.landed;
        }
    }


}
