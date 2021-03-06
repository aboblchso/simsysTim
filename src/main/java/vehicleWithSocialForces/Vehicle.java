package vehicleWithSocialForces;


import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class Vehicle {

    Logger LOG = Logger.getLogger(String.valueOf(Vehicle.class));


    String id;

    private static final double MAX_SPEED = 1;


    private double vx = 0;
    private double vy = 0;

    private double length = 0.4;
    private double width = 0.2;
    private double weight = 20;
    private double A = 2000;
    private double B = 0.08;
    double f1x, f2x, f3x, f4x, f1y, f2y, f3y, f4y;

    private double x;
    private double y;

    private double startTime;


    private boolean isInTheSimulation = false;

    private double phi = 0;
    private double k = 240000;
    boolean toBeRemovedAfterFinish = false;

    public Vehicle(Network n, Node nStart, Node nDestination, double startTime, String vehicleId) {
        this.x = nStart.getX() + Math.random() * 0.5;
        this.y = nStart.getY() + Math.random() * 0.5;
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

    private double radius = 0.01 + 0.005 * Math.random();

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
                LOG.info("The current VY of the vehicle " + this.getId() + " is = " + this.vy);
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
/*
                double fx = f1x + f3x + f4x;
                double fy = f1y + f3y + f4y;
                */
                double fx = f1x + f2x + f3x + f4x;
                double fy = f1y + f2y + f3y + f4y;



                double ax = fx / weight;
                double ay = fy / weight;

                this.vx = this.vx + ax * Simulation.H;
                if (vx > 2){
                    vx=2;
                }
                else if (vx < -2){
                    vx = -2;
                }
                this.vy = this.vy + ay * Simulation.H;
                if (vy > 2){
                    vy=2;
                }
                else if (vy < -2){
                    vy = -2;
                }
    }

    public void move() {

        this.x = this.x + Simulation.H * this.vx;
        this.y = this.y + Simulation.H * this.vy;
        LOG.info("The current position of the vehicle " + this.getId() +
                    " is " + this.getX() + ", " + this.getY());

        Link currentLink = this.route.get(routeIndex);
        if (currentLink.hasVehicleReachedTheEndOfTheLink(this)) {
            if (currentLink != route.get(route.size()-1) && routeIndex + 1 < route.size()){
                LOG.info("The vehicle " + this.getId() + "has crossed the end of the link");
                routeIndex++;
                LOG.info("The vehicle " + this.getId() + "allocated to the start of the next link");
                } else {
                    LOG.info("The vehicle " + this.getId() + " has reached its destination");
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
    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }
}
