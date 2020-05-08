package testPackage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import routeCity.Dijkstras.*;
import routeCity.Network;
import routeCity.Node;

import static routeCity.Dijkstras.*;
import static org.junit.jupiter.api.Assertions.*;

public class DijkstrasTest {
    // Node objects used during tests.
    Node a;
    Node b;
    Node c;
    Node d;
    Node[] nodes;

    @BeforeEach
    void initialize() {
        a = new Node("A", "First");
        b = new Node("B", "Second");
        c = new Node("C", "Third");
        d = new Node("D", "Fourth");
        nodes = new Node[]{a, b, c, d};
    }

    @Test
    void getShortestPathTest() {
        //Note: Could not test this function 100% because of there are just one random network instance.

        // Testing general cases.
        Network network = Network.getInstance();
        Node start = network.getNodes()[0];
        // Sending the same node as start and end node.
        ShortestPath result = getShortestPath(network, start, start);
        assertEquals(0, result.getWeight());
        assertEquals(1, result.getNodesOnThePath().size());
        // Sending node that doesn't belong to the network.
        result = getShortestPath(network, start, new Node("A", "Does not belong to the network"));
        assertNull(result);
        // Sending null objects.
        result = getShortestPath(null, start, start);
        assertNull(result);
        result = getShortestPath(network, null, start);
        assertNull(result);
        result = getShortestPath(network, start, null);
        assertNull(result);
    }


       /* the following network represent the tested network.
                1
             A------B
             | \    |
           5 |   \2 | 1
             |    \ |
             D------C
                1
         */

    /* Commented because function modifier is private.
    @Test
    void getWeightsTableTest(){
        // Create the paths between the nodes.
        a.addLinkedNode(b,1);
        a.addLinkedNode(c,2);
        a.addLinkedNode(d,5);
        b.addLinkedNode(c,1);
        c.addLinkedNode(d,1);
        // Checking the returned table "matrix" by passing a as start node.
        int[][] result = Dijkstras.getWeightsTable(nodes,a);
        // Checking first row of the table.
        assertEquals(0,result[0][1]);
        assertEquals(0,result[0][2]);
        // Checking second row of the table.
        assertEquals(1,result[1][1]);
        assertEquals(0,result[1][2]);
        // Checking third row of the table.
        assertEquals(2,result[2][1]);
        assertEquals(0,result[2][2]);
        // Checking fourth row of the table.
        assertEquals(3,result[3][1]);
        assertEquals(2,result[3][2]);
        // Checking the returned table "matrix" by passing c as start node.
        result = getWeightsTable(nodes,c);
        // Checking first row of the table.
        assertEquals(2,result[0][1]);
        assertEquals(2,result[0][2]);
        // Checking second row of the table.
        assertEquals(1,result[1][1]);
        assertEquals(2,result[1][2]);
        // Checking third row of the table.
        assertEquals(0,result[2][1]);
        assertEquals(2,result[2][2]);
        // Checking fourth row of the table.
        assertEquals(1,result[3][1]);
        assertEquals(2,result[3][2]);
    }
    @Test
    public void getNextNodeToVisitTest() {
        ArrayList<Node> unvisitedNodes = new ArrayList<>(Arrays.asList(nodes));
        // prepare weightTable that can be tested.
        int[][] weightTable = {{0,0,0},{1, INT_INFINITY,-1},{2, INT_INFINITY,-1},{3, INT_INFINITY,-1}};
        // 0 because of the first row have the lowest value. "the value in the middle of the row."
        assertEquals(0, getNextNodeIndexToVisit(nodes, unvisitedNodes,weightTable));
    }

    @Test
    void checkLinkedNodesTest(){
        //  Create the paths between the nodes.
        a.addLinkedNode(b,1);
        a.addLinkedNode(c,2);
        a.addLinkedNode(d,5);
        b.addLinkedNode(c,1);
        c.addLinkedNode(d,1);
        // Turning nodes array into array list to be able to call checkLinkedNodes method.
        ArrayList<Node> nodesAsList = new ArrayList<>(Arrays.asList(nodes));
        ArrayList<Node> unvisitedNodes = new ArrayList<>(nodesAsList);
        //  prepare weightTable that can be tested.
        int[][] weightTable = {{0,0,0},{1, INT_INFINITY,-1},{2, INT_INFINITY,-1},{3, INT_INFINITY,-1}};
        // Checking the weightTable after calling the method.
        checkLinkedNodes(nodesAsList,0,unvisitedNodes,weightTable);
        // Checking second row of the table.
        assertEquals(1,weightTable[1][1]);
        assertEquals(0,weightTable[1][2]);
        // Checking third row of the table.
        assertEquals(2,weightTable[2][1]);
        assertEquals(0,weightTable[2][2]);
        // Checking fourth row of the table.
        assertEquals(5,weightTable[3][1]);
        assertEquals(0,weightTable[3][2]);
    }
    */
}