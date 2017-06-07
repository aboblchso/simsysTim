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


import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by laemmel on 18/04/16.
 */
public class Vehicle {

    Logger LOG = Logger.getLogger(String.valueOf(Vehicle.class));


    String id;

    private static final double MAX_SPEED = 1;

    private double vx = 0;
    private double vy = 0;

    private double length = 0.4;
    private double width = 0.2;
    private double weight = 90;
    private double A = 2000;
    private double B = 0.08;
    double f1x, f2x, f3x, f4x, f1y, f2y, f3y, f4y;

    private double x;
    private double y;

    private int startTime;


    private boolean isInTheSimulation = false;

    private double phi = 0;
    private double k = 240000;
    boolean toBeRemovedAfterFinish = false;

    public Vehicle(Network n, Node nStart, Node nDestination, int startTime, String vehicleId) {
        this.x = nStart.getX();
        this.y = nStart.getY();
        Dijkstra dijkstra = new Dijkstra(n, nStart, nDestination);
        dijkstra.calculateRoute();
        this.route = dijkstra.getRoute;
        this.startTime = startTime;
        this.id = vehicleId;
    }

    public void setInTheSimulation(boolean inTheSimulation) {
        isInTheSimulation = inTheSimulation;
    }

    public boolean isInTheSimulation() {

        return isInTheSimulation;
    }

    public double getRadius() {
        return radius;
    }

    private double radius = 0.25 + 0.1 * Math.random();

    private List<Link> route;
    int routeIndex;
    private double tau = 0.5;

    public Vehicle(double x, double y, List<Link> route) {
        this.x = x;
        this.y = y;
        this.route = route;
    }

    public void update(Map<String,Vehicle> vehs, double time) {

                if (startTime > time) {
                    return;
                }
                isInTheSimulation = true;
                Link currentLink = this.route.get(routeIndex);

            //Check if the targetLine is crossed
            // If so, then increment routeIndex (move to the next Link)

                double dx = currentLink.getTo().getX() - this.x;
                double dy = currentLink.getTo().getY() - this.y;
                double dist =Math.sqrt(dx*dx + dy*dy);
                dx = dx/dist;
                dy = dy/dist;
                // vector speed (vx, vy)
                double vx0 = dx * MAX_SPEED;
                LOG.info("The current VX of the vehicle " + this.getId() + " is = " + this.vx);
                double vy0 = dy * MAX_SPEED;
                LOG.info("The current VY of the vehicle is = " + this.vy);
                this.phi = Math.atan2(vy, vx);

                f1x = weight * ((vx0 - vx)/tau);
                f1y = weight * ((vy0 - vy)/tau);
                f2x = 0;
                f2y = 0;
                f3x = 0;
                f3y = 0;
                f4x = 0;
                f4y = 0;

                for (Map.Entry<String,Vehicle> vehicleEntry : vehs.entrySet()) {
                    if (vehicleEntry.getValue().isInTheSimulation){
                        double g;
                        // If it is the same vehicle we have, go further
                        if (vehicleEntry.getValue() == this) {
                            continue;
                        }

                        double distanceIJ = Math.sqrt(Math.pow((vehicleEntry.getValue().getX() - this.getX()), 2) + Math.pow(vehicleEntry.getValue().getY() - this.getY(), 2));
                        double nIJX = this.getX() - vehicleEntry.getValue().getX();
                        double nIJY = this.getY() - vehicleEntry.getValue().getY();
                        double nIJXNormalized = nIJX / distanceIJ;
                        double nIJYNormalized = nIJY / distanceIJ;

                        f2x = f2x + A * Math.exp(((this.radius + vehicleEntry.getValue().radius) - distanceIJ) / B) * nIJXNormalized;
                        f2y = f2y + A * Math.exp(((this.radius + vehicleEntry.getValue().radius) - distanceIJ) / B) * nIJYNormalized;

                        if ((this.radius + vehicleEntry.getValue().radius) - distanceIJ > 0)
                            g = 1;
                        else g = 0;


                        f3x = f3x + k * g * ((this.radius + vehicleEntry.getValue().radius) - distanceIJ) * nIJXNormalized;
                        f3y = f3y + k * g * ((this.radius + vehicleEntry.getValue().radius) - distanceIJ) * nIJYNormalized;

                        double deltaVTX = (vehicleEntry.getValue().vx - this.vx) * (- nIJYNormalized);
                        double deltaVTY = (vehicleEntry.getValue().vy - this.vy) * nIJXNormalized;

                        f4x = f4x + k * g * ((this.radius + vehicleEntry.getValue().radius) - distanceIJ) * deltaVTX * (-nIJYNormalized);
                        f4y = f4y + k * g * ((this.radius + vehicleEntry.getValue().radius) - distanceIJ) * deltaVTY * nIJXNormalized;
                    }
                }

                double fx = f1x + f2x + f3x + f4x;
                double fy = f1y + f2y + f3y + f4y;

                double ax = fx / weight;
                double ay = fy / weight;

                this.vx = this.vx + ax * Simulation.H;
                this.vy = this.vy + ay * Simulation.H;

    }

    public void move() {

        this.x = this.x + Simulation.H * this.vx;
        this.y = this.y + Simulation.H * this.vy;
        LOG.info("The current position of the vehicle" + this.getId() +
                    " is " + this.getX() + ", " + this.getY());

        Link currentLink = this.route.get(routeIndex);
        if (currentLink.hasVehicleReachedTheEndOfTheLink(this)) {
            if (currentLink != route.get(route.size()-1) && routeIndex + 1 < route.size()){
                LOG.info("The vehicle " + this + "has crossed the end of the link");
                routeIndex++;
                LOG.info("The vehicle " + this + "allocated to the start of the next link");
                } else {
                    LOG.info("The vehicle " + this + " has reached its destination");
                    isInTheSimulation = false;
                    toBeRemovedAfterFinish = true;
            }
        }
    }

    public double getX() {
        return x;
    }

    public List<Link> getRoute() { return route; }

    public double getY() {
        return y;
    }

    public double getPhi() {
        return phi;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }
    public double getWeight() { return weight; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
