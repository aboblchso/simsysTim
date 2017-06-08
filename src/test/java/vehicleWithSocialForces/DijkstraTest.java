package vehicleWithSocialForces;

import org.junit.Test;
import sun.nio.ch.Net;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.*;

/**
 * Created by volot on 08.06.2017.
 */
public class DijkstraTest {
    @Test
    public void calculateRoute() throws Exception {
    }

    @Test
    public void calculateRouteFromListOfNodes() throws Exception {
    }

    @Test
    public void reconstructPath() throws Exception {
    }

    @Test
    public void findNodeWithTheSmallestCostSoFar() throws Exception {
    }

    @Test
    public void expandGraph() throws Exception {
    }

    @Test
    public void collectLinksFromNode() throws Exception {
    }

    @Test
    public void returnLinkByNodesIds() throws Exception {
    }

    @Test
    public void getAllLinksOfTheNetwork(List<Link> allLinks) throws Exception {
        Network network = new Network();
        network.createLink(network.createNode(0,0,0), network.createNode(1,1,1),1);
        getAllLinksOfTheNetwork(allLinks);

        //assertThat(allLinks, hasItems());

    }

    @Test
    public void prepareQSet() throws Exception {
    }

}