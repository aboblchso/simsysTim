package vehicleWithSocialForces;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by volot on 28.04.2017.
 */
public class Node {

    // x and y were FINAL before, but I needed a setter

    private final double x;
    private final double y;
    private final String nodeId;

    private final List<Link> outLinks = new LinkedList<Link>();
    private final List<Link> inLinks = new LinkedList<Link>();

    public Node(double x, double y, String nodeId) {
        this.x = x;
        this.y = y;
        this.nodeId = nodeId;
    }

    public void addOutLink(Link l){
        this.outLinks.add(l);
    }

    public void addInLink (Link l){
        this.inLinks.add(l);
    }

    public double getX() {
        return x;
    }

    public double getY() { return y;
    }


    public String getNodeId() {
        return nodeId;
    }
}
