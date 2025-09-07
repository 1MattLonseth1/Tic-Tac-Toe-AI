import java.util.*;

//A not-so-bright AI for our Tic-Tac-Toe game
//makes purely random choices with no thought or reason!


public class DummyAI implements Player{
    
    //Tracks how many times each spot was chosen (for debug info)
    private HashMap<Integer, Integer> choiceHistory;
    
    
    
    //Initialize this player.  Called ONCE before any 
    //games are played (does NOT get called between games)
    public void initPlayer(){
        choiceHistory = new HashMap<Integer, Integer>();
    }
    
    
    //Returns String description of this player
    public String getDescription(){
        return "Dummy AI";
    }
    
    
    //Takes the player's turn per the argument board state
    //Returns an int of the spot the player wants to take (1-9)
    //
    //Example board state: __X_O_O__ indicates:
    //There's an X in spot 3
    //There's an O in spots 5 and 7
    //Spots 1, 2, 4, 6, 8, 9 are free
    //
    //DummyAI chooses randomly from available spots
    public int takeTurn(String boardState){
        int choice = -1;
        Random random = new Random();
        boolean found = true;
        while (found){
            choice = random.nextInt(9) + 1;
            for (int i = 0; i < choice; i++){
                if (boardState.charAt(i) == '_' && choice == i+1)
                    found = false;
            }
        }
        
        
        //updates the choice history (used for debug info)
        //once the DummyAI has decided on a move
        if (!choiceHistory.containsKey(choice))
            choiceHistory.put(choice, 0);
        choiceHistory.put(choice, choiceHistory.get(choice)+1);
        return choice;
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
    //DummyAI's debug mode shows history of choices made
    public String getDebugInfo(){
        String[] toReturn = new String[choiceHistory.size()+1];
        int ct = 0;
        int total = 0;
        for (Integer key : choiceHistory.keySet()){
            toReturn[ct++] = "Spot #" + key + " chosen " + choiceHistory.get(key) + " time(s)";
            total += choiceHistory.get(key);
        }
        toReturn[ct] = "This Dummy AI took a total of: " + total + " turns!";
        return String.join(",\n", toReturn);
    }
    
    
}
