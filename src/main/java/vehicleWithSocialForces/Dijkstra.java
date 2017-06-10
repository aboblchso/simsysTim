package vehicleWithSocialForces;

import java.util.*;

/**
 * Created by volot on 18.05.2017.
 */
public class Dijkstra {

    private final Network network;
    private final Node nodeStart;
    private final Node nodeDestination;
    Map<String, MirrorNode> mirrorNodeMap = new HashMap<String, MirrorNode>();
    Map<String, String> accordanceMap = new HashMap<>();
    Map<String, MirrorNode> qMap = new HashMap<String, MirrorNode>();
    Map<String, MirrorNode> executedMirrorNodesMap = new HashMap<>();
    MirrorNode currentNode;
    MirrorNode mirrorNodeStart;
    MirrorNode mirrorNodeDestination;

    public List<Link> getRoute = new LinkedList<Link>();

    public Dijkstra(Network network, Node nodeStart, Node nodeDestination) {
        this.network = network;
        this.nodeStart = nodeStart;
        this.nodeDestination = nodeDestination;
    }

    public void calculateRoute (){


        // I should create the corresponding MAP between <Node, MirrorNode>
        // Also I should probably make the MirrorNode Comparable and override
        prepareQSet();
        while (currentNode != mirrorNodeDestination) {
            qMap.remove(currentNode.getNodeID());
            expandGraph(currentNode);
            currentNode = findNodeWithTheSmallestCostSoFar(qMap);
            executedMirrorNodesMap.put(currentNode.getNodeID(), currentNode);
        }
        List<String> mirrorNodePath = reconstructPath(mirrorNodeDestination);
        calculateRouteFromListOfNodes(mirrorNodePath);

        //  qList.put(nStart, )
    }

    public void calculateRouteFromListOfNodes(List<String> mirrorNodePath) {
        for (int i = 0; i < mirrorNodePath.size() - 1; i++){
            List<Link> allLinks = new LinkedList<>();
            getAllLinksOfTheNetwork(allLinks);
            Link link = returnLinkByNodesIds(accordanceMap.get(mirrorNodePath.get(i)),
                    accordanceMap.get(mirrorNodePath.get(i + 1)), allLinks);
            double oldWeight = link.getWeight();
            Random random = new Random();
            link.setWeight(oldWeight + (0.002*(random.nextBoolean() ? 1 : -1)));
            getRoute.add(link);
            System.out.println("added link to the route: " +
                    returnLinkByNodesIds(accordanceMap.get(mirrorNodePath.get(i)),
                            accordanceMap.get(mirrorNodePath.get(i+1)), allLinks).getLinkId());
        }
    }

    public List<String> reconstructPath(MirrorNode mirrorNodeDestination) {
//        System.out.println("node " + executedMirrorNodesMap.get(5).getNodeID() + " has predecessor " +
//        executedMirrorNodesMap.get(5).getPredecessorId());
        MirrorNode nodeToGetPredecessor = mirrorNodeDestination;
        List<String> mirrorNodePath = new LinkedList<>();
        while (nodeToGetPredecessor != null){
            mirrorNodePath.add(0, nodeToGetPredecessor.getNodeID());
            System.out.println(mirrorNodePath.size());
            nodeToGetPredecessor = executedMirrorNodesMap.get(nodeToGetPredecessor.getPredecessorId());
        }
        mirrorNodePath.add(0, mirrorNodeStart.getNodeID());
        System.out.println(mirrorNodePath);
        return mirrorNodePath;
    }

    public MirrorNode findNodeWithTheSmallestCostSoFar(Map<String, MirrorNode> qMap) {
        Double temporaryMinNodeCost = 1.0 / 0.0;
        Map<String, Double> mapOfCosts = new HashMap<>();
        MirrorNode nodeWithTheSmallestCost = null;
        for (Map.Entry<String, MirrorNode> entry : qMap.entrySet()){
            mapOfCosts.put(entry.getKey(), entry.getValue().getNodeCost());
        }
        List<Map.Entry<String, Double>> listOfCosts = new LinkedList<Map.Entry<String, Double>>(mapOfCosts.entrySet());

        Collections.sort(listOfCosts, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        nodeWithTheSmallestCost = qMap.get(listOfCosts.get(0).getKey());

        return nodeWithTheSmallestCost;
    }

    public void expandGraph(MirrorNode currentNode) {
        List<Link> allLinks = new LinkedList<>();
        List<Link> linksToExploreInThatStep = new LinkedList<>();
        getAllLinksOfTheNetwork(allLinks);
        collectLinksFromNode(currentNode, allLinks, linksToExploreInThatStep);
        System.out.println("==========The current node is " + currentNode.getNodeID() + "=============");
        System.out.println("already executed nodes: " + executedMirrorNodesMap.size());
        for (Link link : linksToExploreInThatStep){
            //System.out.println(mirrorNodeMap);
            System.out.println("Exploring the link " + link.getLinkId() + " to node " + link.getTo().getNodeId());
            double fromNodeCost = currentNode.getNodeCost();
            System.out.println("the cost of current node is " + fromNodeCost);
            double oldToNodeCost = mirrorNodeMap.get(link.getTo().getNodeId()).getNodeCost();
            System.out.println("the old cost of the node " + link.getTo().getNodeId() + " was " + oldToNodeCost);
            double newToNodeCost = fromNodeCost + link.getWeight();
            System.out.println("the weight of the corresponding link is " + link.getWeight());
            if (newToNodeCost < oldToNodeCost){
                mirrorNodeMap.get(link.getTo().getNodeId()).setNodeCost(newToNodeCost);
                System.out.println("the new cost of the Node " + link.getTo().getNodeId() + " is " + newToNodeCost);
                mirrorNodeMap.get(link.getTo().getNodeId()).setPredecessorId(currentNode.getNodeID());
                System.out.println("the new predecessor of the Node " + link.getTo().getNodeId() + " is " + currentNode.getNodeID());
            }
            else {
                System.out.println("this node doesn't belong to the shortest path");
            }
        }



    }

    public void collectLinksFromNode(MirrorNode currentNode, List<Link> allLinks, List<Link> linksToExploreInThatStep) {
        for (Link link : allLinks){
            if (link.getFrom().getNodeId().equals(currentNode.getNodeID())){
                linksToExploreInThatStep.add(link);

            }
        }
    }

    public Link returnLinkByNodesIds(String startNodeId, String endNodeId, List<Link> allLinks) {
        for (Link link : allLinks){
            if (link.getFrom().getNodeId().equals(startNodeId) && link.getTo().getNodeId().equals(endNodeId)){
                return link;

            }
        }
        return null;
    }

    public void getAllLinksOfTheNetwork(List<Link> allLinks) {
        for (Map.Entry<Integer, Link> links : network.getLinks().entrySet()){
            allLinks.add(links.getValue());
        }
    }


    public void prepareQSet() {
        for (Node node : network.nodesList){
            String nodeId = node.getNodeId();
            double nodeCost = 1.0 / 0.0;
            String predecessorId = null;
            MirrorNode mirrorNode = new MirrorNode(nodeId, nodeCost, predecessorId);
            accordanceMap.put(mirrorNode.getNodeID(), node.getNodeId());
            mirrorNodeMap.put(nodeId, mirrorNode);
            qMap.put(nodeId, mirrorNode);
            if (mirrorNode.getNodeID().equals(nodeStart.getNodeId())){
                mirrorNode.setNodeCost(0);
                currentNode = mirrorNode;
                mirrorNodeStart = mirrorNode;
            }

            else if (mirrorNode.getNodeID().equals(nodeDestination.getNodeId())){
                mirrorNodeDestination = mirrorNode;
            }
            /*
            List<Double> helpList = new LinkedList<>();
            helpList.add(nodeCost);
            helpList.add(Double.valueOf(predecessorId));
            qList.put(i, helpList);
            */
        }
    }





}
