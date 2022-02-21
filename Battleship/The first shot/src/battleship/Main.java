package battleship;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static String[][] gameField = new String[11][11]; //create game field as 2-nested array

    public static void main(String[] args) {
        String[][] gameField = initializeGameField();

        // for each case, we pass the corresponding name and cell
        aircraftCarrier();
        battleship();
        submarine();
        cruiser();
        destroyer();

        takeAShoot();

    }

    private static void takeAShoot() { // we can shoot the war-ship thanks to this method
        Scanner scanner = new Scanner(System.in);
        System.out.println("The game starts!");
        printGameField();
        System.out.println("Take a shot!");

        while (true) {
            String coordinateOfShoot = scanner.nextLine(); // get the coordinate from user
            String first = coordinateOfShoot.replaceAll("[^A-Za-z]", ""); // this part equals to row, but type must be converted to int
            String second = coordinateOfShoot.replaceAll("[^0-9]", ""); // this part equals to column, but type must be converted to int

            int row = ((int) first.charAt(0)) - 64; // from string row to int row
            int column = Integer.parseInt(second); // from string column to int column

            try {
                // if we are out of range then trow an out-of-bounds exception
                if (row == 0 || row == 11 || column == 0 || column == 11) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                // this part evaluate if we do it successfully or not
                if (Objects.equals(gameField[row][column], "O ")) {
                    gameField[row][column] = "X ";
                    printGameField();
                    System.out.println("You hit a ship!");
                }
                else {
                    gameField[row][column] = "M ";
                    printGameField();
                    System.out.println("You missed!");
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e){ // print the message if we get any ArrayIndexOutOfBoundsException
                System.out.println("Error! You entered the wrong coordinates! Try again:");
            }

        }
    }

    private static void destroyer() {
        AutomaticFillAndPrint(" Destroyer", 2);
    }

    private static void cruiser() {
        AutomaticFillAndPrint(" Cruiser ", 3);
    }

    private static void submarine() {
        AutomaticFillAndPrint(" Submarine", 3);
    }

    private static void battleship() {
        AutomaticFillAndPrint( " Battleship", 4);
    }

    private static void aircraftCarrier() {
        AutomaticFillAndPrint(" Aircraft Carrier",5);
    }

    // check the condition if condition is satisfied then fill and print the game field
    private static void AutomaticFillAndPrint(String name, int cell) {

        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter the coordinates of the %s (%d cells):\n", name, cell);

        while (true) { // loop until valid coordinates are entered
            String coordinates = scanner.nextLine(); // get the coordinate from the user

            // parse the coordinate to make manipulating gameField array easy
            String[] coordinate = coordinates.split(" ");
            String first = coordinate[0].replaceAll("[^A-Za-z]", ""); //F
            String second = coordinate[0].replaceAll("[^0-9]", ""); //3
            String third = coordinate[1].replaceAll("[^A-Za-z]", ""); //F
            String fourth = coordinate[1].replaceAll("[^0-9]", ""); // 7


            // transform the char into int value, then subtract 64. that is  A(65) - 64 = 1, B(66) - 64 = 2, and so on..
            int row1 = ((int) first.charAt(0)) - 64; //6, that is F - 64
            int column1 = Integer.parseInt(second);//3
            int row2 = ((int) third.charAt(0)) - 64;//6, that is F - 64
            int column2 = Integer.parseInt(fourth);//7

            // check if coordinates are increasing order of not, because the condition of for loops in fillGameField() method must be satisfied properly
            if (column1 > column2) {
                int temp = column1;
                column1 = column2;
                column2 = temp;
            }
            else if (row1 > row2){
                int temp = row1;
                row1 = row2;
                row2 = temp;
            }


            // accept only horizontal or vertical location
            if (isWrongLocation(row1, row2, column1, column2)) {
                System.out.println("Error! Wrong ship location! Try again:");
            }

            else if (isTooClose(row1, row2, column1, column2)) {
                System.out.println("Error! You placed it too close to another one. Try again:");
            }
            else if ((Math.abs((row1 - row2)) == cell - 1 && Math.abs((column1 - column2)) == 0 || // checking vertically matching
                    (Math.abs((row1 - row2)) == 0 && Math.abs((column1 - column2)) == cell - 1))) {

                fillGameField(row1, row2, column1, column2);
                printGameField();
                break;
            }// checking horizontally matching{
                // we have to fill the game field according to two directions(only vertical or horizontal) and cell
            else {
                System.out.printf("Error! Wrong length of the %s! Try again:\n", name);
            }

        }
    }

    // accept only horizontal or vertical location, that is we only accept either row1 == row2 or column1 == column2
    private static boolean isWrongLocation(int row1, int row2, int column1, int column2) {
        return (row1 != row2) && (column1 != column2);
    }

    // if there are any war-ship ahead or behind the current war-ship, we are not allowed to place the current war-ship in that coordinates
    private static boolean isTooClose(int row1, int row2, int column1, int column2) {
        boolean checkRow1, checkRow2, checkColumn1, checkColumn2; // declare the part of conditions

        checkRow1 = Objects.equals(gameField[row1 - 1][column1], "O ");
        checkColumn1 = Objects.equals(gameField[row1][column1 -1], "O ");

        if(column2 == 10) { // if column2 (on the left of the game field) exceeds the range we may say that there isn't any "O ", so we can place in point of column2
            checkColumn2 = false; // there is no matching, that is Objects.equals(gameField[row2][column2], "O ") == false;
        }
        else {
            checkColumn2 = Objects.equals(gameField[row2][column2 + 1], "O "); // otherwise, check if this place is suitable for placement
        }
        if (row2 == 10) { // if row2 (on the bottom of the game field) exceeds the range we may say that there isn't any "O ", so we can place in point of row2
            checkRow2 = false; // there is no matching, that is Objects.equals(gameField[row2][column2], "O ") == false;
        }
        else {
            checkRow2 = Objects.equals(gameField[row2 + 1][column2], "O "); // otherwise, check if this place is suitable for placement
        }

        return (
                !(
                        ((column1 == column2) && !(checkRow1 || checkRow2)) ||
                        ((row1 == row2) && !(checkColumn1 || checkColumn2))
                )
        );
    }


    private static void fillGameField(int row1, int row2, int column1, int column2) {

        for (int i = row1; i <= row2; i++) {
            for (int j = column1; j <= column2; j++) {
                gameField[i][j] = "O ";
            }
        }
    }

    private static void printGameField() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(gameField[i][j]);
            }
            System.out.println();
        }
    }

    public static String[][] initializeGameField() { // initialize game field and return array

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (i == 0 && j != 0) { // fill the first row with number from 1 to 10
                    gameField[i][j] = String.format("%d ", j);
                }
                else if (i != 0 && j == 0) { // fill the first column with 'A' to 'J'
                    gameField[i][j] = String.format("%c ", (char) (i + 64));
                }
                else if (i != 0){ // we initialize the other fields with "~" symbol
                    gameField[i][j] = "~ ";
                }
                else {
                    gameField[0][0] = "  ";
                }
            }
        }
        return gameField;
    }
}
