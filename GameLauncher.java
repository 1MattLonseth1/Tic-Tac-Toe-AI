import java.util.*;

//Used to create and run a Tic-Tac-Toe game and its respective Players
public class GameLauncher {

    
    //Toggle to enable extra output at the end of games
    //Debug output is different for each Player type
    //(helpful for implementing/debugging player logic!)
    public static boolean DEBUG_MODE = true;
    
    
    
    public static void main(String[] args){
       Player p1 = new Human();
       Player p2 = new LearningAI();
       //Player p3 = new LearningAI();
       //Player p4 = new DummyAI();
       //Player p5= new DummyAI();
       
       TicTacToe game = new TicTacToe(p2, p1);

       // Bucket b1 = new Bucket("XX_____OO");
       // System.out.println(b1.toString());

       // Bucket b2 = new Bucket("X___O__OO");
       // System.out.println(b2.toString());

       // Bucket b3 = new Bucket("____X__O_");
       // System.out.println(b3.toString());

       // Bucket b4 = new Bucket("_________");
       // System.out.println(b4.toString());


       
       game.runInteractiveGames();

       //game.runBatchGames(100000);


       
       //Run a batch of n games, without any input (aside from the final results)
       //CANNOT be used if either/both players are Human
       //game.runBatchGames(50);
    }

}
