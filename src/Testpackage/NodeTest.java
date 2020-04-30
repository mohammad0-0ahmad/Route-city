package Testpackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import routeCity.Node;
import static org.junit.jupiter.api.Assertions.*;
public class NodeTest {
    // Node objects used during tests.
    Node a;
    Node b;
    Node c;
    @BeforeEach
    void initialize(){
        a = new Node("A", "First");
        b = new Node("B", "Second");
        c = new Node("C", "Third");
    }
    @Test
    void addLinkedNodeTest(){
        // Checking if it will creates a link (path) between two nodes.
        a.addLinkedNode(b,15);
        assertEquals(b,a.getLinkedNodes().get(0).getDestinationNode());
        // Checking if the path will be reflected in the another node "The two nodes know that there is a path between each other".
        assertEquals(a,b.getLinkedNodes().get(0).getDestinationNode());
        // Checking the amount of created links between these two nodes.
        assertEquals(1,a.getLinkedNodes().size());
        assertEquals(1,b.getLinkedNodes().size());
        // Checking the value of weight.
        assertEquals(15,a.getLinkedNodes().get(0).getWeight());
        assertEquals(15,b.getLinkedNodes().get(0).getWeight());

        // Trying have a second path between same nodes.
        b.addLinkedNode(c,10);
        // these two lines will be ignored because nodes are already connected with the target one.
        b.addLinkedNode(c,20);
        a.addLinkedNode(b,15);
        // Checking the amount of created links between these two nodes.
        assertEquals(1,a.getLinkedNodes().size());
        assertEquals(2,b.getLinkedNodes().size());
        assertEquals(1,c.getLinkedNodes().size());
        // Try to have path with negative or zero value.
        a.addLinkedNode(c,-10);
        a.addLinkedNode(c,0);
        // Try to have path with null node.
        a.addLinkedNode(null,16);
        assertEquals(1,a.getLinkedNodes().size());
    }

    @Test
    void isConnectedWithTest(){
        // Create bath between a and b objects.
        a.addLinkedNode(b,15);
        // Checking if it returns the weight between the nodes.
        assertEquals(15,a.isConnectedWith(b));
        assertEquals(15,b.isConnectedWith(a));
        // Checking if it returns zero if the references belong to the same object.
        assertEquals(0,a.isConnectedWith(a));
        // Checking if it returns -1 if the nodes haven't a path in between.
        assertEquals(-1,a.isConnectedWith(c));
        // Checking if it connected with a null node.
        assertEquals(-1,a.isConnectedWith(null));
    }
}
