package vehicleWithSocialForces;


import java.util.*;


public class Simulation {

    public static final double MAX_TIME = 1000;
    public static final double H = 0.1;
    public static final double SCALE = 25.0;
    public static final int STARTING_POINT_OF_THE_GRID = 2;
    public static final int NUMBER_OF_ROWS = 10;
    public static final int NUMBER_OF_COLUMNS = 10;
    public static final int STEP_OF_THE_GRID = 4;
    public static List<Node> listOfNodes = new ArrayList<>();
    public static List<String> listOfNodesIds = new ArrayList<>();
    public static List<Link> listOfLinks = new ArrayList<>();
    private static int numberOfRandomVehicles = 10000;

    private final Vis vis;
    private Map<String, Vehicle> vehs = new HashMap<>();

    public Simulation(Network network) {
         this.vis = new Vis(network);
    }

    public static void main(String[] args) {
        Network network = new Network();


        for (int i = 0; i < NUMBER_OF_COLUMNS; i++){
            for ( int j = 0; j < NUMBER_OF_ROWS; j++){
                Node node = network.createNode(STARTING_POINT_OF_THE_GRID + i*STEP_OF_THE_GRID,
                        STARTING_POINT_OF_THE_GRID + j*STEP_OF_THE_GRID, "node_" + j + "_" + i);
                listOfNodes.add(node);
                listOfNodesIds.add(node.getNodeId());
            }
        }
        System.out.println("The nodes generation is completed. " + listOfNodesIds.size() + " nodes are generated.");

        for (Node node : listOfNodes){
            for (Node anotherNode : listOfNodes){
                if (node.getX() == anotherNode.getX() && node.getY() == anotherNode.getY() + STEP_OF_THE_GRID){
                    Link link = network.createLink(node, anotherNode, (int) ((int) 100000000 * Math.random()));
                    Link reverseLink = network.createLink(anotherNode, node, (int) ((int) 100000000 * Math.random()));
                    listOfLinks.add(link);
                    listOfLinks.add(reverseLink);
                }
                else if (node.getY() == anotherNode.getY() && node.getX() == anotherNode.getX() + STEP_OF_THE_GRID){
                    Link link = network.createLink(node, anotherNode, (int) ((int) 100000000 * Math.random()));
                    Link reverseLink = network.createLink(anotherNode, node, (int) ((int) 100000000 * Math.random()));
                    listOfLinks.add(link);
                    listOfLinks.add(reverseLink);
                }
            }
        }

        System.out.println("The links generation is completed. " + listOfLinks.size() + " links are generated.");

        System.out.println("The network generation is completed!");



        /*
        Node node0 = network.createNode(10,10,0);
        Node node1 = network.createNode(30,10,1);

        Node node2 = network.createNode(30,30,2);
        Node node3 = network.createNode(50,30,3);
        Node node4 = network.createNode(50,50,4);
        Node node5 = network.createNode(50,80,5);
       // Node node6 = network.createNode(4,5,6);


        Link l0 = network.createLink(node0,node1,0);
        Link l0rev = network.createLink(node1,node0,1);
        Link l1 = network.createLink(node1,node2,2);
        Link l1rev = network.createLink(node2,node1,3);
        Link l2 = network.createLink(node2,node3,4);
        Link l2rev = network.createLink(node3,node2,5);
        Link l3 = network.createLink(node3,node4,6);
        Link l3rev = network.createLink(node4,node3,7);
        Link l4 = network.createLink(node4,node5,8);
        Link l4rev = network.createLink(node5,node4,9);
        Link l5 = network.createLink(node2,node0,12);
        Link l6 = network.createLink(node3, node5, 13);
*/
        /*
        List<Link> route0 = new ArrayList<>();
        route0.add(l0);
        route0.add(l1);
        route0.add(l2);
        route0.add(l3);
        route0.add(l4);
        route0.add(l5);

        List<Link> route1 = new ArrayList<>();
        route1.add(l0rev);
        route1.add(l0);
        route1.add(l5);
        */
        Simulation sim = new Simulation(network);
        System.out.println("Simulation is loaded.");
/*
        sim.add(new Vehicle(network,node0,node3,0,"test1"));
        sim.add(new Vehicle(network,node0,node5,0,"test2"));
        sim.add(new Vehicle(network,node5,node1,3,"test3"));
        sim.add(new Vehicle(network,node0,node3,5,"test4"));
        sim.add(new Vehicle(network,node4,node3,1,"test5"));
        sim.add(new Vehicle(network,node5,node1,0,"test6"));
*/
        addRandomVehicles(network, sim, numberOfRandomVehicles);
        sim.run();
    }

    private static void addRandomVehicles(Network network, Simulation sim, int numberOfRandomVehicles) {
        System.out.println("Creating " + numberOfRandomVehicles + " random vehicles");
        for (int i = 0; i < numberOfRandomVehicles; i++){
            String startNodeId = listOfNodesIds.get((int) (Math.random() * listOfNodesIds.size()));
            String finishNodeId = listOfNodesIds.get((int) (Math.random() * listOfNodesIds.size()));
            System.out.println("trying to create the route from the node " + startNodeId + " to the node " + finishNodeId);
            if (!startNodeId.equals(finishNodeId) &&
                    network.nodes.get(startNodeId).getX() <= STARTING_POINT_OF_THE_GRID + (STEP_OF_THE_GRID * (NUMBER_OF_COLUMNS / 2)) &&
                    network.nodes.get(startNodeId).getY() <= STARTING_POINT_OF_THE_GRID + (STEP_OF_THE_GRID * (NUMBER_OF_COLUMNS / 2)) &&
                    network.nodes.get(finishNodeId).getX() >= STARTING_POINT_OF_THE_GRID + (STEP_OF_THE_GRID * (NUMBER_OF_COLUMNS / 2)) &&
                    network.nodes.get(finishNodeId).getY() >= STARTING_POINT_OF_THE_GRID + (STEP_OF_THE_GRID * (NUMBER_OF_COLUMNS / 2))){

                createRandomDeparture(network, sim, startNodeId, finishNodeId, i * (int) (Math.random()*20000000));
            }

        }
    }

    private static void createRandomDeparture(Network network, Simulation sim, String startNodeId, String finishNodeId, int i) {
        double startTime = (Math.random() * (MAX_TIME - 999));
        String vehicleId = "Vehicle_" + startNodeId + "_to_" + finishNodeId + "_at_" + startTime + "_" + (int) Math.random()*10;
        Node startNode = network.nodes.get(startNodeId);
        Node finishNode = network.nodes.get(finishNodeId);
        sim.add(new Vehicle(network, startNode, finishNode, startTime, vehicleId));
        System.out.println("Random Vehicle " + vehicleId + " is created");
    }

    private void run() {
        double time = 0;



        while (time < MAX_TIME) {
            Iterator<Map.Entry<String, Vehicle>> iteratorVehicles = vehs.entrySet().iterator();
            while (iteratorVehicles.hasNext()) {
                Map.Entry<String, Vehicle> vehicleEntry = iteratorVehicles.next();
                if (!vehicleEntry.getValue().toBeRemovedAfterFinish){
                        vehicleEntry.getValue().update(this.vehs, time);
                } else {
                    iteratorVehicles.remove();
                    //this.remove(vehicleEntry.getValue());
                }
            }

            Iterator<Map.Entry<String, Vehicle>> iteratorVehiclesMove = vehs.entrySet().iterator();
            while (iteratorVehiclesMove.hasNext()) {
                Map.Entry<String, Vehicle> vehicleEntryMove = iteratorVehiclesMove.next();
                vehicleEntryMove.getValue().move();
            }
            //
            List<VehicleInfo> vInfos = new ArrayList<>();
            for (Map.Entry<String, Vehicle> vehicleEntry : vehs.entrySet()) {
               VehicleInfo vi = new VehicleInfo(vehicleEntry.getValue().getX(), vehicleEntry.getValue().getY(),
                       vehicleEntry.getValue().getPhi(), vehicleEntry.getValue().getLength(),
                       vehicleEntry.getValue().getWidth(), vehicleEntry.getValue().isInTheSimulation(),
                       vehicleEntry.getValue().getVx(), vehicleEntry.getValue().getVy(),
                       vehicleEntry.getValue().getRadius());
                       vInfos.add(vi);
            }
            this.vis.update(time, vInfos);

            time += H;

            try {
                Thread.sleep((long) (H * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void add(Vehicle v1) {
        this.vehs.put(v1.getId(), v1);
    }

    private void remove(Vehicle vehicle){
        this.vehs.remove(vehicle.getId());
    }
}
