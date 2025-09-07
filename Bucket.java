import java.util.*;

//A "bucket" storing marbles
//used by our LearningAI to reinforce good choices (that lead to wins)
//and disincentivize bad choices (that lead to losses!)

public class Bucket{
    
    //the number of starting marbles for each valid choice at instantiation
    private static int START_MARBLES = 3;
    
    //The bucket's ID, which is the board state it represents choices for
    //ex: "_X__OX__O"
    private String id;
    
    //A map of marble numbers to quantities (key=marble #, value=quantity)
    //ex: {2:4, 5:3, 6:1} means that this bucket has
    //    four #2 marbles, three #5 marbles, and one #6 marble
    //    (for a total of 8 marbles)
    private HashMap<Integer, Integer> marbles;
    
    
    public Bucket(String id){
        this.id = id;
        this.marbles = new HashMap<Integer, Integer>();


        for (int i = 1; i < 10; i++){
            if (id.charAt(i-1) == '_')
                marbles.put(i, START_MARBLES);
        }

        
        //***  Implement me for Part 3.2!!  ***
        //What else needs to happen here??

        //What should the initial state of a Bucket look like? - All marbles at 3

        //Create Buckets and test w/ toString in GameLauncher
        
        
        
        
    }
    
    
    //removes and returns a random marble, weighted by the marble quantities
    //returns the marble chosen and also REMOVES this marble from marbles
    //
    //For example, given marbles = {2:3, 5:1, 8:6} this function has:
    //   -a 30% chance of removing a #2 marble
    //   -a 10% chance of removing a #5 marble
    //   -a 60% chance of removing a #8 marble
    public int removeRandomMarble(){
        if (this.size() == 0)
            throw new IllegalStateException("ERROR! Cannot remove marble from empty bucket!");
        int pick = (int)(Math.random() * this.size());
        int accum = 0;
        for (Integer marble : marbles.keySet()){
            accum += marbles.get(marble);
            if (pick < accum){
                marbles.put(marble, marbles.get(marble)-1);
                return marble;
            }
        }
        return -1; //unreachable, (necessary to keep the compiler happy)
    }
    
    
    //Adds a marble of the argument number to this Bucket
    public void addMarble(int marbleNum){
        if (!marbles.containsKey(marbleNum))
            marbles.put(marbleNum, 0);
        marbles.put(marbleNum, marbles.get(marbleNum)+1);
    }
    
    
    //Returns the total number of marbles in this Bucket
    public int size(){
        int totalSize = 0;
        for (Integer i : marbles.values()){
            totalSize += i;
        }
        return totalSize;
    }
    
    
    //Returns a nice, pretty String representation of this 
    //Bucket (including the Bucket's id and its stored marbles)
    public String toString(){
        String toReturn = "";
        for (int i = 1; i <= Player.BOARD_LEN; i++){
            if (marbles.containsKey(i))
                toReturn += "#" + i + "x" + marbles.get(i) + ", ";
        }
        if (toReturn.length() > 0)
            toReturn = toReturn.substring(0,toReturn.length()-2);
        return "Bucket " + this.id + ": [" + toReturn + "]";
    }
       
}
