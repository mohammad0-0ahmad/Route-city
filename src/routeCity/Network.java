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

    public void remake(int min,int max,String[] nodesName) {
        nodes = new Node[nodesName.length];
        for (int i = 0; i <nodes.length; i++) {
            nodes[i] = new Node(getNodeSymbol(i), BUS_STATIONS[i]);
        }
        setRandomRelations(min,max);
    }

    public String getNodeSymbol(int index) {
        char letter = (char) (index % 26+65);
        return letter + ""+(index / 26 + 1);
    }
}
