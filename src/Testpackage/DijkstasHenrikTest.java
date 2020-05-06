package Testpackage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import routeCity.Node;
import routeCity.Algo;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the Dijkstras methods
 */
class DijkstasHenrikTest {
    Node a;
    Node b;
    Node c;
    Node d;
    Node[] nodes;

    /**
     * Creting a network to test
     */
    @BeforeEach
    void initialize(){
        a = new Node("A", "A");
        b = new Node("B", "B");
        c = new Node("C", "C");
        d = new Node("D", "D");
        nodes = new Node[]{a, b, c, d};
        a.addLinkedNode(b,1);
        a.addLinkedNode(c,2);
        a.addLinkedNode(d,5);
        b.addLinkedNode(c,1);
        c.addLinkedNode(d,1);

    }

    /**
     * Test if the getDistances of two different nodes is correct.
     */
    @Test
    void getDistancesTest(){
        int[] result = Algo.getDistances(nodes,0);
        //
        assertEquals(0,result[0]);
        assertEquals(1,result[1]);
        assertEquals(2,result[2]);
        assertEquals(5,result[3]);
        //
        result = Algo.getDistances(nodes,1);
        assertEquals(1,result[0]);
        assertEquals(0,result[1]);
        assertEquals(1,result[2]);
        assertEquals(999,result[3]);
        //
    }

    /**
     * Test if the main algorithm returns a correct message to the user
     */
    @Test
    void algoTest() {
        String message = Algo.algo(nodes,0,3);
        assertEquals("The smallest distance from A to D is 3 km. Please change bus at: C ",message);
    }


    /**
     * Test if the getConnections returns the correct integerlist and check if the addConnectionsToString return the correct String.
     */
    @Test
    void getConnectionsTest() {
        int[] closestNodeToReachThis = new int[]{0,0,1,2};
        ArrayList<Integer> expected = new ArrayList<>();
       expected.add(1); expected.add(2);
        ArrayList<Integer> resultList = Algo.getConnections(3, closestNodeToReachThis);
       assertEquals(expected,resultList);
        String message = Algo.addConnectionsToString(expected,nodes);
        assertEquals(" Please change bus at: B C ",message);

    }


}