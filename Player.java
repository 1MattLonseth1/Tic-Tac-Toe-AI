
//A collection of common methods shared by the various player
//types of our Tic-Tac-Toe game


//************************************************************************************
//*                                                                                  *
//*                    YOU ARE NOT ALLOWED TO MODIFY THIS CLASS                      *
//*                                                                                  *
//************************************************************************************

public interface Player{
    
    //a blank on the game board
    public static final char BLANK = '_';
    //the X and O symbols for the two players, respectively
    public static final char X = 'X';
    public static final char O = 'O';
    public static final char[] SYMB = {X, O};
    //Number of free spots on a blank board (3x3)
    public static final int BOARD_LEN = 9;
    //used by betweenGames(...) to indicate if player won, lost, or draw
    public static final int LOST = -1;
    public static final int DRAW = 0;
    public static final int WON = 1;
    
    
    //Perform any initialization for the respective player
    //Called ONCE before any games are played... NOT called between every game
    public void initPlayer();
    
    //Returns a String descriptor of this player (ex: "Human")
    public String getDescription();
    
    //Takes the player's turn, and returns the spot (1-9, inclusive) where
    //the player wants to place their mark
    //Argument boardstate indicates the free/occupied spots on hte game board.
    public int takeTurn(String boardState);
    
    //Gets called at the end of every game
    //argument indicates whether this player won, lost,
    //or game was draw (see final values in Player.java)
    public void betweenGames(int didPlayerWin);
    
    
    //Returns some extra info useful for debugging
    //Printed at the end of each game ONLY IF the
    //DEBUG_MODE final is set to true in GameLauncher.java
    public String getDebugInfo();
    
    
}