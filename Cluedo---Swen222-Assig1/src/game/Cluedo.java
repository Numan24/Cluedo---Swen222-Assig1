package game;

import java.util.*;


/**
 * The class that pulls the game elements together
 * 
 * @author dom
 *
 */
public class Cluedo {
  private List<Player> players = new ArrayList<Player>();
  private Player currentPlayer;
  private int currentPlayerID = -1;
  private int playerCount;
  
  private Board board;
  
  private boolean running = true;
  
  
  
  public Cluedo() {
    board = new Board();

    // we add all players but only use 3-6 some of them
    players.add(new Player("Kasandra Scarlett"));
    players.add(new Player("Jack Mustard"     ));
    players.add(new Player("Diane White"      ));
    players.add(new Player("Jacob Green"      ));
    players.add(new Player("Eleanor Peacock"  ));
    players.add(new Player("Victor Plum"      ));

    playerCount = 3; // TODO: Input for this
    
    // spawn our players
    for (int i=0; i<playerCount; ++i)
      players.get(i).moveToTile(board.playerSpawns.get(i));
    
    currentPlayer = players.get(0);
    currentPlayer.newTurn();
  }
  
  public void mainLoop() {
    Scanner lineScan = new Scanner(System.in);
    while (running) {
      
      // if it's the next players turn
      if (currentPlayer.movesLeft() <= 0) {
        currentPlayer.setSelected(false);
        currentPlayerID = (currentPlayerID + 1) % playerCount;
        currentPlayer = players.get(currentPlayerID);
        currentPlayer.newTurn();
      }
      System.out.print("\n\n\n\n\n");
      System.out.print(board.toString());
      System.out.println("It's " +currentPlayer.name()+ " turn. " +currentPlayer.movesLeft()+ " moves left.");

      currentPlayer.computeActions();
      currentPlayer.listActions();
      
      // ask for actions until we get a valid action execution 
      Scanner s = new Scanner(lineScan.nextLine());
      while ((!s.hasNextInt()) || (! currentPlayer.doAction(s.nextInt()))) {
        System.out.println("Enter a valid action number");
        s = new Scanner(lineScan.nextLine());
      }
    }
  }
}
