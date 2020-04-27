package routeCity;

public class Network implements Dijkstras{
    /* static variables & methods */
    private static Network instance = new Network();

    public static final String[] BUS_STATIONS = new String[10];

    public static Network getInstance(){return instance;}

    /*  >Member variables<  */

    private Node[] nodes;

    /*  >>Constructors<<  */

    private Network(){}

    /*  >>>Member methods<<<  */
    private void setRandomRelations(int min,int max){}
    private int getRandomWeight(int min,int max){return -1;}
    private boolean isClosed(){return false;}
    public String getShortestPath(Node start,Node end){return null;}
    public void printNetwork(){}
    public void remake(){}
}
