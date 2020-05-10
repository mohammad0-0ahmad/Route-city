package routeCity;

import java.util.Scanner;

/**
 * This class handles the output in the menu and calls the desired methods.
 */
public abstract class CityRouter {
    private static final Network network= Network.getInstance();
    /*  > Static methods<<<  */

    /**
     * This menu prints the menu and calls the user input method and the switch method.
     */
    public static void startInConsole(){
        boolean currentNetworkIsPrinted = false;
        String userInput = "";
        while (!userInput.equals("Q")) {
            System.out.println("Main menu:\nPlease make your choice and press enter\n[P] Print network\n[S] Search travel\n[R] Remake network\n[Q] Exit");
            userInput = userInput();
            switch (userInput) {
                case "P": {
                    network.printNetwork();
                    currentNetworkIsPrinted = true;
                    break;
                }
                case "S": {
                    if (!currentNetworkIsPrinted) {
                        network.printNetwork();
                        currentNetworkIsPrinted = true;
                    }
                    enterTravelStations();
                    break;
                }
                case "R": {
                    network.remake();
                    System.out.println("The network is remade in a random way.");
                    currentNetworkIsPrinted=false;
                    break;
                }
                case "Q": {
                    break;
                }
                default: {
                    System.out.println("Please enter a valid character displayed in the menu.");
                }
            }
        }
    }
    /**
     * This method take an input and return a capital string.
     * @return user input
     */
    private static String userInput() {
        return new Scanner(System.in).nextLine().toUpperCase();
    }
    /**
     * This method ask for the bus stations the user wants to travel between and calling the dijkstra's algorithm method with the node numbers.
     */
    private static void enterTravelStations() {
        Node from =null;
        Node to =null;
        System.out.println("Please enter the bus station symbol you want to travel from:");
        while (from==null) {
            from = convertSymbolToNode();
            if (from==null) {
                System.out.println("This is not a bus station symbol in the network. Please try again.");
            }
        }
        System.out.println("Please enter the bus station symbol you want to travel to:");
        while (to==null) {
            to = convertSymbolToNode();
            if (to==null) {
                System.out.println("This is not a bus station symbol in the network. Please try again.");
            }
        }
        // Getting and printing the shortest between the two selected bus stations.
        System.out.println("\n"+network.getShortestPath(from,to)+"\n");
    }
    /**
     * This method getting user input and returns desired node depending on the input.
     * @return The desired node
     */
    private static Node convertSymbolToNode() {
        String symbolToConvert = new Scanner(System.in).nextLine().toUpperCase();
        for (int i = 0;i<network.getNodes().length;i++) {
            if (network.getNodes()[i].getSymbol().equals(symbolToConvert)) {
                return network.getNodes()[i];
            }
        }
        return null;
    }
}
