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


import java.util.List;
import java.util.logging.Logger;

/**
 * Created by laemmel on 18/04/16.
 */
public class Vehicle {

    Logger LOG = Logger.getLogger(String.valueOf(Vehicle.class));

    private static final double MAX_SPEED = 1;

    private double vx = 0;
    private double vy = 0;

    private double length = 0.4;
    private double width = 0.2;

    private double x;
    private double y;

    private double phi = 0;



    private List<Link> route;
    int routeIndex;


    public Vehicle(double x, double y, List<Link> route) {
        this.x = x;
        this.y = y;
        this.route = route;
    }


    public void update(List<Vehicle> vehs) {

                Link currentLink = this.route.get(routeIndex);


            //Check if the targetLine is crossed
            // If so, then increment routeIndex (move to the next Link)

                double dx = currentLink.getTo().getX() - this.x;
                double dy = currentLink.getTo().getY() - this.y;
                double dist =Math.sqrt(dx*dx + dy*dy);
                dx = dx/dist;
                dy = dy/dist;

                this.vx = dx * MAX_SPEED;
                LOG.warning("The current VX of the vehicle is = " + this.vx);
                this.vy = dy * MAX_SPEED;
                LOG.warning("The current VY of the vehicle is = " + this.vy);
                this.phi = Math.atan2(vy, vx);

    }

    public void move() {

        this.x = this.x + Simulation.H * this.vx;
        this.y = this.y + Simulation.H * this.vy;
        LOG.warning("The current position of the vehicle" + this +
                    " is " + this.getX() + ", " + this.getY());

        Link currentLink = this.route.get(routeIndex);
        if (currentLink.hasVehicleReachedTheEndOfTheLink(this)) {
            LOG.warning("The vehicle " + this + "has crossed the end of the link");
            routeIndex++;
            LOG.warning("The vehicle " + this + "allocated to the start of the next link");

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
}
