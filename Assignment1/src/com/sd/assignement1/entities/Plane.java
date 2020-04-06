package com.sd.assignement1.entities;

import java.util.ArrayDeque;
import java.util.Deque;

public class Plane {

<<<<<<< HEAD
    /**
     * Queue of the bags in the plane
     */
    private static Deque<Bag> bagStack = new ArrayDeque<Bag>();

    /**
     * Plane identifier
     */
    private static int id;

    /**
     * Number of the passenger in the plane
     */
    private static int numPassengers;

    /**
     * Number of the bags in the plane
     */
    private static int numBags;

    /**
     * Number of current Passenger in the plane
     */
    private static int currentPassengers;

    /**
     * Number of the current bags in the plane
     */
    private static int currentBags;

    /**
     * Boolean that confirm if the plane is landed or not
     */
    private static boolean landed;

    /**
     * Array with the passengers in the plane
     */
    private static Passenger[] passengers;
=======
    public static Deque<Bag> bagStack = new ArrayDeque<Bag>();

    public static int id;

    public static int numPassengers;

    public static int numBags;

    public static int currentPassengers;

    public static int currentBags;

    public static boolean landed;

    public static Passenger[] passengers;
>>>>>>> 902cd58e893d4c56c6e4a7ca0f3b3ac796346df8

    /**
     *  Passenger instantiation
     * @param id Identifier of the plane
     * @param numPassengers Number of the passenger in the plane
     * @param landed boolean if plane is landed or not
     */
    public Plane(int id, int numPassengers, boolean landed){
        this.id = id;
        this.numPassengers = numPassengers;
        this.currentBags = 0;
        this.currentPassengers = 0;
        this.landed = landed;

    }


    /**
     *  Add a bag to the plane
     * @param b
     */
    public synchronized void addBag(Bag b){
        synchronized (this) {
            bagStack.add(b);
            numBags += 1;
            currentBags += 1;
        }
    }

<<<<<<< HEAD
    /**
     * Get a bag from the plane
     * @return
     */
    public synchronized static Bag getBag(){
        currentBags--;
        return bagStack.getFirst();
=======
    public synchronized  Bag getBag(){
        synchronized (this) {
            currentBags--;
            return bagStack.getFirst();
        }
>>>>>>> 902cd58e893d4c56c6e4a7ca0f3b3ac796346df8
    }

    /**
     *  Add a passeng to the plane
     * @param p
     */
    public synchronized void addPassenger(Passenger p){
        synchronized (this) {
            currentPassengers +=1;
        }
    }

    /**
     * confirm if the plane have passenger or if empty
     * @return
     */
    public synchronized boolean empty(){
        synchronized (this) {
            if (currentPassengers == 0) {
                return true;
            } else {
                return false;
            }
        }
    }

<<<<<<< HEAD
    /**
     *  Confirm if the plane have baggs or if is empty
     * @return
     */
    public synchronized static boolean hasBags(){
        if(currentBags == 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Passenger leave the plane
     */
    public synchronized void leave(){
        if(numPassengers>0 && landed()){
            numPassengers--;
        }
        else{
            System.err.println("Number of passengers leaving the plane incorrect");
=======
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
>>>>>>> 902cd58e893d4c56c6e4a7ca0f3b3ac796346df8
        }


    }

<<<<<<< HEAD
    /**
     * Set if the plane is landed or not
     * @param landed
     */
    public synchronized void setLanded(boolean landed){
        this.landed = landed;
=======
    public  synchronized  void setLanded(boolean landed){
        synchronized (this){
            this.landed = landed;
            this.notifyAll();
        }



>>>>>>> 902cd58e893d4c56c6e4a7ca0f3b3ac796346df8
    }

    public synchronized boolean landed(){
        synchronized (this) {
            return this.landed;
        }
    }

    /**
     * Get the current Bags in the plane
     * @return
     */
    public static int getCurrentBags(){
        return this.currentBags;
    }

}
