import java.util.*;


//A Human Player for the Tic-Tac-Toe game
//Choices are made via prompts to the console!


public class Human implements Player{
    
    //Tracks how many times each spot was chosen (for debug info)
    private HashMap<Integer, Integer> choiceHistory;

    
    
    //Initialize this player.  Called ONCE before any 
    //games are played (does NOT get called between games)
    public void initPlayer(){
        choiceHistory = new HashMap<Integer, Integer>();
    }

    
    //Returns String description of this player
    public String getDescription(){
        return "Human";
    }

    
    //Takes the player's turn per the argument board state
    //Returns an int of the spot the player wants to take (1-9)
    //
    //Example board state: __X_O_O__ indicates:
    //There's an X in spot 3
    //There's an O in spots 5 and 7
    //Spots 1, 2, 4, 6, 8, 9 are free
    //
    //Human prompts user for choice
    public int takeTurn(String boardState){

        String validChoices = makeValidChoicesString(boardState);
        String input = getValidUserInput(validChoices);
        
        int choice = Integer.parseInt(input);
        updateChoiceHistory(choice);
        
        return Integer.parseInt(input);
    }

    
    //Returns a String of valid choice values per the board state.
    //Ex: given: __XO__X_O, this function returns: "12568"
    private static String makeValidChoicesString(String boardState){ 
        String validChoices = "";
        for (int i = 0; i < boardState.length(); i++){
            if (boardState.charAt(i) == Player.BLANK)
                validChoices += "" + (i+1);
        }
        return validChoices;
    }

    
    //Prompts the user repeatedly until they enter a number that exists in validChoices
    private static String getValidUserInput(String validChoices){
        System.out.print("Where would you like to place your symbol?: ");
        String input = TicTacToe.scan.nextLine();
        while (!isValidInput(validChoices, input)){
            System.out.print("Invalid input! Select a valid cell per the board above: ");
            input = TicTacToe.scan.nextLine();
        }
        return input;
    }
    
    
    //Checks if user's move choice is valid per board state (true) or not (false)
    private static boolean isValidInput(String validChoices, String input){
        if (input == null || input.length() != 1 || !Character.isDigit(input.charAt(0)))
            return false;
        if (!validChoices.contains(input))
            return false;
        return true;
    }
    
    
    //updates the choice history map with the current choice (for debug info)
    private void updateChoiceHistory(int choice){
        if (!choiceHistory.containsKey(choice))
            choiceHistory.put(choice, 0);
        choiceHistory.put(choice, choiceHistory.get(choice)+1);
    }
    
    
    //Gets called at the end of every game
    //argument indicates whether this player won, lost,
    //or game was draw (see final values in Player.java)
    public void betweenGames(int didPlayerWin){
        //I don't need to do anything between games!
    }
    
    
    //Returns some extra info useful for debugging
    //Printed at the end of each game ONLY IF the
    //DEBUG_MODE final is set to true in GameLauncher.java
    //
    //Human's debug mode shows history of choices made
    public String getDebugInfo(){
        String[] toReturn = new String[choiceHistory.size()+1];
        int ct = 0;
        int total = 0;
        for (Integer key : choiceHistory.keySet()){
            toReturn[ct++] = "Spot #" + key + " chosen " + choiceHistory.get(key) + " time(s)";
            total += choiceHistory.get(key);
        }
        toReturn[ct] = "This Human took a total of: " + total + " turns!";
        return String.join(",\n", toReturn);
    }
    
}