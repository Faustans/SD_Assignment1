package com.sd.assignement1.mainProgram;

import com.sd.assignement1.entities.*;
import com.sd.assignement1.sharedRegions.*;

import java.io.FileNotFoundException;
import java.util.Date;
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
     * Repository log filename
     */
    public static final String filename = "log_" + new Date().toString().replace(' ', '_') + ".txt";

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

    public static void main(String[] args) throws FileNotFoundException {
        int passengerId = 1;
        int bagID = 1;
        Passenger[] passengers = new Passenger[N*P];

        //shared memory regions
        Repository repo = new Repository();
        ArrivalLounge arrivalLounge= new ArrivalLounge(repo);
        ArrivalTerminalExit arrivalTerminalExit= new ArrivalTerminalExit(repo);
        ArrivalTerminalTQuay arrivalTerminalTQuay = new ArrivalTerminalTQuay(repo);
        BaggageCollectionPoint baggageCollectionPoint = new BaggageCollectionPoint(repo);
        BaggageReclaimOffice baggageReclaimOffice = new BaggageReclaimOffice(repo);
        DepartureTerminalEntrance departureTerminalEntrance = new DepartureTerminalEntrance(repo);
        DepartureTerminalTQuay departureTerminalTQuay = new DepartureTerminalTQuay(repo);
        TemporaryStorageArea temporaryStorageArea = new TemporaryStorageArea(repo);

        //entities
        BusDriver busDriver = new BusDriver(repo,arrivalTerminalTQuay,departureTerminalTQuay);

        //Initialize the passengers for each plane
        Plane[] planes = new Plane[P];

        for(int plane = 0; plane<P;plane++) {
            for (int i = 0; i < N; i++) {
                int z = (int) (Math.random() * ((B - 0) + 1));
                Bag bags[] = new Bag[z];
                if (z > 0) {
                    for (int x = 0; x < z; x++) {
                        bags[x] = new Bag(bagID, passengerId);
                        bagID++;
                    }
                    if (((int) (Math.random() * 2)) > 0) {
                        passengers[passengerId-1] = new Passenger(passengerId, repo, bags, "TRF", States.WSD, z, arrivalLounge, arrivalTerminalExit, baggageCollectionPoint, baggageReclaimOffice, departureTerminalEntrance, departureTerminalTQuay, arrivalTerminalTQuay);
                        passengerId++;
                         
                        for(Bag b:bags){
                            b.setSituation("TRF");
                        }
                    } else {
                        passengers[passengerId-1] = new Passenger(passengerId, repo, bags, "PKAT", States.WSD, z, arrivalLounge, arrivalTerminalExit, baggageCollectionPoint, baggageReclaimOffice, departureTerminalEntrance, departureTerminalTQuay, arrivalTerminalTQuay);
                        passengerId++;
                         
                        for(Bag b:bags){
                            b.setSituation("PKAT");
                        }
                    }
                } else {
                    if (((int) (Math.random() * 2)) > 0) {
                        passengers[passengerId-1] = new Passenger(passengerId, repo, bags, "TRF", States.WSD, 0, arrivalLounge, arrivalTerminalExit, baggageCollectionPoint, baggageReclaimOffice, departureTerminalEntrance, departureTerminalTQuay, arrivalTerminalTQuay);
                        passengerId++;
                         
                        for(Bag b:bags){
                            b.setSituation("TRF");
                        }
                    } else {
                        passengers[passengerId-1] = new Passenger(passengerId, repo, bags, "PKAT", States.WSD, 0, arrivalLounge, arrivalTerminalExit, baggageCollectionPoint, baggageReclaimOffice, departureTerminalEntrance, departureTerminalTQuay, arrivalTerminalTQuay);
                        passengerId++;
                         
                        for(Bag b:bags){
                            b.setSituation("PKAT");
                        }
                    }

                }
            }

            planes[plane] = new Plane(plane, 6, false);
            for (int z = plane*N; z < (passengers.length*(plane+1))/(P); z++) {
                System.out.println("Z ------------>               " + z + " ----- AND PLANE: " + plane);
                planes[plane].addPassenger(passengers[z]);
                if (passengers[z].getNumBags() > 0) {
                    Bag[] bags = passengers[z].getBags();
                    for (int q = 0; q < bags.length; q++) {
                        planes[plane].addBag(bags[q]);
                    }
                }
                passengers[z].setPlane(planes[plane]);
            }
        }

        Porter porter = new Porter(repo, States.WPTL, planes[0], baggageCollectionPoint, temporaryStorageArea);
        busDriver.setName("BusDriver");
        busDriver.start();
        porter.setName("Porter");
        porter.start();
        for(Passenger p: passengers){
            p.setName("Passenger"+p.myId());
            p.start();

        }
        for(Passenger p: passengers){

            try{
                p.join();
            }
            catch (InterruptedException e){

            }
        }

    }
}
