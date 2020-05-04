package routeCity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * It contains all utilities that is in need to creates relations "paths" between network nodes.
 */
public interface NodesToNetwork {
    /**
     * It turns a node array into random ordered array list.
     * @param nodes array of node objects.
     * @return random ordered array list with the same nodes.
     */
    default ArrayList<Node> getRandomOrderedNodes(Node[] nodes){
        ArrayList result = new ArrayList(Arrays.asList(nodes));
        // reorder nodes randomly.
        Collections.shuffle(result);
        return result;
    }

    /**
     * It filters a node array list by removing all nodes that can't be connect with the other nodes any more.
     * @param nodes array list that include nodes that will be filtered.
     * @param maxAmountLinks maximum amount of possible paths that can a node have.
     * @return an array stores all nodes that have ability to be connected OR an empty one.
     */
    default ArrayList<Node> getPossibleNodesToLink(ArrayList<Node> nodes,int maxAmountLinks){
        ArrayList<Node> result = new ArrayList<>(nodes);
        for (int i = 0; i < result.size(); i++) {
            // Removing the node that has already reached maximum amount of paths.
            if (result.get(i).getLinkedNodes().size() >= maxAmountLinks){
                // Printing a warning in case a node has more than maxAmountLinks that should not be reached.
                if (result.get(i).getLinkedNodes().size() > maxAmountLinks){
                    System.err.println("Warning node " +result.get(i).getSymbol() + " is already connected with more than "+ maxAmountLinks + " nodes.");
                }
                result.remove(i);
                i--;
            }else {
                // To avoid returning node that will be able to be connected with the other. "a node can't have to paths to the same node."
                // Assuming that the actual node hasn't an ability to be connected with the other nodes. "until find a node that can be connected with."
                boolean isAlreadyConnectedWithAllNodes = true;
                // Checking the other nodes.
                for (int j = 0 ; j < result.size();j++){
                    // To skip checking the node with itself.
                    if (i == j){
                        continue;
                    }
                    Node temp = result.get(j);
                    // -1 means that the actual node is not connected with the another one.
                    if(result.get(i).isConnectedWith(temp) == -1){
                        isAlreadyConnectedWithAllNodes = false;
                        break;
                    }
                }
                // Removing node in case could not find that can be connected.
                if (isAlreadyConnectedWithAllNodes){
                    result.remove(i);
                    i--;
                }
            }
        }
        // Sorting the nodes so the node that have less paths "connected less than the other" will be first.
        Collections.sort(result,(o1, o2) -> {
            if(o1.getLinkedNodes().size() > o2.getLinkedNodes().size()){
                return 1;
            }else if(o1.getLinkedNodes().size() < o2.getLinkedNodes().size()){
                return -1;
            }
            return 0;
        });
        return result;
    }

    /**
     * Overloading of {@link #getPossibleNodesToLink(ArrayList, int)}.
     * It filters a node array list by removing all nodes that can't be connect with specific node.
     * @param nodes array list that include nodes that will be filtered.
     * @param maxAmountLinks maximum amount of possible paths that can a node have.
     * @param fitsMe a node object that will be used during the filtering.
     * @return an array stores all nodes that have ability to be connected with fitsMe node object OR an empty one.
     */
    default ArrayList<Node> getPossibleNodesToLink(ArrayList<Node> nodes,int maxAmountLinks,Node fitsMe){
        // If fitsMe node has already reached maximum limit a empty arraylist will be returned.
        if (fitsMe == null || fitsMe.getLinkedNodes().size() == maxAmountLinks){
            return new ArrayList<>();
        }
        // Checking if the node belongs to the network. "nodes array list".
        boolean existInNodes = false;
        for (Node node: nodes) {
            if (node == fitsMe) {
                existInNodes = true;
                break;
            }
        }
        if (!existInNodes){
            System.err.println("This node "+fitsMe.getSymbol()+" can't be connected with the other nodes because it doesn't belong to the same network.");
            return new ArrayList<>();
        }
        // Removing the nodes which are already connected with fitsMe node object.
        ArrayList <Node> result = getPossibleNodesToLink(nodes,maxAmountLinks);
        for (int i = 0; i < result.size();i++){
            // Larger than -1 means that fitsMe is connected with the actual node or is the same object.
            if(fitsMe.isConnectedWith(result.get(i)) > -1){
                result.remove(i);
                i--;
            }
        }
        // reorder nodes randomly.
        Collections.shuffle(result);
        return result;
    }
}