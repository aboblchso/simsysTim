package vehicleWithSocialForces;

/**
 * Created by volot on 27.04.2017.
 */
public class Link {


    private Node from;
    private Node to;
    private final int linkId;
    private final double weight;
    double rotatedDx;
    double rotatedDy;

    public Link(Node from, Node to, int linkId) {
        this.from = from;
        this.to = to;
        this.linkId = linkId;
        double dx = (to.getX() - from.getX());
        double dy = (to.getY() - from.getY());
        this.weight = Math.sqrt((dx*dx) + (dy*dy));
        this.rotatedDx = - dy;
        this.rotatedDy =   dx;
    }


    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public int getLinkId() {
        return linkId;
    }

    public double getWeight() {
        return weight;
    }

    public boolean hasVehicleReachedTheEndOfTheLink(Vehicle vehicle) {
        // here we should implement the method to prove if the vehicle passed the end of the link
        // first rotate the link (we need a method for this
        double x0 = rotatedDx;
        double y0 = rotatedDy;
        double x1 = vehicle.getX() - to.getX();
        double y1 = vehicle.getY() - to.getY();
        double crossProduct = ( x0 * y1 ) - ( x1 * y0 );
        if (crossProduct < 0) return true;
        else return false;

    }
/*
    // here we get the direction [x, y] of the rotated around the "to" node vector
    public double[] rotatedDirection(){
        double[] rotatedDirection = new double[2];
        rotatedDirection[0] = this.getDirection()[1];
        rotatedDirection[1] = - this.getDirection()[0];
        return rotatedDirection;
    }

    // this method returns the direction [x, y] of any link based on "from"/"to" coordinates
    public double[] getDirection () {
        double[] getDirections = new double[2];
        getDirections[0] = this.getTo().getX() - this.getFrom().getX();
        getDirections[1] = this.getTo().getY() - this.getFrom().getY();
        return getDirections;
    }

    public double[] pointerToVehicle(Vehicle vehicle){
        double pointer[] = new double[2];
        pointer[0] = vehicle.getX() - to.getX();
        pointer[1] = vehicle.getY() - to.getY();
        return pointer;
    }


    public static void main(String[] args) {

            Node from = new Node(1, 34, 1);
            Node to = new Node(0, 2, 2);
            Link link = new Link(from,to,1);
            System.out.println(link.getDirection()[0]);
    }

*/
}
