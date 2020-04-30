import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all file input / output
 * related operations such as saving and loading a game.
 */
public class FoxHoundIO {

    public static boolean onBoard(int dim, String pos){
        char col = (char)pos.charAt(0);
        int row = Integer.parseInt(pos.substring(1));
        boolean flag = true;
        if (col>='A' && col<=(char)('A'+(dim-1)) && row>=1 && row<=dim){
            flag = true;
        }
        else{
            flag = false;
        }
        return flag;
    }

    public static char loadGame(String[] players, Path input) {
        try{
            char ans;
            int dim = 8;
            if (input.equals(null)){
                throw new NullPointerException();
            }
            if (players.length!=5){
                throw new IllegalArgumentException();
            }
            File saved_game = new File(String.valueOf(input));
            Scanner scanner = new Scanner(saved_game); //scanner to read data from the file
            char next = (scanner.next()).charAt(0); //first character of each piece of data in file will be the figure to move
            if (next == 'F' || next =='H'){
                ans = next;
                int ind =0;
                String[] new_players = new String[5];//the array holding the updated version of players after every new piece of
                //data is retrieved from file
                while (scanner.hasNext()){//every move that is saved in the file will be appended to players
                    new_players[ind] = scanner.next();
                    if (onBoard(8,players[ind])){
                        return '#';
                    }
                    ind++;
                }
                players = new_players;
                return ans;
            }
            else{
                return '#';
            }
        }
        catch (FileNotFoundException Exception){
            return '#';
        }
    }

    public static boolean saveGame(String[] players, char nextMove, Path saveFile) {
        try{
            if(saveFile.equals(null)){
                throw new NullPointerException();
            }
            if(players.length!=5){
                throw new IllegalArgumentException();
            }
            File saved_game = new File(String.valueOf(saveFile));
            if (saved_game.exists()){//can't save two different games with the same name
                System.out.print("This file already exists!");
                System.exit(0);
            }
            PrintWriter w = new PrintWriter(saved_game);
            w.print(nextMove + " ");
            for (int j =0; j<players.length; j++){
                w.print(players[j]+ " ");
            }
            w.close();
            return true;
        }
        catch (FileNotFoundException Exception){
            return false;
        }
    }
}
