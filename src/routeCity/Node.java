package routeCity;

import java.util.ArrayList;

public class Node {
    /*  >Member variables<  */
    private String symbol;
    private String name;
    private ArrayList<Path> linkedNodes;

    /*  >>Constructors<<  */
    public Node(String symbol,String name){}

    /*  >>>Member methods<<<  */

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Path> getLinkedNodes() {
        return linkedNodes;
    }

    public void addLinkedNode(Node destinationNode,int weight){

    }

    public class Path{
        /*  >Member variables<  */

        private int weight;
        private Node destinationNode;

        /*  >>Constructors<<  */

        private Path(Node destinationNode,int weight){

        }

        /*  >>>Member methods<<<  */

        public Node getDestinationNode() {
            return destinationNode;
        }

        public int getWeight() {
            return weight;
        }
    }
}
