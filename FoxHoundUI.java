import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;



import java.util.Objects;
import java.util.Arrays;

/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all user interface related
 * functionality such as printing menus and displaying the game board.
 */
public class FoxHoundUI {

    /** Number of main menu entries. */
    private static final int MENU_ENTRIES = 2;
    /** Main menu display string. */
    private static final String MAIN_MENU =
        "\n1. Move\n2. Exit\n\nEnter 1 - 2:";

    /** Menu entry to select a move action. */
    public static final int MENU_MOVE = 1;
    /** Menu entry to terminate the program. */
    public static final int MENU_EXIT = 2;

    /**
     * Print the main menu and query the user for an entry selection.
     * 
     * //@param figureToMove the figure type that has the next move
     * //@param stdin a Scanner object to read user input from
     * @return a number representing the menu entry selected by the user
     * @throws IllegalArgumentException if the given figure type is invalid
     * @throws NullPointerException if the given Scanner is null
     */

    public static String type_of_figure(String str, String[] players, int dim){ //function to decide whether character to be printed is F, H or .
        String figure = "";
        boolean found = false;
        for (int i=0;i<players.length;i++){ //looping through the list with the player positions
            if ((players[i].equals(str))&&(i<players.length-1)){ //str is any given position on the board. If it exists in the list and it is not the last element, then it must be a hound
                figure = "H";
                found = true;
                break;
            }
            else if((players[i].equals(str))&&(i==players.length-1)){//if it is the last element, it must be a fox
                figure = "F";
                found=true;
                break;
            }
        }
        if (!found) {//if str is not found in the list of player positions, that means there is no figure in that position, so we print a dot
            figure = ".";
        }

        return figure;
    }

    public static void mainBoard(String[] players, int dim){//this function prints the board without the letters on the top and bottom
        for (int j=1; j<=dim; j++){
            
            if (dim<=9){ //j loops through rows, so we print its value to have the number of the row on each side of the board
                System.out.print("\n"+Integer.toString(j)+" "); 
        
            }
            else{
                if (j>9){
                    System.out.print("\n"+Integer.toString(j));
                }
                else{
                    System.out.print("\n"+"0"+Integer.toString(j));//if dimension is two digits, then single digit rows need a zero in front
                }
            }

            for (int k=1; k<=dim; k++){ //k loops through the columns
                String str = (char)(k+64)+Integer.toString(j);
                //since the two nested loops will run dim^2 times
                System.out.print(type_of_figure(str,players,dim));
            }

            if (dim<=9){ // printing j again at the other end of the board
                System.out.print(" "+Integer.toString(j)); 
        
            }
            else{
                if (j>9){
                    System.out.print(" "+Integer.toString(j));
                }
                else{
                    System.out.print(" "+"0"+Integer.toString(j));
                }
            }
        }
    }
    
    public static void letters(int dimension){ // function to print column letters on top and bottom
        System.out.print("  ");
        for (int i=0; i<dimension; i++){
            char letter = (char) ('A'+i);
            System.out.print(letter);
        }
    }


    public static void displayBoard(String[] players, int dimension){
        letters(dimension);
        System.out.print("  \n");
        mainBoard(players, dimension);
        System.out.print("\n");
        System.out.print("\n");
        letters(dimension);
        System.out.print("  ");
    }

    public static String[] positionQuery(int dim, Scanner stdin){

        System.out.println("Provide origin and destination coordinates.\nEnter two positions between A1-"+String.valueOf((char)('A'+(dim-1)))+Integer.toString(dim)+":");
        //Telling the user the possible valid coordinates for all possible dimensions.
        //asking origin and destination from the user
        String answer = stdin.nextLine();
        String[] ans_arr = answer.split(" "); //making the origin and destination two separate strings by splitting them at the space
        //and putting them in a list so I can access them and save them in separate variables.
        String origin = ans_arr[0];
        String destination = ans_arr[1];

        String[] array = new String[2];//array to be returned with origin and destination coordinates
        char orig_col = origin.charAt(0);//separating the letter and the number from each coordinate
        int orig_row = Integer.parseInt(origin.substring(1));
        char dest_col = destination.charAt(0);
        int dest_row = Integer.parseInt(destination.substring(1));


        if(('A'<=orig_col && orig_col<=(char)('A'+(dim-1))) && (orig_row <=dim && orig_row >=1) && ('A'<=dest_col && dest_col<=(char)('A'+(dim-1))) && (dest_row <=dim && dest_row >=1)){//checking if
                //coordinates are on the board and if they are appending them to the array, otherwise print error
            array[0]=origin;
            array[1]=destination;
        }
        else {
            System.err.println("These are not valid coordinates");

        }
        return array;
    }


    public static int mainMenuQuery(char figureToMove, Scanner stdin) {
        Objects.requireNonNull(stdin, "Given Scanner must not be null");
        if (figureToMove != FoxHoundUtils.FOX_FIELD 
         && figureToMove != FoxHoundUtils.HOUND_FIELD) {
            throw new IllegalArgumentException("Given figure field invalid: " + figureToMove);
        }

        String nextFigure = 
            figureToMove == FoxHoundUtils.FOX_FIELD ? "Fox" : "Hounds";

        int input = -1;
        while (input == -1) {
            System.out.println(nextFigure + " to move");
            System.out.println(MAIN_MENU);

            boolean validInput = false;
            if (stdin.hasNextInt()) {
                input = stdin.nextInt();
                validInput = input > 0 && input <= MENU_ENTRIES;
            }

            if (!validInput) {
                System.out.println("Please enter valid number.");
                input = -1; // reset input variable
            }

            stdin.nextLine(); // throw away the rest of the line
        }

        return input;
    }

    public static void main(String[] args){
        String[] array = {"B3","D1","A5","H6","F6"};
        Scanner input = new Scanner(System.in);
        displayBoard(array, 8);
        System.out.println(Arrays.toString(positionQuery(11, input)));
        
    }

    public static Path fileQuery(Scanner test_in) {
        System.out.println("Enter file path:");
        test_in.useDelimiter("\\Z");
        String path_s = test_in.next();
        Path path = Paths.get(path_s);
        if (test_in.equals(null)){
            throw new NullPointerException();
        }

        return path;
    }
}







