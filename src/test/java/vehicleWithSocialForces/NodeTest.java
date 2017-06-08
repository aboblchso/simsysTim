package vehicleWithSocialForces;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by volot on 08.06.2017.
 */
public class NodeTest {
    @Test
    public void addOutLink() throws Exception {
    }

    @Test
    public void addInLink() throws Exception {
    }

    @Test
    public void getX() throws Exception {
        Node node = new Node(0.002,500,1);
        assertEquals(0, node.getX(),0.01);
    }

    @Test
    public void getY() throws Exception {
    }

    @Test
    public void getNodeId() throws Exception {
    }

}