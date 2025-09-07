import java.util.*;

//An abstraction of a Tic-Tac-Toe game, played directly in the console!

public class TicTacToe {

    //For user input -- needed for Human Players and prompting to "play again?"
    public static Scanner scan;
    
    //Stores both players for the game (to make things more convenient)
    private Player[] players;
    
    //A String representation of an empty board and its spots
    private static final String BOARD_OUTPUT = "\t1 | 2 | 3\n" +
                                               "\t---------\n" +
                                               "\t4 | 5 | 6\n" +
                                               "\t---------\n" +
                                               "\t7 | 8 | 9";
    
    //To validate prompting the user to play again
    private static final String PLAY_AGAIN_YES = "Y";
    private static final String PLAY_AGAIN_NO = "N";
    
    
    
    public TicTacToe(Player p1, Player p2){
        if (p1 == null || p2 == null)
            throw new IllegalArgumentException("null Player argument provided!");
        
        //Put our players in an array for convenience and initialize them
        this.players = new Player[]{p1, p2};
        for (Player p : players)
            p.initPlayer();
        TicTacToe.scan = new Scanner(System.in);
    }
    

    
    
    //Runs games with regular output until the player decides they want to quit
    public void runInteractiveGames(){
        
        int gameCount = 0;
        int[] results = new int[3];
        String choice;
        do{
            results[playOneGame(true)]++;
            if (GameLauncher.DEBUG_MODE){
                displayDebugInfo();
            }
            gameCount++;
        } while (playAgain());
        displayGameResults(results, gameCount);

    }
    
    
    //Plays a series of gameCount games with none of the in-game output
    //Can only be played with **non-Human Players**!
    public void runBatchGames(int gameCount){
        
        if (gameCount <= 0)
            throw new IllegalArgumentException("Invalid game count provided!: " + gameCount);
        validateNoHumans();
        
        int[] results = new int[3];
        for (int i = 0; i < gameCount; i++){
            results[playOneGame(false)]++;
        }        
        if (GameLauncher.DEBUG_MODE){
            displayDebugInfo();
        }
        displayGameResults(results, gameCount);

    }
    

    //Ensure neither player is a Human (in the case of running batch games)
    private void validateNoHumans(){
        for (int i = 0; i < players.length; i++){
            if (players[i] instanceof Human)
                throw new IllegalArgumentException("runBatchGames(...) cannot be used with Human player(s)!");
        }
    }
    
    
    //Plays a single game of tic-tac-toe
    //verbose indicates if in-game output is printed
    //returns the number of the winning player, or 0 for a draw
    private int playOneGame(boolean verbose){
        
        int current = initGame(verbose);
        String boardState = TicTacToe.createNewBoardStateStr();

        while (boardState.contains(""+Player.BLANK)){
            
            boardState = handleOneTurn(current, boardState, verbose);
            if (checkForWin(boardState)){
                endGameWin(current, boardState, verbose);
                return current+1;    
            }
            current = ++current % players.length;
        }
        endGameDraw(boardState, verbose);
        return 0;
    }
    
    
    //Initialize the game
    //Namely, pick the random starting player
    private int initGame(boolean verbose){
        
        int current = (int)(Math.random()*players.length);
        if (verbose)
            System.out.println("Player #" + (current + 1) + " (" + players[current].getDescription() + 
                           ") goes first as " + Player.SYMB[current] + "'s!\n");
        return current;
    }
    
    
    //Handles a single turn of the tic-tac-toe game
    private String handleOneTurn(int current, String boardState, boolean verbose){
        if (verbose){
            System.out.println("*****   Player #" + (current + 1) + " (" + players[current].getDescription() + 
                               ")'s turn -- as " + Player.SYMB[current] + "'s!   *****");
            printBoard(boardState);
        }
        int move = players[current].takeTurn(boardState);
        validatePlayerChoice(move, boardState);
        if (verbose){
            System.out.println("Player #" + (current + 1) + " (" + players[current].getDescription() + 
                               ") takes spot: " + move + "\n");
        }
        return updateBoardState(move, current, boardState);
    }
    
    
    //Updates the game board per the current player's most recent move
    //Returns a String representation of the updated board state
    private static String updateBoardState(int move, int current, String boardState){
        
        String newBoardState = "";
        for (int i = 0; i < boardState.length(); i++){
            if (i+1 == move)
                newBoardState += Player.SYMB[current];
            else
                newBoardState += boardState.charAt(i);
        }
        return newBoardState;
    }
    
    
    //Ensures that the choice made by the Player is an empty/valid spot on game board
    private static void validatePlayerChoice(int move, String boardState){
        if (move <= 0 || move > Player.BOARD_LEN || boardState.charAt(move-1) != Player.BLANK)
            throw new IllegalStateException("ERROR! Player returned invalid move (" + move + 
                                            " for " + boardState + ")!");
    }
    
    //Initialize an empty boardState string (ie "_________")
    private static String createNewBoardStateStr(){
        String boardState = "";
        for (int i = 0; i < Player.BOARD_LEN; i++)
            boardState += Player.BLANK;
        return boardState;
    }
    
    
    //Called at the end of the game that does NOT end in a draw
    //Prints out appropriate text and calls between game methods on the players
    private void endGameWin(int winner, String boardState, boolean verbose){
        
        if (verbose){
            System.out.println("\n\nPlayer #" + (winner + 1) + " (" + players[winner].getDescription() +
                               ") wins!!");
            System.out.println("\nFinal board state:");
            printBoard(boardState);
        }
        players[winner].betweenGames(Player.WON);
        players[(winner+1)%2].betweenGames(Player.LOST);
        
    }

    //Called at the end of the game that ends in a draw
    //Prints out appropriate text and calls between game methods on the players
    private void endGameDraw(String boardState, boolean verbose){
        
        if (verbose){
            System.out.println("\n\nGame ends in a DRAW!");
            System.out.println("\nFinal board state:");
            printBoard(boardState);
        }
        for (int i = 0; i < players.length; i++){
            players[i].betweenGames(Player.DRAW);
        }
    }
    
    //checks to see if EITHER player has won the game (true) or not (false)
    //check for horizontal, vertical, and diagonal lines of 3 of the same symbol
    private static boolean checkForWin(String boardState){
        
        return (checkHoriz(boardState) || checkVert(boardState) || checkDiag(boardState));  
    }
    
    //Checks the game board for horizontal lines of 3 of the same symbol
    private static boolean checkHoriz(String boardState){
        for (int i = 0; i < 3; i++){
            int[] toCheck = new int[3];
            int ct = 0;
            for (int j = i * 3; j < (i+1) * 3; j++){
                toCheck[ct++] = j;
            }
            if (isCompleteRow(boardState, toCheck))
                return true;
        }
        return false;
    }
    
    
    //Checks the game board for vertical lines of 3 of the same symbol
    private static boolean checkVert(String boardState){
        for (int i = 0; i < 3; i++){
            int[] toCheck = new int[3];
            int ct = 0;
            for (int j = i; j < boardState.length(); j+=3){
                toCheck[ct++] = j;
            }
            if (isCompleteRow(boardState, toCheck))
                return true;
        }
        return false;
    }
       
    
    //Checks the game board for diagonal lines of 3 of the same symbol
    private static boolean checkDiag(String boardState){
        for (int i = 0; i < 2; i++){
            int[] toCheck = new int[3];
            int ct = 0;
            for (int j = i*2; j < boardState.length()-i; j+=(4/(i+1))){
                toCheck[ct++] = j;
            }
            if (isCompleteRow(boardState, toCheck))
                return true;
        }
        return false;
    }
    
    
    //Checks to see if, given the board state and the spots specified in 
    //the idx array, if all spots have the same symbol (and none are blank)
    private static boolean isCompleteRow(String boardState, int[] idx){
        char[] vals = new char[3];
        for (int i = 0; i < vals.length; i++)
            vals[i] = boardState.charAt(idx[i]);
        return vals[0] == vals[1] && vals[1] == vals[2] && vals[2] != Player.BLANK;
    }    
    
    
    //Prints out the game board in a nice pretty format (w/ spot numbers) 
    //per the current board state
    public static void printBoard(String boardState){
         String toPrint = BOARD_OUTPUT;
         for (int i = 0; i < boardState.length(); i++){
             char ch = boardState.charAt(i);
             if (ch != Player.BLANK)
                 toPrint = toPrint.replace(""+(i+1), ""+ch);
         }
         System.out.println("\n" + toPrint + "\n");
    }
    
    
    //Prompt the player to play again until they enter a proper Y or N
    private static boolean playAgain(){
        System.out.println("Would you like to play again? (Y/N): ");
        String choice = scan.nextLine().toUpperCase();
        while (!choice.equals(PLAY_AGAIN_YES) && !choice.equals(PLAY_AGAIN_NO)){
            System.out.println("Enter a valid input (Y/N): ");
            choice = scan.nextLine().toUpperCase();
        }
        System.out.println("\n\n");
        return choice.equals(PLAY_AGAIN_YES);
    }
    
    
    //Report results after all batch/interactive games are completed
    private void displayGameResults(int[] results, int gameCount){
        
        System.out.println("\n\n" + gameCount + " game(s) completed!");
        System.out.println("\nGame results:");
        for (int i = 1; i <= players.length; i++){
            System.out.println("\tPlayer #" + i + " (" + players[i-1].getDescription() + 
                               ") wins: " + results[i] + ", win rate: " + 
                               (roundTo(((double)results[i]*100)/gameCount , 2) + "%"));
        }
        System.out.println("\tDraws: " + results[0] + ", draw rate: " + 
                           (roundTo(((double)results[0]*100)/gameCount , 2) + "%"));                         
        
    }
    
    
    //Displays additional debug information (only if DEBUG_MODE is true in GameLauncher.java)
    //is displayed:
    //  -for interactive games: at the end of EACH game
    //  -for batch games: only once at the very end, after all batch games are completed
    private void displayDebugInfo(){
        System.out.println("\n************************    DEBUG INFO    ************************");
        for (int i = 0; i < players.length; i++){
            System.out.println("===   Player #" + (i+1) + " (" + players[i].getDescription() + 
                               ") debug info:   ===");
            System.out.println(players[i].getDebugInfo() + "\n\n");
        }
        System.out.println("******************************************************************");
    }
    
    // Rounds a decimal n places
    public static double roundTo(double num, int n) {
        return Math.round(num * Math.pow(10, n)) / Math.pow(10, n);
    }
}