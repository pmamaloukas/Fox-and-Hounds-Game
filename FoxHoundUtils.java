import java.util.Arrays;
import java.lang.Math;
import java.util.Scanner;
/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions to check the state of the game
 * board and validate board coordinates and figure positions.
 */
public class FoxHoundUtils {

    // ATTENTION: Changing the following given constants can 
    // negatively affect the outcome of the auto grading!

    /** Default dimension of the game board in case none is specified. */
    public static final int DEFAULT_DIM = 8;
    /** Minimum possible dimension of the game board. */
    public static final int MIN_DIM = 4;
    /** Maximum possible dimension of the game board. */
    public static final int MAX_DIM = 26;

    /** Symbol to represent a hound figure. */
    public static final char HOUND_FIELD = 'H';
    /** Symbol to represent the fox figure. */
    public static final char FOX_FIELD = 'F';

    // HINT Write your own constants here to improve code readability ...
    
    public static String[] initialisePositions(int dimension){

        if (dimension<MIN_DIM){
            throw new IllegalArgumentException();
        }

        String[] init = new String[(dimension / 2)+1]; //number of hounds is half the number of squares on the board, so array must hold
        //dimension/2 positions, plus one position for the fox

        for (int i=0; i<(dimension/2); i++){
            init[i] = ((char)('B'+(2*i))) +"1"; //setting the positions of the hounds, on letters B,D,F... on row 1
        }
        init[dimension/2] = ((char)('A'+Math.ceil(dimension/2.0))) + Integer.toString(dimension); //setting position of fox
        //last element in array, must be in the middle of the last row on the board, so we find the middle letter given the
        //dimensions the user has input
        return init;
        
    }

    public static boolean searchElement (String[] array, String element){ //helper function for checking if a specific element is in a list
        boolean flag = true;
        for (int i=0; i<array.length; i++){
            if (array[i].equals(element)){
                flag = true;
                break;
            }
            else{
                flag = false;
            }
        }
        return flag;
    }

    public static boolean houndIsThere (String[] array, String element){
        boolean flag = false;
        for (int i=0; i< array.length-1; i++){
            if(element.equals(array[i])){
                flag = true;
            }
        }
        return flag;
    }

    public static boolean foxIsThere (String[] array, String element){
        boolean flag = false;
        int len = array.length;
        if (array[len-1].equals(element)){
            flag = true;
        }
        return flag;
    }

    public static boolean isValidMove (int dim,String[] players,char figure,String origin,String destination){

       char curr_column = origin.charAt(0); //column of figure before move (A,B...H...)
       int curr_row = Integer.parseInt(origin.substring(1)); // row of figure before move (1,2...8...)
       char dest_column = destination.charAt(0); //column where we want to move figure
       int dest_row = Integer.parseInt(destination.substring(1)); // row where we want to move figure
       boolean flag = true; //the variable that will be returned
       
       if (dest_row>dim || dest_column>(char)('A'+(dim-1)) || searchElement(players,origin) == false
               || searchElement(players,destination) == true || (houndIsThere(players,origin)&&figure == 'F')
               ||(foxIsThere(players,origin) && figure == 'H') ){
           //checking if the row and column input by user do exist on the board given the dimensions,
           //if there exists a figure to be moved in the origin position,
           //if the destination position is already occupied by another figure
           //and if the figure supposed to move agrees with the figure in the origin.
           flag = false;
       }
       else {
           if (figure == 'H'){ //hounds can only move forward, so only two possible squares for each hound
               if (((dest_column == (char)curr_column+1) && (dest_row == curr_row+1)) ||
                       ((dest_column == (char)curr_column-1) && (dest_row == curr_row+1))){
                   flag = true;
            }
                else {
                    flag = false;
                }
            }

            else if(figure == 'F'){ //foxes can move forward and backward, so four possible squares
                if (((dest_column == (char)curr_column+1) && (dest_row == curr_row+1)) ||
                        ((dest_column == (char)curr_column-1) && (dest_row == curr_row+1))
                || ((dest_column == (char)curr_column+1) && (dest_row == curr_row-1)) ||
                        ((dest_column == (char)curr_column-1) && (dest_row == curr_row-1))){
                    flag = true;
                }
                else {
                    flag = false;
                }
            }
       }
        return flag;
    }     


    public static boolean isFoxWin(String position){
        int row = Integer.parseInt(position.substring(1));
        boolean fox_wins = false;
        if (row == 1){
            fox_wins = true;
        }
        return fox_wins;
    }

    public static boolean isHoundWin (String[] player_pos, int dim){ //hounds win if the fox has no more possible moves, so checking if the fox can
        //move in any of the four possible squares using the isValidMove function
        char curr_column = player_pos[player_pos.length-1].charAt(0); //column of figure before move (A,B...H...)
        int curr_row = Integer.parseInt(player_pos[player_pos.length-1].substring(1)); // row of figure before move (1,2...8...)

        boolean hound_wins = false;

        if (dim<1 || dim >26){
            throw new IllegalArgumentException();
        }

        else if (dim>=1 && dim<=26){

            if ((!isValidMove(dim, player_pos, 'F', player_pos[player_pos.length-1], (char)(curr_column+1)+Integer.toString(curr_row+1)))&&
            (!isValidMove(dim, player_pos, 'F', player_pos[player_pos.length-1], (char)(curr_column+1)+Integer.toString(curr_row-1)))&&
            (!isValidMove(dim, player_pos, 'F', player_pos[player_pos.length-1], (char)(curr_column-1)+Integer.toString(curr_row+1)))&&
            (!isValidMove(dim, player_pos, 'F', player_pos[player_pos.length-1], (char)(curr_column-1)+Integer.toString(curr_row-1)))){
                hound_wins=true;
                System.out.println("The hounds win!");
            }
        }

        return hound_wins;
    }


public static void main(String[] args) {

    String[] array = initialisePositions(8);
    System.out.println(foxIsThere(array, "E8"));
    System.out.println(isValidMove(8,array,'H',"B3","C2"));
    System.out.println(isValidMove(8,array,'F',"B3","C2"));
    System.out.println(isValidMove(8,array,'F',"E9","F7"));
    System.out.println(isValidMove(8,array,'H',"E8","F7"));
    System.out.println(isValidMove(8,array,'H',"B3","C4"));

    Scanner input1 = new Scanner (System.in);
    System.out.println("Configure dimensions for board? (Y/N) ");
    String answer = input1.nextLine();
    String[] players = {"A6","C6","C7","D8","B5"};

    int dimensions;

    if (answer.equals("N")){
        dimensions = DEFAULT_DIM;
    }
    else {
        System.out.println("Choose number of dimensions: ");
        dimensions = input1.nextInt();
        input1.close();
    }
    System.out.println(initialisePositions(dimensions));
    if (dimensions<=MAX_DIM && dimensions >= MIN_DIM){
        System.out.println(Arrays.toString (initialisePositions(dimensions)));
    }
    else {
        System.out.println ("Error: Invalid Input Dimensions");
    }
    System.out.print(isValidMove(dimensions,initialisePositions(dimensions),'F',"G11","H10"));
    System.out.println(isFoxWin("F6"));
}
}