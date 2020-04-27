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

    private Node[] nodes;

    /*  >>Constructors<<  */

    /**
     * Constructor with possibility to create a network with other names at the stationsnames, more stations or
     * other amount of paths than provided in the project.
     * 2 or 3 nodes
     * @param min minimun paths
     * @param max maximum paths
     * @param nodesName busstations names
     */
    private Network(int min,int max,String[] nodesName) {
        remake(min,max,nodesName);
    }

    /*  >>>Member methods<<<  */
    private void setRandomRelations(int min, int max) {
    }

    private int getRandomWeight(int min, int max) {
        return -1;
    }

    private boolean isClosed() {
        return false;
    }

    public String getShortestPath(Node start, Node end) {
        return null;
    }

    public void printNetwork() {
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
    public String getNodeSymbol(int index) {
        char letter = (char) (index % 26+65);
        return letter + ""+(index / 26 + 1);
    }
}
