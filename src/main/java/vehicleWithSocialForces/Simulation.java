package vehicleWithSocialForces;
/* *********************************************************************** *
 * project: simsocsys
 *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2016 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : gregor dot laemmel at gmail dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */


import java.util.*;

/**
 * Created by laemmel on 24/04/16.
 */
public class Simulation {

    public static final double MAX_TIME = 1000;
    public static final double H = 0.1;
    public static final double SCALE = 100.0;
    private static int numberOfRandomVehicles = 2;

    private final Vis vis;
    private Map<String, Vehicle> vehs = new HashMap<>();

    public Simulation(Network network) {
         this.vis = new Vis(network);
    }

    public static void main(String[] args) {
        Network network = new Network();
        Node node0 = network.createNode(1,1,0);
        Node node1 = network.createNode(4,1,1);
        Node node2 = network.createNode(4,4,2);
        Node node3 = network.createNode(1,4,3);
        Node node4 = network.createNode(1,6,4);
        Node node5 = network.createNode(4,6,5);
        Node node6 = network.createNode(4,6,6);
        Link l0 = network.createLink(node0,node1,0);
        Link l0rev = network.createLink(node1,node0,1);
        Link l1 = network.createLink(node1,node2,2);
        Link l1rev = network.createLink(node2,node1,3);
        Link l2 = network.createLink(node2,node3,4);
        Link l2rev = network.createLink(node3,node2,5);
        Link l3 = network.createLink(node3,node4,6);
        Link l3rev = network.createLink(node4,node3,7);
        Link l4 = network.createLink(node4,node5,8);
        Link l4rev = network.createLink(node5,node4,9);
        Link l5 = network.createLink(node5,node6,10);
        Link l5rev = network.createLink(node6,node5,11);
        Link l6 = network.createLink(node2,node0,12);


        /*
        List<Link> route0 = new ArrayList<>();
        route0.add(l0);
        route0.add(l1);
        route0.add(l2);
        route0.add(l3);
        route0.add(l4);
        route0.add(l5);

        List<Link> route1 = new ArrayList<>();
        route1.add(l0rev);
        route1.add(l0);
        route1.add(l5);
        */

        Simulation sim = new Simulation(network);
        sim.add(new Vehicle(network,node0,node1,0,"test1"));
        sim.add(new Vehicle(network,node0,node1,1,"test2"));
        //addRandomVehicles(network, sim, numberOfRandomVehicles);
        sim.run();

    }

    private static void addRandomVehicles(Network network, Simulation sim, int numberOfRandomVehicles) {
        for (int i = 0; i<numberOfRandomVehicles; i++){
            int startNodeId = (int) (Math.random() * network.nodes.size());
            int finishNodeId = (int) (Math.random() * network.nodes.size());
            if (startNodeId != finishNodeId){
                createRandomDeparture(network, sim, startNodeId, finishNodeId, i);
            }
        }
    }

    private static void createRandomDeparture(Network network, Simulation sim, int startNodeId, int finishNodeId, int i) {
        int startTime = (int) (Math.random() * (MAX_TIME - 990));
        String vehicleId = "Vehicle_" + startNodeId + "_to_" + finishNodeId + "_at_" + startTime + "_" + i;
        Node startNode = network.nodes.get(startNodeId);
        Node finishNode = network.nodes.get(finishNodeId);
        sim.add(new Vehicle(network, startNode, finishNode, startTime, vehicleId));
        System.out.println("Random Vehicle " + vehicleId + " is created");
    }

    private void run() {
        double time = 0;



        while (time < MAX_TIME) {
            for (Map.Entry<String, Vehicle> vehicleEntry : vehs.entrySet()) {
                if (!vehicleEntry.getValue().toBeRemovedAfterFinish){
                        vehicleEntry.getValue().update(this.vehs, time);
                } else {
                    this.remove(vehicleEntry.getValue());
                }
            }
            for (Map.Entry<String, Vehicle> vehicleEntry : vehs.entrySet()) {
                vehicleEntry.getValue().move();
            }
            //
            List<VehicleInfo> vInfos = new ArrayList<>();
            for (Map.Entry<String, Vehicle> vehicleEntry : vehs.entrySet()) {
               VehicleInfo vi = new VehicleInfo(vehicleEntry.getValue().getX(), vehicleEntry.getValue().getY(), vehicleEntry.getValue().getPhi(), vehicleEntry.getValue().getLength(), vehicleEntry.getValue().getWidth(), vehicleEntry.getValue().isInTheSimulation());
                       vInfos.add(vi);
            }
            this.vis.update(time, vInfos);

            time += H;

            try {
                Thread.sleep((long) (H * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void add(Vehicle v1) {
        this.vehs.put(v1.getId(), v1);
    }

    private void remove(Vehicle vehicle){
        this.vehs.remove(vehicle.getId());
    }
}
