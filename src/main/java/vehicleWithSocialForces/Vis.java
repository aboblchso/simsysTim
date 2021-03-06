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

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Vis extends PApplet {


    private List<VehicleInfo> vehs = new ArrayList<>();

    private static final int WIDTH = 1080;
    private static final int HEIGHT = 1080;

    private int x = 0;
    private int y = 0;

    private double phi = 0;
    private Network n;

    public Vis(Network n) {
        this.n = n;
        JFrame fr = new JFrame();
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setSize(WIDTH, HEIGHT);
        JPanel panel = new JPanel();
        panel.setLayout(new OverlayLayout(panel));

        fr.add(panel, BorderLayout.CENTER);

        panel.add(this);
        panel.setEnabled(true);
        panel.setVisible(true);

        this.init();
        frameRate(90);

        fr.setVisible(true);

        size(WIDTH, HEIGHT);
        background(0,0,0);

    }

    @Override
    public void draw() {
        background(0,0,0); // eraser
        n.draw(this);

        synchronized (this.vehs) {
            for (VehicleInfo v : this.vehs) {
                v.draw(this);
            }
        }
        saveFrame("output/ped-######.png");

    }

    public void update(double time, List<VehicleInfo> vehs) {
        synchronized (this.vehs) {
            this.vehs = new ArrayList<VehicleInfo>(vehs);
        }

    }
}
