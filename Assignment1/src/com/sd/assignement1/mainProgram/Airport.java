package com.sd.assignement1.mainProgram;

import com.sd.assignement1.entities.*;
import com.sd.assignement1.sharedRegions.*;

import java.util.Random;

public class Airport {
    /**
     * Number of Planes
     */
    public static final int P = 5;

    /**
     * Number of Passengers per plane
     */
    public static final int N = 6;

    /**
     * Maximum number of baggages/person
     */
    public static final int B = 2;

    /**
     * Capacity of transfer bus
     */
    public static final int T = 3;

    /**
     * Minimum milliseconds to wakeup.
     * Affects Passenger walking time.
     */
    public static final int minSleep = 5;

    /**
     * Maximum milliseconds to wakeup.
     * Affects Passenger walking time.
     */
    public static final int maxSleep = 10;

    public static void main(String[] args) {
        int passengerId = 1;
        int bagID = 1;


        //shared memory regions
        Repository repo = new Repository();
        ArrivalLounge arrivalLounge= new ArrivalLounge();
        ArrivalTerminalExit arrivalTerminalExit= new ArrivalTerminalExit();
        ArrivalTerminalTQuay arrivalTerminalTQuay = new ArrivalTerminalTQuay();
        BaggageCollectionPoint baggageCollectionPoint = new BaggageCollectionPoint();
        BaggageReclaimOffice baggageReclaimOffice = new BaggageReclaimOffice();
        DepartureTerminalEntrance departureTerminalEntrance = new DepartureTerminalEntrance();
        DepartureTerminalTQuay departureTerminalTQuay = new DepartureTerminalTQuay();
        TemporaryStorageArea temporaryStorageArea = new TemporaryStorageArea();

        //entities
        BusDriver busDriver = new BusDriver(repo);

        //Initialize the passengers for each plane
        Plane[] planes = new Plane[P];
        Passenger[] passengers = new Passenger[N];

        for(int plane = 0; plane<P;plane++) {
            for (int i = 0; i < N; i++) {
                int z = (int) (Math.random() * ((B - 0) + 1));
                Bag bags[] = new Bag[z];
                if (z > 0) {
                    for (int x = 0; x < z; x++) {
                        bags[i] = new Bag(bagID, passengerId);
                        bagID++;
                    }
                    if (((int) (Math.random() * 2)) > 1) {
                        passengers[i] = new Passenger(passengerId, repo, bags, "TRF", States.WSD, z, arrivalLounge, arrivalTerminalExit, baggageCollectionPoint, baggageReclaimOffice, departureTerminalEntrance, departureTerminalTQuay, arrivalTerminalTQuay);
                        passengerId++;
                    } else {
                        passengers[i] = new Passenger(passengerId, repo, bags, "PKAT", States.WSD, z, arrivalLounge, arrivalTerminalExit, baggageCollectionPoint, baggageReclaimOffice, departureTerminalEntrance, departureTerminalTQuay, arrivalTerminalTQuay);
                        passengerId++;
                    }
                } else {
                    if (((int) (Math.random() * 2)) > 1) {
                        passengers[i] = new Passenger(passengerId, repo, bags, "TRF", States.WSD, 0, arrivalLounge, arrivalTerminalExit, baggageCollectionPoint, baggageReclaimOffice, departureTerminalEntrance, departureTerminalTQuay, arrivalTerminalTQuay);
                        passengerId++;
                    } else {
                        passengers[i] = new Passenger(passengerId, repo, bags, "PKAT", States.WSD, 0, arrivalLounge, arrivalTerminalExit, baggageCollectionPoint, baggageReclaimOffice, departureTerminalEntrance, departureTerminalTQuay, arrivalTerminalTQuay);
                        passengerId++;
                    }

                }
            }

            planes[plane] = new Plane(plane, 6, false);
            for (int z = 0; z < passengers.length; z++) {
                planes[plane].addPassenger(passengers[z]);
                if (passengers[z].getNumBags() > 0) {
                    Bag[] bags = passengers[z].getBags();
                    for (int q = 0; q < bags.length; q++) {
                        planes[plane].addBag(bags[q]);
                    }
                }
            }
        }

        Porter porter = new Porter(repo, States.WPTL, planes[0], baggageCollectionPoint, temporaryStorageArea);

        busDriver.start();
        porter.start();
        for(Passenger p: passengers){
            p.start();
            try{
                p.join();
            }
            catch (InterruptedException e){

            }
        }

    }
}
