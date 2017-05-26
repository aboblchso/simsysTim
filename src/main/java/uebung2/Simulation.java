package uebung2;
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


import java.util.ArrayList;
import java.util.List;

/**
 * Created by laemmel on 24/04/16.
 */
public class Simulation {

    public static final double H = 0.1;
    public static final double SCALE = 100.0;

    private final Vis vis;
    private List<Vehicle> vehs = new ArrayList<>();

    public Simulation(Network n) {
         this.vis = new Vis(n);
    }

    public static void main(String[] args) {
        Network n = new Network();
        Node n0 = n.createNode(0,0,0);
        Node n1 = n.createNode(4,0,1);
        Node n2 = n.createNode(4,4,2);
        /*
        Node n3 = n.createNode(0,2,3);
        Node n4 = n.createNode(0,4,4);
        Node n5 = n.createNode(6,4,5);
        Node n6 = n.createNode(6,6,6);
        */
        Link l0 = n.createLink(n0,n1,0);
        Link l1 = n.createLink(n1,n2,2);
        /*
        Link l0rev = n.createLink(n1,n0,1);
        Link l1rev = n.createLink(n2,n1,3);
        Link l2 = n.createLink(n2,n3,4);
        Link l2rev = n.createLink(n3,n2,5);
        Link l3 = n.createLink(n3,n4,6);
        Link l3rev = n.createLink(n4,n3,7);
        Link l4 = n.createLink(n4,n5,8);
        Link l4rev = n.createLink(n5,n4,9);
        Link l5 = n.createLink(n5,n6,8);
        Link l5rev = n.createLink(n6,n5,9);
*/

        List<Link> route0 = new ArrayList<>();
        route0.add(l0);
        route0.add(l1);
        /*
        route0.add(l2);
        route0.add(l3);
        route0.add(l4);
        route0.add(l5);
*/

        Simulation sim = new Simulation(n);
        sim.add(new Vehicle(0,0, route0));
        sim.run();

    }

    private void run() {
        double time = 0;

        double maxTime = 1000;


        while (time < maxTime) {
            for (Vehicle v : this.vehs) {
                v.update(this.vehs);
            }
            for (Vehicle v : this.vehs) {
                v.move();
            }
            //
            List<VehicleInfo> vInfos = new ArrayList<>();
            for (Vehicle v : this.vehs) {
               VehicleInfo vi = new VehicleInfo(v.getX(), v.getY(), v.getPhi(), v.getLength(), v.getWidth());
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
        this.vehs.add(v1);
    }
}
