package routeCity;

import java.util.ArrayList;
import java.util.Arrays;

import routeCity.Node.Path;

/**
 * It implements dijkstra's algorithm to find the shortest path between two nodes.
 * The used source to create this class is : https://www.youtube.com/watch?v=pVfj6mxhdMw&t
 */

public abstract class Dijkstras {
    /**
     * It represents infinity in integer limits. "With other words it is the largest value of int range."
     */
    private static final int INT_INFINITY = (int) (2d / 0);

    /**
     * It the main method that implements dijkstra's algorithm to find shortest path between two nodes of node network.
     *
     * @param network node network that should be closed "can reach any node from any another one".
     * @param start   start node. The path start with it.
     * @param end     end node. The last node of the path.
     * @return ShortestPath object that represents the entire path from start to end and its weight. see : {@link ShortestPath}
     */
    public static ShortestPath getShortestPath(Network network, Node start, Node end) {
        // Checking if one of the required object is null.
        if (network == null || start == null || end == null) {
            return null;
        }
        // Checking if start and end node belong to the network.
        ArrayList<Node> nodesAsList = new ArrayList<>(Arrays.asList(network.getNodes()));
        if (!nodesAsList.contains(start) || !nodesAsList.contains(end)) {
            return null;
        }
        // Checking if start and end belongs to the same node.
        if (start == end) {
            ShortestPath result = new ShortestPath(0);
            result.addNodeOnThePath(start);
            return result;
        }
        // Begin looking for the shortest path between received nodes.
        // Getting weights table that help to find shortest path. "change depending on start node."
        int[][] weightsTable = getWeightsTable(network.getNodes(), start);
        // Getting the index of start and end node of network nodes array.
        final int startIndex = nodesAsList.indexOf(start);
        int endIndex = nodesAsList.indexOf(end);
        // Preparing the result that will be returned.
        // Setting the weight of the whole path from weight table.
        ShortestPath result = new ShortestPath(weightsTable[endIndex][1]);
        while (true) {
            // Because of the nature of the weights table should end node be stored first and so on to reach start one.
            result.addNodeOnThePath(nodesAsList.get(endIndex));
            // Checking if loop reached start node.
            if (endIndex == startIndex) {
                return result;
            }
            // Updating end node to the node that become visited before the actual one.
            endIndex = weightsTable[endIndex][2];
        }
    }

    /**
     * It creates a weights table that contains information that helps to find shortest path between two nodes.
     *
     * @param nodes network nodes array.
     * @param start start node.
     * @return weights table in matrix format. Check this link to understand what this table is. https://www.youtube.com/watch?v=pVfj6mxhdMw&t=35s
     */
    private static int[][] getWeightsTable(Node[] nodes, Node start) {
        ArrayList<Node> unvisitedNodes = new ArrayList<>(Arrays.asList(nodes));
        // create a matrix that will represent weights table.
        int[][] result = new int[unvisitedNodes.size()][3];
        // Getting the index of start node.
        final int startIndex = unvisitedNodes.indexOf(start);
        // Prepare weights table.
        for (int i = 0; i < result.length; i++) {
            result[i][0] = i;
            result[i][1] = INT_INFINITY;
            result[i][2] = -1;
        }
        // Change start node weight to 0 and its previous node to its index.
        result[startIndex][1] = 0;
        result[startIndex][2] = startIndex;
        // Converting node to arrayList that is in need to call checkLinkedNodes(). "To avoid reconvert it every round".
        ArrayList<Node> tempNodes = new ArrayList<>(Arrays.asList(nodes));
        // Visiting all unvisited nodes.
        while (unvisitedNodes.size() > 0) {
            int actualNodeIndex = getNextNodeIndexToVisit(nodes, unvisitedNodes, result);
            checkLinkedNodes(tempNodes, actualNodeIndex, unvisitedNodes, result);
            // Removes the visited node.
            unvisitedNodes.remove(nodes[actualNodeIndex]);
        }
        return result;
    }

    /**
     * It returns the index of the unvisited node that have shortest path from start node depending on weight table data.
     *
     * @param nodes          network nodes array.
     * @param unvisitedNodes a node array list that contains all nodes that have not been visited during the implementation of the algorithm.
     * @param weightTable    weights table in matrix format. Check this link to understand what this table is. https://www.youtube.com/watch?v=pVfj6mxhdMw&t=35s
     * @return an int number refers to the node that should be visited.
     */
    static private int getNextNodeIndexToVisit(Node[] nodes, ArrayList<Node> unvisitedNodes, int[][] weightTable) {
        // Creating a copy of weightTable.
        ArrayList<int[]> tempWeightTable = new ArrayList<>(Arrays.asList(weightTable));
        // Removing visited nodes.
        tempWeightTable.removeIf(row -> !unvisitedNodes.contains(nodes[row[0]]));
        // Getting the index of the row that belongs to an unvisited node that have lowest weight in the table.
        int indexFromTable = 0;
        for (int i = 1; i < tempWeightTable.size(); i++) {
            if (tempWeightTable.get(i)[1] < tempWeightTable.get(indexFromTable)[1]) {
                indexFromTable = i;
            }
        }
        // return the index of the node that should be visited.
        return tempWeightTable.get(indexFromTable)[0];
    }

    /**
     * It visits a specific node of networks nodes.
     * Visiting means here that all path to liked nodes with the actual node will be checked to update weight table in case could found lower weight of a path.
     *
     * @param nodes            network nodes array.
     * @param nodeToVisitIndex the actual node that should its linked nodes be checked.
     * @param unvisitedNodes   a node array list that contains all nodes that have not been visited during the implementation of the algorithm.
     * @param weightTable      weights table in matrix format. Check this link to understand what this table is. https://www.youtube.com/watch?v=pVfj6mxhdMw&t=35s
     */
    static private void checkLinkedNodes(ArrayList<Node> nodes, int nodeToVisitIndex, ArrayList<Node> unvisitedNodes, int[][] weightTable) {
        // Checking all paths weights that refer to the linked nodes with the actual visited node.
        for (Path path : nodes.get(nodeToVisitIndex).getLinkedNodes()) {
            // Getting destination node reference.
            Node destinationNode = path.getDestinationNode();
            // Checking if the destination node is already visited.
            if (unvisitedNodes.contains(destinationNode)) {
                // Fetching the index of destination node from nodes array list "it represents the network nodes array".
                int nodeToCheckIndex = nodes.indexOf(destinationNode);
                // The weight of the whole path from start node to the current destination node passed by the actual visited node.
                int weight = weightTable[nodeToVisitIndex][1] + path.getWeight();
                if (weight < weightTable[nodeToCheckIndex][1]) {
                    // updating weights table.
                    weightTable[nodeToCheckIndex][1] = weight;
                    weightTable[nodeToCheckIndex][2] = nodeToVisitIndex;
                }
            }
        }
    }

    /**
     * It represents the returned result of looking for shortest path between two nodes of a network.
     */
    public static class ShortestPath {
        /**
         * Refers to the weights of the whole path form start node to end path.
         */
        private final int weight;
        /**
         * Array list contains all nodes that should passed to reach a destination node.
         */
        private final ArrayList<Node> nodesOnThePath;

        private ShortestPath(int weight) {
            this.weight = weight;
            nodesOnThePath = new ArrayList<>();
        }

        /**
         * Adds Node to nodesOnThePath by inserting it in the first place.
         *
         * @param node node object.
         */
        private void addNodeOnThePath(Node node) {
            if (!nodesOnThePath.contains(node)) {
                nodesOnThePath.add(0, node);
            }
        }

        /**
         * Getter method.
         *
         * @return weight of ShortestPath. see {@link #weight}
         */
        public int getWeight() {
            return weight;
        }

        /**
         * Getter method.
         *
         * @return array list on node object. see: {@link #nodesOnThePath}
         */
        public ArrayList<Node> getNodesOnThePath() {
            return nodesOnThePath;
        }
    }
}