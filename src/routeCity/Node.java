package routeCity;

import java.util.ArrayList;

/**
 * It represent a node or vertex of a network.
 */
public class Node {
    /*  >Member variables<  */
    /**
     * Storing a symbol that represent the node. "to make it easier for user in console for example"
     */
    private String symbol;
    /**
     * The actual name of the node. can be for example bus station or city name etc.
     */
    private String name;
    /**
     * Storing paths that refer to the nodes which are connected with the actual node.
     */
    private ArrayList<Path> linkedNodes;

    /*  >>Constructors<<  */

    /**
     * Node Constructor.
     * @param symbol a symbol that represent the node.
     * @param name the name of the node.
     */
    public Node(String symbol,String name){
        this.symbol = symbol;
        this.name = name;
        linkedNodes = new ArrayList<>();
    }

    /*  >>>Member methods<<<  */

    /**
     * Getter method.
     * @return the symbol of the node.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Getter method.
     * @return the name of node.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method.
     * @return An arrayList of paths that node have with other nodes.
     */
    public ArrayList<Path> getLinkedNodes() {
        return linkedNodes;
    }

    /**
     * It adds a Path object to linkedNodes array list. see: {@link #linkedNodes} and {@link Path}.
     * It will not adding two paths that refer to the same node.
     * @param destinationNode  see: {@link Path#destinationNode}.
     * @param weight see: {@link Path#weight}.
     */
    public void addLinkedNode(Node destinationNode,int weight){
        if (destinationNode == null){
            System.err.println("The received node object is null.");
        }else{
            if (weight <= 0) {
                // printing warning.
                System.err.println("I can't create path between " + this.symbol + " & " + destinationNode.symbol + " because of weight value is equals or smaller than zero.");
            } else {
                // Checking if the node have already a path with the target node.
                boolean hasAlreadyPathWith = false;
                for (Path linkedNode : linkedNodes) {
                    if (linkedNode.getDestinationNode() == destinationNode) {
                        hasAlreadyPathWith = true;
                        break;
                    }
                }
                // Adding a path between the node is not connected yet.
                if (!hasAlreadyPathWith) {
                    linkedNodes.add(new Path(destinationNode, weight));
                    // Getting the size of the array list to know if the another node have already the path.
                    int size = destinationNode.getLinkedNodes().size();
                    // to avoid extra calling.
                    if (size == 0 || destinationNode.getLinkedNodes().get(size - 1).getDestinationNode() != this) {
                        destinationNode.addLinkedNode(this, weight);
                    }
                } else {
                    // printing warning.
                    System.err.println("Nodes " + this.symbol + " & " + destinationNode.symbol + " are already connected.");
                }
            }
        }
    }

    /**
     * It can be used to check if two nodes is connected OR to return the weight of the path that can be exist between two nodes.
     * @return 0 if the both references belong to the same node object OR -1 that means the node is not connected OR a number that refer to the weight between the nodes.
     */
     public int isConnectedWith(Node node){
         if (node == null){
             System.err.println("The received node object is null.");
             return -1;
         }
         // Checking if node parameter is the same object that used to call this method.
         if (this == node){
             return 0;
         }
         // Checking if the nodes is connected and return the weight if they are connected.
         for (Path path : linkedNodes) {
             if (path.destinationNode == node){
                 return path.getWeight();
             }
         }
        return -1;
     }

    /**
     * It represent the path between a node and target node."two nodes"
     */
    public class Path{
        /*  >Member variables<  */
        /**
         * It refer to the weight of the path like distance and weather traffic status in the path etc. Note!! In the actual version it represent just the distance between two nodes.
         */
        private int weight;
        /**
         * The destination node.
         */
        private Node destinationNode;

        /*  >>Constructors<<  */

        /**
         * Path Constructor.
         * @param destinationNode see: {@link #destinationNode}
         * @param weight see: {@link #weight}
         */
        private Path(Node destinationNode,int weight){
            this.weight = weight;
            this.destinationNode = destinationNode;
        }

        /*  >>>Member methods<<<  */

        /**
         * Getter method.
         * @return a node that is a destination node.
         */
        public Node getDestinationNode() {
            return destinationNode;
        }

        /**
         * Getter method.
         * @return an integer number that refer to weight of the path.
         */
        public int getWeight() {
            return weight;
        }
    }
}
