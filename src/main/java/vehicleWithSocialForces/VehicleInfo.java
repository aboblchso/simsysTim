package vehicleWithSocialForces;


import processing.core.PApplet;


public class VehicleInfo {

    private final int x;
    private final int y;
    private final double phi;

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

            p.ellipse(x, y, (float) (radius * Simulation.SCALE), (float) (radius * Simulation.SCALE));


            if (momentSpeed < 0.2) {
                p.fill(255, 64, 64, 200);
            } else if (momentSpeed < 0.9) {
                p.fill(255, 214, 3, 200);
            } else {
                p.fill(64, 255, 64, 200);
            }
        }

    }
}
