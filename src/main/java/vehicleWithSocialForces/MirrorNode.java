package vehicleWithSocialForces;

/**
 * Created by volot on 01.06.2017.
 */
public class MirrorNode {
    private String nodeID;
    private double nodeCost;
    private String predecessorId;

    public MirrorNode(String nodeId, double nodeCost, String predecessorId) {
        this.nodeID = nodeId;
        this.nodeCost = nodeCost;
        this.predecessorId = predecessorId;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public void setNodeCost(double nodeCost) {
        this.nodeCost = nodeCost;
    }

    public void setPredecessorId(String predecessorId) {
        this.predecessorId = predecessorId;
    }

    public String getNodeID() {
        return nodeID;
    }

    public double getNodeCost() {
        return nodeCost;
    }

    public String getPredecessorId() {
        return predecessorId;
    }
}

