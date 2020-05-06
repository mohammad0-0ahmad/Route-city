package routeCity;
import java.util.ArrayList;
import java.util.Collections;

public interface Algo {
    /**
     * This method calculates and return the smallest distance between two bus stations.
     * @param nodes All bus stations
     * @param fromNode Startnode
     * @param toNode Destinationnode
     * @return The message to the user
     */
    static String algo(Node[] nodes, int fromNode, int toNode) {
        // The node with the shortest distance to the fromnode.
        int theClosestNode = fromNode;
        // The present minimum distance.
        int min;
        // Showing which node with the smallest distance to the nodes in the nodesarray.
        int[] closestNodeToReachToThis = new int[nodes.length];
        // Array to keep track if the method already visited the node.
        int[] visited = new int[nodes.length];
        visited[fromNode] = 1;
        // Array with the distances from the fromnode
        int[] distance = getDistances(nodes, fromNode);
        // List with the connections if there are any.
        ArrayList<Integer> connections = new ArrayList<>();
        // Main forloop
        for (int counter = 0; counter < nodes.length; counter++) {
            min =999;
            // Finding the closest node and getting the distance
            for (int i = 0; i < nodes.length; i++) {
                if (min > distance[i] && visited[i] != 1) {
                    min = distance[i];
                    theClosestNode = i;
                }
            }
            visited[theClosestNode] = 1;
            // Updating the distancearray and closestnodearray
            for (int i = 0; i < nodes.length; i++) {
                if (visited[i] != 1) {
                    if (min + getDistances(nodes, theClosestNode)[i] < distance[i]) {
                        distance[i] = min + getDistances(nodes, theClosestNode)[i];
                        closestNodeToReachToThis[i] = theClosestNode;
                    }
                }
            }
        }
        connections = getConnections(toNode, closestNodeToReachToThis);
        return "The smallest distance from " + nodes[fromNode].getName() + " to " + nodes[toNode].getName() + " is " + distance[toNode] +
                " km."+ addConnectionsToString(connections,nodes);
    }

    /**
     * This function returns the distance to the fromnode. It is used both to get the fromnode distances and later in the mainmethod to check distance from other nodes
     * @param nodes The nodes
     * @param fromNode The node to check
     * @return The distances
     */
    static int[] getDistances(Node[] nodes, int fromNode){
        int[] distance = new int[nodes.length];
        for (int j = 0; j < nodes.length; j++) {
            if (nodes[fromNode].isConnectedWith(nodes[j]) == -1) {
                distance[j] = 999;
            } else {
                distance[j] = nodes[fromNode].isConnectedWith(nodes[j]);
            }
        }
        return distance;
    }

    /**
     * This method adds the different nodes to a list to show the user where to change bus.
     * @param j the fromnodeposition
     * @param closestNodeToReachToThis The closestnodearray
     * @return The list
     */
    static ArrayList<Integer> getConnections (int j, int[] closestNodeToReachToThis){
        ArrayList<Integer> connections = new ArrayList<>();
            do {
                j = closestNodeToReachToThis[j];
                if (j != 0) {
                    connections.add(j);
                }
            }
            while (j != 0);
            Collections.reverse(connections);
            return connections;
    }

    /**
     * This method returns the connectionnames if there are any.
     * @param connections the connectionlist
     * @param nodes The nodes
     * @return The names
     */
    static String addConnectionsToString(ArrayList<Integer> connections, Node[] nodes) {
        String result = "";
        if (connections.size() > 0) {
            result+=" Please change bus at: ";
            for (int connection : connections) {
                result+=nodes[connection].getName() + " ";
            }
            return result;
        }
        else
            return "";
    }
}

