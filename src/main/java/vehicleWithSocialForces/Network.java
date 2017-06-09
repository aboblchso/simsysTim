package vehicleWithSocialForces;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by volot on 28.04.2017.
 */
public class Network{
    final Map<String, Node> nodes = new HashMap<String, Node>();
    public final List<Node> nodesList = new ArrayList<>();
    private final Map<Integer, Link> links = new HashMap<Integer, Link>();
    private final List<LinkInfo> linkInfoArrayList = new ArrayList<>();
    private final List<NodeInfo> nodeInfoArrayList = new ArrayList<>();

    public Map<Integer, Link> getLinks() {
        return links;
    }

    public Node createNode (double x, double y, String nodeId){
        Node n = new Node(x, y, nodeId);
        this.nodesList.add(n);
        this.nodes.put(nodeId, n);
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.x = (float) (x * Simulation.SCALE);
        nodeInfo.y = (float) (y * Simulation.SCALE);
        nodeInfoArrayList.add(nodeInfo);
        return n;
    }

    public Link createLink (Node from, Node to, int linkId){
        Link newLink = new Link(from, to, linkId);
        this.links.put(linkId, newLink);
        LinkInfo linkInfo = new LinkInfo();
        linkInfo.x0 = (float) (from.getX()* Simulation.SCALE);
        linkInfo.y0 = (float) (from.getY()* Simulation.SCALE);
        linkInfo.x1 = (float) (to.getX()* Simulation.SCALE);
        linkInfo.y1 = (float) (to.getY()* Simulation.SCALE);
        linkInfoArrayList.add(linkInfo);
        return newLink;
    }

    public void draw (PApplet p){
        for (LinkInfo linkInfo : this.linkInfoArrayList){
            p.line(linkInfo.x0, linkInfo.y0, linkInfo.x1, linkInfo.y1);
        }
       /*
        for (NodeInfo nodeInfo : this. nodeInfoArrayList){
            p.ellipse(nodeInfo.x, nodeInfo.y, 5, 5);
        }
        */
    }

    // Innere classe - ist nur für
    private static final class LinkInfo {
        float x0, x1, y0, y1;

    }

    private static final class NodeInfo {
        float x,y;
    }

}
