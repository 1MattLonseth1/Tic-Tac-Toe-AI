import java.util.*;

//An AI player that gets smarter the more it plays!
//Uses "buckets"/marbles to reinforce optimal choices
public class LearningAI implements Player{
    
    
    //Learning AIs buckets.  Keys are ID strings
    private HashMap<String, Bucket> buckets;
    //The marbles on the side for the current game
    private HashMap<String, Integer> sideboard;
    
    
    //Initialize this player.  Called ONCE before any 
    //games are played (does NOT get called between games)
    public void initPlayer(){
        this.buckets = new HashMap<String, Bucket>();
        this.sideboard = new HashMap<String, Integer>();
    }
 
    
    //Returns String description of this player
    public String getDescription(){
        return "Learning AI";
    }
    
    
    //Takes the player's turn per the argument board state
    //Returns an int of the spot the player wants to take (1-9)
    //
    //Example board state: __X_O_O__ indicates:
    //There's an X in spot 3
    //There's an O in spots 5 and 7
    //Spots 1, 2, 4, 6, 8, 9 are free
    //
    //LearningAI uses learning algorithm w/ buckets to make choice
    public int takeTurn(String boardState){
        if (!(buckets.containsKey(boardState))){
            buckets.put(boardState, new Bucket(boardState));
        }

        int marble = buckets.get(boardState).removeRandomMarble();

        sideboard.put(boardState, marble);

        return marble;
    }

    
    //Gets called at the end of every game
    //argument indicates whether this player won, lost,
    //or game was draw (see final values in Player.java)
    public void betweenGames(int didPlayerWin){
        if (didPlayerWin == -1){
            for (String sbID : sideboard.keySet()){
                if (buckets.get(sbID).size() == 0){
                    buckets.get(sbID).addMarble(sideboard.get(sbID));
                }
            }
        }
        else if (didPlayerWin == 0){
            for (String sbID : sideboard.keySet()){
                buckets.get(sbID).addMarble(sideboard.get(sbID));
            }
        }
        else{
            for (String sbID : sideboard.keySet()){
                buckets.get(sbID).addMarble(sideboard.get(sbID));
                buckets.get(sbID).addMarble(sideboard.get(sbID));


            }
        }
    }
    
    
    //Returns some extra info useful for debugging
    //Printed at the end of each game ONLY IF the
    //DEBUG_MODE final is set to true in GameLauncher.java
    //
    //LearningAI's debug info shows contents of its "buckets"
    public String getDebugInfo(){
        Collection<Bucket> vals = buckets.values();
        String[] toReturn = new String[buckets.size()];
        int ct = 0;
        for (Bucket b : vals){
            toReturn[ct++] = b.toString();
        }
        Arrays.sort(toReturn);
        return  String.join(",\n", toReturn);
    }
    
    
}
