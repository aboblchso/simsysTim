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
    final Map<Integer, Node> nodes = new HashMap<Integer, Node>();
    private final Map<Integer, Link> links = new HashMap<Integer, Link>();
    private final List<LinkInfo> linkInfos = new ArrayList<>();

    public Map<Integer, Link> getLinks() {
        return links;
    }

    public Node createNode (double x, double y, int nodeId){
        Node n = new Node(x, y, nodeId);
        this.nodes.put(nodeId, n);
        return n;
    }

    public Link createLink (Node from, Node to, int linkId){
        Link l = new Link(from, to, linkId);
        this.links.put(linkId, l);
        LinkInfo linkInfo = new LinkInfo();
        linkInfo.x0 = (float) (from.getX()* Simulation.SCALE);
        linkInfo.y0 = (float) (from.getY()* Simulation.SCALE);
        linkInfo.x1 = (float) (to.getX()* Simulation.SCALE);
        linkInfo.y1 = (float) (to.getY()* Simulation.SCALE);
        linkInfos.add(linkInfo);
        return l;
    }

    public void draw (PApplet p){
        for (LinkInfo linkInfo : this.linkInfos){
            p.line(linkInfo.x0, linkInfo.y0, linkInfo.x1, linkInfo.y1);
        }
    }

    // Innere classe - ist nur für
    private static final class LinkInfo {
        float x0, x1, y0, y1;

    }

}
