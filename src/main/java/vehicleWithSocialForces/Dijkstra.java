package vehicleWithSocialForces;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by volot on 18.05.2017.
 */
public class Dijkstra {

    private final Network network;
    private final Node nStart;
    private final Node nDestination;
    Map<Integer, List<Double>> qList = new HashMap<>();

    public List<Link> getRoute;

    public Dijkstra(Network network, Node nStart, Node nDestination) {
        this.network = network;
        this.nStart = nStart;
        this.nDestination = nDestination;
    }

    public void calculateRoute (){
        for (int i=0; i < network.nodes.size(); i++){
            Node node = network.nodes.get(i);
            Integer nodeId = node.getNodeId();
            double nodeCost = 1.0 / 0.0;
            Integer predecessorId = null;
            List<Double> helpList = new LinkedList<>();
            helpList.add(nodeCost);
            helpList.add(Double.valueOf(predecessorId));
            qList.put(i, helpList);
        }

      //  qList.put(nStart, )

    }

}
