package vehicleWithSocialForces;/* *********************************************************************** *
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


import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Created by laemmel on 24/04/16.
 */
public class VehicleInfo {

    private final int x;
    private final int y;
    private final double phi;

    private final double scale = 100;
    private final int length;
    private final int width;
    private final boolean isInTheSimulation;
    private final double vx;
    private final double vy;
    private final double momentSpeed;
    private final double radius;

    public VehicleInfo(double x, double y, double phi, double length, double width, boolean isInTheSimulation, double vx, double vy, double radius) {
        this.x = (int) (Simulation.SCALE * x);
        this.y = (int) (Simulation.SCALE * y);
        this.phi = phi;
        this.length = (int) (Simulation.SCALE * length);
        this.width = (int) (Simulation.SCALE * width);
        this.isInTheSimulation = isInTheSimulation;
        this.vx = vx;
        this.vy = vy;

        this.momentSpeed = Math.sqrt(vx*vx + vy*vy);
        this.radius = Simulation.SCALE * radius;
    }

    public void draw(PApplet p) {

        if (isInTheSimulation){
        p.ellipse(x, y, (float) radius * 10, (float) radius * 10);

        if (momentSpeed < 0.2) {
            p.fill(255, 64, 64, 200);
        } else if (momentSpeed < 0.9) {
            p.fill(255, 214, 3, 200);
        } else {
            p.fill(64, 255, 64, 200);
        }
        /*
        p.pushMatrix();

        p.translate(x, y);

        p.rotate((float) (phi));
//        p.stroke(255,0,0);
        p.rect(-length / 2, -width / 2, length, width);

        p.ellipseMode(PConstants.CENTER);
        p.fill(255, 0, 0);
        p.ellipse(length / 2, -width / 2, 10, 10);
        p.fill(0, 0, 255);
        p.ellipse(length / 2, width / 2, 10, 10);
        p.popMatrix();
        */
        }

    }
}
