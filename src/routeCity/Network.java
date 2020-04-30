package routeCity;

public class Network implements Dijkstras {
    /* static variables & methods */
    public static String[] BUS_STATIONS = {"Kungsgatan", "Centralstationen", "Drottningtorget", "Hamngatan",
            "Vasaplatsen", "Sportarenan", "Flygplatsen", "Södra vägen", "Musikvägen", "Kulturvägen"};
    private static Network instance = new Network(2,3,Network.BUS_STATIONS);
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
    private Network(int min,int max,String[] nodesName) { remake(min,max,nodesName);
    }

    /*  >>>Member methods<<<  */

    private void setRandomRelations(int min, int max) {}

    /**
     * It returns a random weight that can be used as path weight between tow nodes.
     * @param min the minimal possible number of the returned value.
     * @param max the maximal possible number of the returned value.
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
    public void remake(int min,int max,String[] nodesName) {
        nodes = new Node[nodesName.length];
        for (int i = 0; i <nodes.length; i++) {
            nodes[i] = new Node(getNodeSymbol(i), BUS_STATIONS[i]);
        }
        setRandomRelations(min,max);
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
