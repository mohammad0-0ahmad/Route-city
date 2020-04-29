package Testpackage;
import org.junit.jupiter.api.Test;
import routeCity.Node;
import static org.junit.jupiter.api.Assertions.*;
public class NodeTest {
    @Test
    void addLinkedNode(){
        // Checking if it will creates a link (path) between two nodes.
        Node a = new Node("A", "First");
        Node b = new Node("B", "Second");
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
        Node c = new Node("C", "Third");
        b.addLinkedNode(c,10);
        // these two lines will be ignored because nodes are already connected with the target one.
        b.addLinkedNode(c,20);
        a.addLinkedNode(b,15);
        // Checking the amount of created links between these two nodes.
        assertEquals(1,a.getLinkedNodes().size());
        assertEquals(2,b.getLinkedNodes().size());
        assertEquals(1,c.getLinkedNodes().size());
    }
}
