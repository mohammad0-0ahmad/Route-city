package routeCity;
import java.util.ArrayList;
/**
 * It represents a network of nodes.
 */
public class Network implements Dijkstras,NodesToNetwork{

    /* static variables & methods */
    /**
     * It holds some bus station names which are used to create the random network.
     */
    public static final String[] BUS_STATIONS = {"Kungsgatan", "Centralstationen", "Drottningtorget", "Hamngatan", "Vasaplatsen", "Sportarenan", "Flygplatsen", "Södra vägen", "Musikvägen", "Kulturvägen"};
    /**
     * Random network that fits the actual requirements.
     */
    private static final Network instance = new Network(Network.BUS_STATIONS,3,1,10);

    /**
     * Getter method.
     * @return the only network object. "singleton".
     */
    public static Network getInstance() {
        return instance;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*  >Member variables<  */

    private Node[] nodes;
    /**
     * Getter method.
     * @return the nodes array
     */
    public Node[] getNodes() {
        return nodes;
    }
    /**
     * Amount maximum paths that a node has with the other nodes. Note: Its value should be greater or equals to 2 to be able to create a nodes network. "Should all nodes be connected together."
     */
    private final int maxPaths;
    /**
     * Minimum weight of a path. Note: Path weight will not be less than 1 even if this variable hold a value less than 1.
     */
    private int minWeight;
    /**
     * Maximum weight of a path.
     */
    private int maxWeight;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*  >>Constructors<<  */

    /**
     * Constructor with possibility to create a network with other names at the stations name, more stations or
     * other amount of paths than provided in the project.
     * 2 or 3 nodes
     * @param maxPaths maximum paths
     * @param nodesName busstations names
     */
    private Network(String[] nodesName,int maxPaths,int minWeight,int maxWeight) {
        this.maxPaths = maxPaths;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        remake(nodesName,true);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*  >>>Member methods<<<  */

    /**
     * It creates random paths "relations" between network nodes.
     */
    private void setRandomRelations() {
        // Refers to the status of the actual process.
        boolean isCompleted = false;
        // Converting nodes array to array list and reorder it randomly.
        ArrayList<Node> temp = getRandomOrderedNodes(nodes);
        // Creating paths between all nodes that make them all connected. "To make sure that network will be closed."
        for (int i = 0; i < temp.size()-1; i++){
            temp.get(i).addLinkedNode(temp.get(i+1),getRandomWeight());
        }
        // Try to fill random paths between nodes so almost all nodes get maximum amount paths.
        while (!isCompleted){
            temp = getPossibleNodesToLink(temp,maxPaths);
            // If there are no nodes able to get linked will change isCompleted value to true to be able to end the process.
            if (temp.size() <= 1){
                isCompleted = true;
            }else {
                // Creating paths between nodes that are able to get connected.
                for (Node node : temp) {
                    // Getting the all node that are able to be connected with the actual node.
                    ArrayList<Node> possibleToLinkWithMe = getPossibleNodesToLink(temp,maxPaths,node);
                    // If there is a node able to be connected a path will be created in between.
                    if (possibleToLinkWithMe.size() > 0){
                        node.addLinkedNode(possibleToLinkWithMe.get(0),getRandomWeight());
                    }
                }
            }
        }
    }

    /**
     * It returns a random weight that can be used as path weight between tow nodes.
     * @return a weight that is larger or equals than 1.
     */
    private int getRandomWeight() {
        if (minWeight > maxWeight){
            System.err.println("minWeight value is greater than maxWeight value. The values will be switched.");
            int temp = maxWeight;
            maxWeight = minWeight;
            minWeight = temp;
        }
        int between = maxWeight - minWeight;
        int result = (int)(Math.round(Math.random() * between)) + minWeight;
        return Math.max(result, 1);
    }

    public String getShortestPath(Node start, Node end) {
        return null;
    }

    /**
     * It prints the network in the console. "in table format"
     */
    public void printNetwork() {
        StringBuilder firstRow = new StringBuilder("\t");
        StringBuilder symbolsAndBusStations = new StringBuilder();
        StringBuilder rowsToPrint = new StringBuilder();
        for (Node rowNode : nodes) {
            symbolsAndBusStations.append(rowNode.getSymbol()).append(" : ").append(rowNode.getName()).append("\n");
            firstRow.append(rowNode.getSymbol()).append("\t");
            StringBuilder rowToPrint = new StringBuilder(rowNode.getSymbol() + "\t");
            for (Node columnNode : nodes) {
                int weightBetweenNodes = rowNode.isConnectedWith(columnNode);
                switch (weightBetweenNodes){
                    // -1 means that the nodes is not connected.
                    case -1:
                        rowToPrint.append("-\t");break;
                    // 0 means that rowNode and columnNode refers to the same node.
                    case 0:
                        rowToPrint.append("\t");break;
                    // adding the weight of the path that is exist between the rowNode and columnNode.
                    default:
                        rowToPrint.append(weightBetweenNodes).append("\t");
                }
            }
            rowsToPrint.append(rowToPrint.append("\n"));
        }
        System.out.println(firstRow + "\n" + rowsToPrint + "\n" + symbolsAndBusStations);
    }

    /**
     * Function to create the nodes and set the network OR to recreate new relations between the nodes of an exist network.
     * @param nodesName bus station names
     * @param replace will make the function recreates network node depending on nodesName value in case its value was true OR it will just remove nodes relations in case its value was false.
     */
    private void remake(String[] nodesName,boolean replace) {
        // replacing nodes array with new one.
        if (replace) {
            nodes = new Node[nodesName.length];
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new Node(getNodeSymbol(i), BUS_STATIONS[i]);
            }
        }else {
            // Clearing nodes relations.
            for (Node node:nodes) {
                node.getLinkedNodes().clear();
            }
        }
        // Creating random relations between nodes.
        setRandomRelations();
        if (!replace) {
            System.out.println("The network is remaked in a randomed way.");
        }
    }

    /**
     * It calls {@link #remake(String[],boolean)} to clear the relation between the actual network nodes.
     * Made it just to make calling the method easier without need to send parameters.
     */
    public void remake(){
        remake(null,false);
    }

    /**
     * Method to get the correct symbol for the station. It use the ASCII number and begin at "A" and add some numbers.
     * If a higher amount than 26 nodes is used it start over with an "A" but other numbers after.
     * @param index nodes
     * @return the symbolname
     */
    private String getNodeSymbol(int index) {
        char letter = (char) (index % 26+65);
        return letter + ""+(index / 26 + 1);
    }
}