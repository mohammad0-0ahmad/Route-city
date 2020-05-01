package routeCity;

import java.util.ArrayList;

public class Network implements Dijkstras {
    /* static variables & methods */
    public static String[] BUS_STATIONS = {"Kungsgatan", "Centralstationen", "Drottningtorget", "Hamngatan",
            "Vasaplatsen", "Sportarenan", "Flygplatsen", "Södra vägen", "Musikvägen", "Kulturvägen"};
    private static Network instance = new Network(2,3,Network.BUS_STATIONS);
    private ArrayList<Node> listToRandomFrom = new ArrayList<Node>();
    private static Node fromNode;
    private static Node toNode;
    private static int fromNodePositionInArray;
    private static int toNodePositionInArray;
    private int minNodes;
    private int maxNodes;
    private int minWeight = 1;
    private int maxWeight = 10;
    public static Network getInstance() {
        return instance;
    }


    /*  >Member variables<  */

    public Node[] nodes;

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
        this.minNodes = min;
        this.maxNodes = max;
        remake(nodesName);
        printNetwork();
    }
    /*  >>>Member methods<<<  */

        private void setRandomRelations() {
            setFirstRoundOfConnections();
            proceedRandomProcess();
        }
        private void setFirstRoundOfConnections() {
            prepareListWithNodesNotFullyConnected();
            fromNode = getRandomNode();
            fromNodePositionInArray = getArrayPosition(fromNode);
            int startNodePositionInArray = fromNodePositionInArray;
            listToRandomFrom.remove(fromNode);
            while (listToRandomFrom.size() > 0) {
                toNode = getRandomNode();
                toNodePositionInArray = getArrayPosition(toNode);
                listToRandomFrom.remove(toNode);
                nodes[fromNodePositionInArray].addLinkedNode(nodes[toNodePositionInArray],getRandomWeight());
                fromNode = toNode;
                fromNodePositionInArray = toNodePositionInArray;
            }
            nodes[fromNodePositionInArray].addLinkedNode(nodes[startNodePositionInArray],getRandomWeight());
        }
        private void proceedRandomProcess() {
            prepareListWithNodesNotFullyConnected();
            while (listToRandomFrom.size()>maxNodes) {
                prepareListWithNodesNotFullyConnected();
                fromNode = getRandomNode();
                fromNodePositionInArray = getArrayPosition(fromNode);
                listToRandomFrom.remove(fromNode);
                toNode = getRandomNode();
                toNodePositionInArray = getArrayPosition(toNode);
                listToRandomFrom.remove(toNode);
                if (fromNode.isConnectedWith(toNode)!=0) {
                    nodes[fromNodePositionInArray].addLinkedNode(nodes[toNodePositionInArray], getRandomWeight());
                }
            }
        }
        private void prepareListWithNodesNotFullyConnected() {
            listToRandomFrom.clear();
            for (Node node:nodes) {
                if (node.getLinkedNodes().size()<maxNodes) {
                    listToRandomFrom.add(node);
                }
            }
        }


        private int getArrayPosition(Node nodeToSearch) {
            int i;
            for (i = 0; i < nodes.length; i++) {
                if (nodes[i].getName().equals(nodeToSearch.getName())) {
                    break;
                }
            }
            return i;
        }

        private Node getRandomNode() {
            return listToRandomFrom.get((int) (Math.random() * listToRandomFrom.size()));
        }
    /**
     * It returns a random weight that can be used as path weight between tow nodes.
     * @param min the minimal possible number of the returned value.
     * @param max the maximal possible number of the returned value.
     * @return a weight that is larger or equals than 1.
     */
    private int getRandomWeight() {
        if (minWeight > maxWeight){
            System.err.println("min value is greater than max value.");
            int temp = maxWeight;
            maxWeight = minWeight;
            minWeight = temp;
        }
        int between = maxWeight - minWeight;
        int result = (int)(Math.round(Math.random() * between)) + minWeight;
        if (result < 1){
            return 1;
        }
        return result ;
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
     * @param min minimum of paths
     * @param max maximum of paths
     * @param nodesName busstation names
     */
    public void remake(String[] nodesName) {
        nodes = new Node[nodesName.length];
        for (int i = 0; i <nodes.length; i++) {
            nodes[i] = new Node(getNodeSymbol(i), BUS_STATIONS[i]);
        }
        setRandomRelations();
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
