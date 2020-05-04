package routeCity;

import java.util.Scanner;

public abstract class CityRouter {
    private static Network network= Network.getInstance();
    private static String userInput = "";
    private static Scanner sc = new Scanner(System.in);
    /*  > Static methods<<<  */
    public static void startInConsole(){
        while (!userInput.equals("Q")) {
            System.out.println("Main menu:\nPlease make your choice\n[P] Print network\n[S] Search travel\n[R] Remake network\n[Q] Exit");
            selectMethod(userSelectInMenu());
        }
    }
    public static String userSelectInMenu() {
        userInput = sc.next().toUpperCase();
        return  userInput;
    }
    private static void selectMethod(String userInput) {
        switch (userInput) {
            case "P": {
                network.printNetwork();
                break;
            }
            case "S": {
                searchTravel();
                break;
            }
            case "R": {
                network.remake(network.BUS_STATIONS);
                break;
            }
            case "Q": {
                break;
            }
            default: {
                System.out.println("Please enter a valid character");
            }
        }
    }
    private static void searchTravel() {
        int from =-1;
        int to =-1;
        while (from==-1) {
            System.out.println("Please enter the busstationsymbol you want to travel from:");
            from = convertSymbolToInt();
        }
        while (to==-1) {
            System.out.println("Please enter the busstationsymbol you want to travel to:");
            to = convertSymbolToInt();
        }
        algo.algo(network,from,to);
    }
    public static int convertSymbolToInt() {
        String symbolToConvert = sc.next().toUpperCase();
        for (int i = 0;i<network.nodes.length;i++) {
            if (network.nodes[i].getSymbol().equals(symbolToConvert)) {
                return i;
            }
        }
        return -1;
    }

}
