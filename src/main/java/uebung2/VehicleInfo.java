package uebung2;/* *********************************************************************** *
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

    public VehicleInfo(double x, double y, double phi, double length, double width) {
        this.x = (int) (Simulation.SCALE * x);
        this.y = (int) (scale * y);
        this.phi = phi;
        this.length = (int) (scale * length);
        this.width = (int) (scale * width);

    }

    public void draw(PApplet p) {
        p.pushMatrix();

        p.translate(x, y);

        p.rotate((float) (phi));
        if (1==1) {
            p.fill(255, 64, 64, 200);
        } else {
            p.fill(64, 255, 64, 200);
        }
//        p.stroke(255,0,0);
        p.rect(-length / 2, -width / 2, length, width);
        p.ellipseMode(PConstants.CENTER);
        p.fill(255, 0, 0);
        p.ellipse(length / 2, -width / 2, 10, 10);
        p.fill(0, 0, 255);
        p.ellipse(length / 2, width / 2, 10, 10);
        p.popMatrix();


    }
}
