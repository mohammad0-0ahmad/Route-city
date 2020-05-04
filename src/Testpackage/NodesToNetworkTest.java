package Testpackage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import routeCity.Node;
import routeCity.NodesToNetwork;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class NodesToNetworkTest implements NodesToNetwork {
    // Node objects used during tests.
    Node a;
    Node b;
    Node c;
    Node d;
    Node[] nodes;
    @BeforeEach
    void initialize(){
        a = new Node("A", "First");
        b = new Node("B", "Second");
        c = new Node("C", "Third");
        d = new Node("D", "Fourth");
        nodes = new Node[]{a, b, c, d};
    }

    /**
     * Test checks the following method {@link #getPossibleNodesToLink(ArrayList, int)}
     */
    @Test
    void getRandomOrderedNodesTest(){
        // Calling the method to test it and checking if size of they "nodes and returned array list" both is equals.
        ArrayList<Node> nodesAL = getRandomOrderedNodes(nodes);
        assertEquals(nodes.length,nodesAL.size());
        // checking if the returned array list is reordered randomly.
        // assuming that it is not reordered.
        boolean isShuffled = false;
        // have while loop because it can happens that the array list have the same order and it's ok but should also return mostly reordered array list.
        while (!isShuffled && nodesAL.size()> 1){
            for (int i = 0; i<nodes.length;i++) {
                if (nodes[i] !=nodesAL.get(i)){
                    isShuffled = true;
                    break;
                }
            }
            // recalling the method to prove that it can return reordered data.
            if(!isShuffled){
                nodesAL = getRandomOrderedNodes(nodes);
            }
        }
        // checking if it return reordered data.
        assertTrue(isShuffled);
    }

    /**
     * Test checks the following methods: {@link #getPossibleNodesToLink(ArrayList, int, Node)} and {@link #getPossibleNodesToLink(ArrayList, int)}.
     */
    @Test
    void getPossibleNodesToLinkTest(){
        // testing sending 0 as maxAmountLinks.
        ArrayList<Node> nodesAL = new ArrayList<>(Arrays.asList(nodes));
        ArrayList<Node> result = getPossibleNodesToLink(nodesAL,0);
        assertEquals(0,result.size());
        // testing returned result after adding a path between node a and b with different values of maxAmountLinks.
        a.addLinkedNode(d,10);
        result = getPossibleNodesToLink(nodesAL,1);
        assertEquals(2,result.size());
        result = getPossibleNodesToLink(nodesAL,4);
        assertEquals(4,result.size());
        // checking if it returns ordered array depending on amount links have node with other nodes.
        assertEquals(a,result.get(2));
        assertEquals(d,result.get(3));
        // checking the overrider version of the function.
        // checking the amount of nodes that are able to be connected with node a.
        assertEquals(2,getPossibleNodesToLink(result,2,a).size());
        // checking the amount of nodes that are able to be connected with null object.
        assertEquals(0,getPossibleNodesToLink(result,2,null).size());
        // checking the amount of nodes that are able to be connected with a node that doesn't belong to the same network.
        assertEquals(0,getPossibleNodesToLink(result,2,new Node("n1","A node don't belong to the array")).size());
        // checking the original method again.
        // checking the possible node that can be connected together after connecting a with all other nodes.
        a.addLinkedNode(c,10);
        a.addLinkedNode(b,5);
        assertEquals(3,getPossibleNodesToLink(result,5).size());
        // checking the possible node that can be connected with node a after connecting it with all other nodes. "here different can be noticed between they both"
        assertEquals(0,getPossibleNodesToLink(result,5,a).size());
    }
}
