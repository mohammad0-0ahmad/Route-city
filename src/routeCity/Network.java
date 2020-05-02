package routeCity;
import java.util.ArrayList;

public class Network implements Dijkstras,NodesToNetwork{
    /* static variables & methods */
    public static String[] BUS_STATIONS = {"Kungsgatan", "Centralstationen", "Drottningtorget", "Hamngatan",
            "Vasaplatsen", "Sportarenan", "Flygplatsen", "Södra vägen", "Musikvägen", "Kulturvägen"};
    private static Network instance = new Network(2,3,Network.BUS_STATIONS);
    public static Network getInstance() {
        return instance;
    }

    /*  >Member variables<  */

    private Node[] nodes;

    /*  >>Constructors<<  */

    /**
     * Constructor with possibility to create a network with other names at the stations name, more stations or
     * other amount of paths than provided in the project.
     * 2 or 3 nodes
     * @param min minimun paths
     * @param max maximum paths
     * @param nodesName busstations names
     */
    private Network(int min,int max,String[] nodesName) {
        remake(max,nodesName);
    }
    /*  >>>Member methods<<<  */

    /**
     * It creates random paths "relations" between network nodes.
     * @param max amount maximum paths that a node will have with the other nodes.
     */
    private void setRandomRelations(int max) {
        // Refers to the status of the actual process.
        boolean isCompleted = false;
        // Converting nodes array to array list and reorder it randomly.
        ArrayList<Node> temp = getRandomOrderedNodes(nodes);
        // Creating paths between all nodes that make them all connected. "To make sure that network will be closed."
        for (int i = 0; i < temp.size()-1; i++){
            temp.get(i).addLinkedNode(temp.get(i+1),getRandomWeight(1,10));
        }
        // Try to fill random paths between nodes so almost all nodes get maximum amount paths.
        while (!isCompleted){
            temp = getPossibleNodesToLink(temp,max);
            // If there are no nodes able to get linked will change isCompleted value to true to be able to end the process.
            if (temp.size() <= 1){
                isCompleted = true;
            }else {
                // Creating paths between nodes that are able to get connected.
                for (Node node : temp) {
                    // Getting the all node that are able to be connected with the actual node.
                    ArrayList<Node> possibleToLinkWithMe = getPossibleNodesToLink(temp,max,node);
                    // If there is a node able to be connected a path will be created in between.
                    if (possibleToLinkWithMe.size() > 0){
                        node.addLinkedNode(possibleToLinkWithMe.get(0),getRandomWeight(1,10));
                    }
                }
            }
        }
    }

    /**
     * It returns a random weight that can be used as path weight between tow nodes.
     * @param min the minimal possible number of the returned value.
     * @param max the maximum possible number of the returned value.
     * @return a weight that is larger or equals than 1.
     */
    private static int getRandomWeight(int min, int max) {
        if (min > max){
            System.err.println("min value is greater than max value.");
            int temp = max;
            max = min;
            min = temp;
        }
        int between = max - min;
        int result = (int)(Math.round(Math.random() * between)) + min;
        return Math.max(result, 1);
    }

    private boolean isClosed() {
        return false;
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
     * Function to create the nodes and set the network.
     * @param max maximum of paths
     * @param nodesName busstation names
     */
    public void remake(int max,String[] nodesName) {
        nodes = new Node[nodesName.length];
        for (int i = 0; i <nodes.length; i++) {
            nodes[i] = new Node(getNodeSymbol(i), BUS_STATIONS[i]);
        }
        setRandomRelations(max);
    }

    /**
     * Method to get the correct symbol for the station. It use the ASCIInumber and begin at "A" and add some numbers.
     * If a higher amount than 26 nodes is used it start over with an "A" but other numbers after.
     * @param index nodes
     * @return the symbolname
     */
    private String getNodeSymbol(int index) {
        char letter = (char) (index % 26+65);
        return letter + ""+(index / 26 + 1);
    }
}
