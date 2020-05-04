package routeCity;

import java.util.Scanner;

/**
 * This class handles the output in the menu and calls the desired methods.
 */
public abstract class CityRouter {
    private static Network network= Network.getInstance();
    private static String userInput = "";
    private static Scanner sc = new Scanner(System.in);
    /*  > Static methods<<<  */

    /**
     * This menu prints the menu and calls the userinputmethod and the switchmethod.
     */
    public static void startInConsole(){
        while (!userInput.equals("Q")) {
            System.out.println("Main menu:\nPlease make your choice\n[P] Print network\n[S] Search travel\n[R] Remake network\n[Q] Exit");
            selectMethod(userInput());
        }
    }
    /**
     * This method take an input and return a capital string.
     * @return userinput
     */
    private static String userInput() {
        userInput = sc.next().toUpperCase();
        return  userInput;
    }

    /**
     * This method calls the different choices in the menu.
     * @param userInput The userinput
     */
    private static void selectMethod(String userInput) {
        switch (userInput) {
            case "P": {
                network.printNetwork();
                break;
            }
            case "S": {
                enterTravelStations();
                break;
            }
            case "R": {
                network.remake(Network.BUS_STATIONS);
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

    /**
     * This method ask for the busstations the user wants to travel between and calling the dijkstras method with the nodenumbers.
     */
    private static void enterTravelStations() {
        int from =-1;
        int to =-1;
        System.out.println("Please enter the busstationsymbol you want to travel from:");
        while (from==-1) {
            from = convertSymbolToInt();
            if (from==-1) {
                System.out.println("This is not a busstation symbol in the network. Please try again.");
            }
        }
        System.out.println("Please enter the busstationsymbol you want to travel to:");
        while (to==-1) {
            to = convertSymbolToInt();
            if (to==-1) {
                System.out.println("This is not a busstation symbol in the network. Please try again.");
            }
        }
        algo.algo(network,from,to);
    }
    /**
     * This method converts the symbolname to an integer depending on the position in the nodeslist.
     * @return The position in the arraylist
     */
    private static int convertSymbolToInt() {
        String symbolToConvert = sc.next().toUpperCase();
        for (int i = 0;i<network.nodes.length;i++) {
            if (network.nodes[i].getSymbol().equals(symbolToConvert)) {
                return i;
            }
        }
        return -1;
    }
}
