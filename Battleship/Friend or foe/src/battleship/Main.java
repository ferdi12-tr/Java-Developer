package battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    private static String[][] fogGameField;

    public static void main(String[] args) {

        ArrayList<Battleship> players = new ArrayList<>(); // to hold all players
        int i = 0; // to hold the players number

        while (i < 2) { // we accept only two players
            players.add(new Battleship("player " + (i + 1))); // create a new player and pass the player to the array
            fogGameField = players.get(i).getFogGameField(); // in each shooting we display initial Fog Game Field, that is untouched

            System.out.printf("Player %d place your ships on the game field\n\n", i + 1);

            // prepare the game fields
            Battleship.printField(players.get(i).getGameField());
            players.get(i).aircraftCarrier();
            players.get(i).battleship();
            players.get(i).submarine();
            players.get(i).cruiser();
            players.get(i).destroyer();

            System.out.println("Press Enter and pass the move to another player");
            if (scanner.nextLine().equals("")) { // if the user press enter go and create 2. player
                i++;
            } else {
                break;
            }
        }
        play(players); // two players are ready to play game
    }

    private static void play(ArrayList<Battleship> players) {
        Battleship player1 = players.get(0); // get player1 object
        Battleship player2 = players.get(1); // get player2 object

        // this two method calls each other until we hit the another things which is not enter.
        player1Shoot(player1, player2); // pass the all two players
        player2Shoot(player1, player2);
    }

    private static void player2Shoot(Battleship player1, Battleship player2) { // the method is shooting for player2
        Battleship.printField(fogGameField); // print the initial fogGamePlayer that we already declare and initialize in main method
        System.out.println("---------------------");
        Battleship.printField(player2.getGameField()); // we are player2 so we can see our gameField
        player2.takeAShoot(player1.getGameField()); // we are player2, and we should attack player1's gameField.
        System.out.println("Press Enter and pass the move to another player");
        if (scanner.nextLine().equals("")) { // pass the move to player1
            player1Shoot(player1, player2);
        }
    }

    private static void player1Shoot(Battleship player1, Battleship player2) { // the method is shooting for player1
        Battleship.printField(fogGameField);// print the initial fogGamePlayer that we already declare and initialize in main method
        System.out.println("---------------------");
        Battleship.printField(player1.getGameField()); // we are player1 so we can see our gameField
        player1.takeAShoot(player2.getGameField()); // we are player1, and we should attack player2's gameField.
        System.out.println("Press Enter and pass the move to another player");
        if (scanner.nextLine().equals("")) { // pass the move to player1
            player2Shoot(player1, player2);
        }
    }
}
