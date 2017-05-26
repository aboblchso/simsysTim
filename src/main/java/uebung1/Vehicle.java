package uebung1;/* *********************************************************************** *
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

/**
 * Created by laemmel on 18/04/16.
 */
public class Vehicle {

    private static final double MAX_SPEED = 1;
    private static final double MAX_OMEGA = Math.PI / 2;

    public Wiring getWiring() {
        return wiring;
    }

    private final Wiring wiring;
    private double vx = 0;
    private double vy = 0;

    public enum Wiring {
        Straight,
        Crossover
    }

    private final static double FRICTION = 0.1;

    private final static double FORCE = 1;


    private final double weight = 1;

    private double length = 0.4;
    private double width = 0.2;

    // поступательная скорость
    private double speed = 0;
    // вращательная скорость
    private double omega = 0;

    private double x;
    private double y;

    private double phi = 0;//radian!!
    private double sensXLeft;
    private double sensYLeft;
    private double sensXRight;
    private double sensYRight;


    // The constructor to build the Vehicle object
    public Vehicle(double x, double y, Wiring wiring) {
        this.x = x;
        this.y = y;
        this.wiring = wiring;
    }


    public void update(List<Vehicle> vehs) {

        //
        double dx = 1 * Math.cos(phi);
        double dy = 1 * Math.sin(phi);

        double xPrime = x + dx * length / 2;
        double yPrime = y + dy * length / 2;

        this.sensXLeft = xPrime - dy * width / 2;
        this.sensYLeft = yPrime + dx * width / 2;

        this.sensXRight = xPrime + dy * width / 2;
        this.sensYRight = yPrime - dx * width / 2;

        // This is the measurements, made by the left and right sensors
        // This is just the initial values so the will start at the first second
        double leftSensActivation = .5;
        double rightSensActivation = 1.;

        Vehicle closest = null;
        double minSqrDist = Double.POSITIVE_INFINITY;
        //Iterate through all vehicles
        for (Vehicle v : vehs) {
            // If it is the same vehicle we have, go further
            if (v == this) {
                continue;
            }

            // for each vehicle calculate the square of the distaance to it (deltaX^2 + deltaY^2)
            double sqrDist = (this.x - v.getX()) * (this.x - v.getX()) +
                    (this.y - v.getY()) * (this.y - v.getY());

            // Check if the distance is smaller then the smallest registered (so far)
            if (sqrDist < minSqrDist) {
                // if smaller, set as the smallest distance
                minSqrDist = sqrDist;
                // And mark the vehicle closest
                closest = v;
            }
        }
        //TODO calculate left and right sensor activation
        if (closest != null) {
            // the coordiates of the closest vehicle from the getters of that vehicle object
            double x = closest.getX();
            double y = closest.getY();

            // now calculate which one of our sensors is closer to this vehicle
            // so we must "leftDistance" and RightDistance (more precisely - their squares)
            double sqrDistLeft = (x - this.getSensXLeft()) * (x - this.getSensXLeft()) + (y - this.getSensYLeft()) * (y - this.getSensYLeft());
            double sqrDistRight = (x - this.getSensXRight()) * (x - this.getSensXRight()) + (y - this.getSensYRight()) * (y - this.getSensYRight());
            // so if the Left side is closer to another vehicle, we must turn left and the FORCE on the right must be greater
            // initially it was the other way around
            if (sqrDistLeft < sqrDistRight) {
                leftSensActivation = 0.1;
                rightSensActivation = 1.;
            // the other way around here
            } else {
                leftSensActivation = 1.;
                rightSensActivation = 0.1;
            }

        }

        // Introduce the variables for the corresponding forces
        double forceLeftEngine;
        double forceRightEngine;
        // For the different Wiring there is a different procedure.
        // The motors can be attached to the sensor according the respective side or the other way around (crossover)
        if (this.wiring == Wiring.Crossover) {
            // If crossover then exchange the sides
            forceRightEngine = leftSensActivation * FORCE;
            forceLeftEngine = rightSensActivation * FORCE;
        } else {
            // If not - translate to the respective motors
            forceRightEngine = rightSensActivation * FORCE;
            forceLeftEngine = leftSensActivation * FORCE;
        }

        // this is a forward Force component, whis is defined by the minimal of two forces (left and right)
        double Fdri = 2 * Math.min(forceLeftEngine, forceRightEngine);
        // the acceleration from this Force (with the subtraction of the Friction Force)
        double a = Fdri / weight - FRICTION;

        // defining the temporal forward component of the speed
        double tmpSpeed = this.speed + Simulation.H * a;
        // just to be safe, the vehicle doesn't drive backwards
        this.speed = Math.max(0, Math.min(tmpSpeed, MAX_SPEED));

        // defining the rotational component of the Force as a different between two side Forces
        double Frot =  forceRightEngine - forceLeftEngine;

        // определяем вращательное ускорение
        double alpha = Frot / (weight * width / 2);

        // define the temporal rotation speed (w + deltaT*alfa)
        double tmpOmega = this.omega + Simulation.H * alpha;
        // define the actual rotation speed, so it lies between -PI / 2  and + PI / 2 and is = temporal w
        this.omega = Math.max(-MAX_OMEGA, Math.min(tmpOmega, MAX_OMEGA));

        // define the new angle of the vehicle
        this.phi = this.phi + this.omega * Simulation.H;

        // now define the vector components of the speed of the vehicle
        this.vx = this.speed * Math.cos(phi);
        this.vy = this.speed * Math.sin(phi);

    }

    // now that we know the old position and both components of the vector speed,
    // we can actually move the vehicle to the next point
    // assuming the change in position defined by the speed only
    public void move() {
        // change x position
        this.x = this.x + Simulation.H * this.vx;
        // change y position
        this.y = this.y + Simulation.H * this.vy;
        // here we teleport the vehicles when they reach the edge of the simulation screen
        if (this.x > 8) {
            this.x = 0;
        }
        if (this.x < 0) {
            this.x = 8;
        }
        if (this.y > 6) {
            this.y = 0;
        }
        if (this.y < 0) {
            this.y = 6;
        }


    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getPhi() {
        return phi;
    }

    public double getSensXLeft() {
        return sensXLeft;
    }

    public double getSensXRight() {
        return sensXRight;
    }

    public double getSensYLeft() {
        return sensYLeft;
    }

    public double getSensYRight() {
        return sensYRight;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }
}
