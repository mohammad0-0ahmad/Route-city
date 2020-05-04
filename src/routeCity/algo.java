package routeCity;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class algo {

    public static void algo(Network network, int fromNode, int toNode) {
        int[] shortestPathToGetHere = new int[network.nodes.length];
        int min = 999; // min holds the minimum value,
        int shortestPreviousNodePositionInArray = 0; //  shortestPreviousNodePositionInArray holds the value for the next node.
        int[] distance = new int[network.nodes.length]; // the distance matrix
        int[][] matrix = new int[network.nodes.length][network.nodes.length]; // the actual matrix
        int[] visited = new int[network.nodes.length]; // the visited array

        for (int i = 0; i < network.nodes.length; i++) {
            visited[i] = 0; //initialize visited array to zeros
            shortestPathToGetHere[i] = 0;
            for (int j = 0; j < network.nodes.length; j++) {
                if (network.nodes[i].isConnectedWith(network.nodes[j])==-1) {
                    matrix[i][j] = min; // make the zeros as 999
                }
                else if (network.nodes[i].isConnectedWith(network.nodes[j])==0) {
                    matrix[i][j] = 0;
                }
                else {
                    matrix[i][j]=network.nodes[i].isConnectedWith(network.nodes[j]);
                }
            }
        }
        distance = matrix[fromNode]; //initialize the distance array
        visited[fromNode] = 1; //set the actual node as visited

/**
 * Finds the shortest not visited node to use in next step.
 */
        for (int counter = 0; counter < network.nodes.length; counter++) {
            min = 999;
            for (int i = 0; i < network.nodes.length; i++) {
                if (min > distance[i] && visited[i] != 1) {
                    min = distance[i];
                    shortestPreviousNodePositionInArray = i;
                }
            }
            visited[shortestPreviousNodePositionInArray] = 1;
            for (int i = 0; i < network.nodes.length; i++) {
                if (visited[i] != 1) {
                    if (min + matrix[shortestPreviousNodePositionInArray][i] < distance[i]) {
                        distance[i] = min + matrix[shortestPreviousNodePositionInArray][i];
                        shortestPathToGetHere[i] = shortestPreviousNodePositionInArray;
                    }
                }
            }
        }

        System.out.println("The smallest distance from "+ network.nodes[fromNode].getName()+" to "+ network.nodes[toNode].getName()+" is " +distance[toNode]+" km.");

        int j =toNode;
        ArrayList<Integer> connections = new ArrayList<>();
        while (j != 0) {
            j = shortestPathToGetHere[j];
            if(j!=0) {
                connections.add(j);
            }
        }
    Collections.reverse(connections);
        if (connections.size()>0) {
            System.out.println("Please change bus at: ");
            for (int connection : connections) {
                System.out.print(network.nodes[connection].getName()+", ");
            }
            System.out.println();
        }
    }

}

