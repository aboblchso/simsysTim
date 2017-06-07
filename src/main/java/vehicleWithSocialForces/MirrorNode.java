package vehicleWithSocialForces;

/**
 * Created by volot on 01.06.2017.
 */
public class MirrorNode {
    private Integer nodeID;
    private double nodeCost;
    private Integer predecessorId;

    public MirrorNode(Integer nodeId, double nodeCost, Integer predecessorId) {
        this.nodeID = nodeId;
        this.nodeCost = nodeCost;
        this.predecessorId = predecessorId;
    }

    public void setNodeID(Integer nodeID) {
        this.nodeID = nodeID;
    }

    public void setNodeCost(double nodeCost) {
        this.nodeCost = nodeCost;
    }

    public void setPredecessorId(Integer predecessorId) {
        this.predecessorId = predecessorId;
    }

    public Integer getNodeID() {
        return nodeID;
    }

    public double getNodeCost() {
        return nodeCost;
    }

    public Integer getPredecessorId() {
        return predecessorId;
    }
}

