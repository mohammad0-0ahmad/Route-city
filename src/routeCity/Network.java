package routeCity;

import routeCity.Dijkstras.ShortestPath;

import java.util.ArrayList;

/**
 * It represents a network of nodes "bus stations".
 * Note: Network nodes should be connected together to form a closed network.
 */
public class Network implements NodesToNetwork {

    /* static variables & methods */
    /**
     * It holds some bus station names which are used to create the random network.
     */
    public static final String[] BUS_STATIONS = {"Kungsgatan", "Centralstationen", "Drottningtorget", "Hamngatan", "Vasaplatsen", "Sportarenan", "Flygplatsen", "Södra vägen", "Musikvägen", "Kulturvägen"};
    /**
     * Random network that fits the actual requirements.
     */
    private static final Network instance = new Network(Network.BUS_STATIONS, 3, 1, 10);

    /**
     * Distance unit that used to represent the weight of a path.
     */
    private static final String DISTANCE_UNIT = " km";

    /**
     * Getter method.
     *
     * @return the only network object. "singleton".
     */
    public static Network getInstance() {
        return instance;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*  >Member variables<  */
    /**
     * An array that store all network node. "bus stations in the actual case."
     */
    private Node[] nodes;

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
     *
     * @param maxPaths  maximum paths
     * @param nodesName bus stations names
     */
    private Network(String[] nodesName, int maxPaths, int minWeight, int maxWeight) {
        this.maxPaths = maxPaths;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        remake(nodesName, true);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*  >>>Member methods<<<  */
    /**
     * Getter method.
     *
     * @return the nodes array
     */
    public Node[] getNodes() {
        return nodes;
    }

    /**
     * It creates random paths "relations" between network nodes.
     */
    private void setRandomRelations() {
        // Refers to the status of the actual process.
        boolean isCompleted = false;
        // Converting nodes array to array list and reorder it randomly.
        ArrayList<Node> temp = getRandomOrderedNodes(nodes);
        // Creating paths between all nodes that make them all connected. "To make sure that network will be closed."
        for (int i = 0; i < temp.size() - 1; i++) {
            temp.get(i).addLinkedNode(temp.get(i + 1), getRandomWeight());
        }
        // Try to fill random paths between nodes so almost all nodes get maximum amount paths.
        while (!isCompleted) {
            temp = getPossibleNodesToLink(temp, maxPaths);
            // If there are no nodes able to get linked will change isCompleted value to true to be able to end the process.
            if (temp.size() <= 1) {
                isCompleted = true;
            } else {
                // Creating paths between nodes that are able to get connected.
                for (Node node : temp) {
                    // Getting the all node that are able to be connected with the actual node.
                    ArrayList<Node> possibleToLinkWithMe = getPossibleNodesToLink(temp, maxPaths, node);
                    // If there is a node able to be connected a path will be created in between.
                    if (possibleToLinkWithMe.size() > 0) {
                        node.addLinkedNode(possibleToLinkWithMe.get(0), getRandomWeight());
                    }
                }
            }
        }
    }

    /**
     * It returns a random weight that can be used as path weight between two nodes.
     *
     * @return a weight that is larger or equals than 1.
     */
    private int getRandomWeight() {
        if (minWeight > maxWeight) {
            System.err.println("minWeight value is greater than maxWeight value. The values will be switched.");
            int temp = maxWeight;
            maxWeight = minWeight;
            minWeight = temp;
        }
        int between = maxWeight - minWeight;
        int result = (int) (Math.round(Math.random() * between)) + minWeight;
        return Math.max(result, 1);
    }

    /**
     * It calls and implements Dijkstra's algorithm on the network.
     *
     * @param start start bus station "node".
     * @param end   end bus station "node".
     * @return string that explain the travel from start bus station to end one.
     */
    public String getShortestPath(Node start, Node end) {
        // Calling dijkstra's algorithm.
        ShortestPath shortestPathBetweenStartAndEnd = Dijkstras.getShortestPath(this, start, end);
        // Converting the result to string form.
        StringBuilder result = new StringBuilder();
        int pathWeight = shortestPathBetweenStartAndEnd.getWeight();
        ArrayList<Node> busStationsInBetween = shortestPathBetweenStartAndEnd.getNodesOnThePath();
        // In case start refers to the same object that end refers to.
        if (pathWeight == 0 && busStationsInBetween.size() == 1) {
            result.append("You are already in the desired bus station. ").append(start.getSymbol()).append(": ").append(start.getName());
        }
        // In case there are not bus stations "nodes" in between.
        else if (busStationsInBetween.size() == 2) {
            result.append("You can take direct bus from ").append(start.getSymbol()).append(": ").append(start.getName()).append(" to ").append(end.getSymbol()).append(": ").append(end.getName()).append(".\nThe distance in between is: ").append(pathWeight).append(DISTANCE_UNIT).append(".");
        }
        // In case were bus stations in between start and end one.
        else if (busStationsInBetween.size() > 2) {
            result.append("The distance between ").append(start.getSymbol()).append(": ").append(start.getName()).append(" and ").append(end.getSymbol()).append(": ").append(end.getName()).append(" is ").append(pathWeight).append(DISTANCE_UNIT).append(".");
            // Get extra details about the travel from start bus station to the end one.
            result.append("\nMore details:");
            for (int i = 0; i < busStationsInBetween.size() - 1; i++) {
                Node from = busStationsInBetween.get(i);
                Node to = busStationsInBetween.get(i + 1);
                // the following line adds more details about the travel form start node and end and the weight of every small path of the whole path.
                // ex: C1: Drottningtorget -- 3 mils --> D1: Hamngatan
                result.append("\n").append(from.getSymbol()).append(": ").append(from.getName()).append(" -- ").append(from.isConnectedWith(to)).append(DISTANCE_UNIT).append(" --> ").append(to.getSymbol()).append(": ").append(to.getName());
            }
        }
        // In case get other result.
        else {
            result.append("Could not find the way.");
        }
        return result.toString();
    }

    /**
     * It prints the network in the console. "in table format"
     */
    public void printNetwork() {
        System.out.println("----------------Bus network----------------\nThe numbers stands for the kilometers\nbetween the stations.");
        StringBuilder firstRow = new StringBuilder("\t");
        StringBuilder symbolsAndBusStations = new StringBuilder();
        StringBuilder rowsToPrint = new StringBuilder();
        for (Node rowNode : nodes) {
            symbolsAndBusStations.append(rowNode.getSymbol()).append(" : ").append(rowNode.getName()).append("\n");
            firstRow.append(rowNode.getSymbol()).append("\t");
            StringBuilder rowToPrint = new StringBuilder(rowNode.getSymbol() + "\t");
            for (Node columnNode : nodes) {
                int weightBetweenNodes = rowNode.isConnectedWith(columnNode);
                switch (weightBetweenNodes) {
                    // -1 means that the nodes is not connected.
                    case -1:
                        rowToPrint.append("-\t");
                        break;
                    // 0 means that rowNode and columnNode refers to the same node.
                    case 0:
                        rowToPrint.append("\t");
                        break;
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
     * Function to create the nodes and set the network OR to recreate new relations between the nodes of an existing network.
     *
     * @param nodesName bus station names
     * @param replace   will make the function recreates network node depending on nodesName value in case its value was true OR it will just remove nodes relations in case its value was false.
     */
    private void remake(String[] nodesName, boolean replace) {
        // replacing nodes array with new one.
        if (replace) {
            nodes = new Node[nodesName.length];
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new Node(getNodeSymbol(i), BUS_STATIONS[i]);
            }
        } else {
            // Clearing nodes relations.
            for (Node node : nodes) {
                node.getLinkedNodes().clear();
            }
        }
        // Creating random relations between nodes.
        setRandomRelations();
    }

    /**
     * It calls {@link #remake(String[], boolean)} to clear the relation between the actual network nodes.
     * Made it just to make calling the method easier without need to send parameters.
     */
    public void remake() {
        remake(null, false);
    }

    /**
     * Method to get the correct symbol for the station. It use the ASCII number and begin at "A" and add some numbers.
     * If a higher amount than 26 nodes is used it start over with an "A" but other numbers after.
     *
     * @param index nodes
     * @return the symbol name
     */
    private String getNodeSymbol(int index) {
        char letter = (char) (index % 26 + 65);
        return letter + "" + (index / 26 + 1);
    }
}