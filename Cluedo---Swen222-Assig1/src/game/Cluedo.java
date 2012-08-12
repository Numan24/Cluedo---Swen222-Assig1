package game;

import java.util.*;


/**
 * The class that pulls the game elements together
 * 
 * @author dom
 *
 */
public class Cluedo {
  
  /* =========== Fields (with getters/setters) =========== */
  
  private List<Player> players = new ArrayList<Player>();
  public List<Player> players() { return Collections.unmodifiableList(players); }
  
  private Player currentPlayer;
  private int currentPlayerID = 0;
  private int playerCount;
  
  private Board board;
  
  private boolean running = true;

  
  /* =========== Constructor =========== */

  
  public Cluedo() {
    board = new Board();

    // we add all players but only use 3-6 some of them
    players.add(new Player("Kasandra Scarlett", this));
    players.add(new Player("Jack Mustard"     , this));
    players.add(new Player("Diane White"      , this));
    players.add(new Player("Jacob Green"      , this));
    players.add(new Player("Eleanor Peacock"  , this));
    players.add(new Player("Victor Plum"      , this));

    playerCount = 0;
    while (playerCount < 3) {
      System.out.println("how many players? (3-6)");
      playerCount = makeSelection(6);
    }
    while (players.size() > playerCount) // remove all unused characters
      players.remove(players.size()-1);
    
    // spawn our players
    for (int i=0; i<playerCount; ++i)
      players.get(i).spawn(board.playerSpawns.get(i));
    
    currentPlayer = players.get(0);
    currentPlayer.newTurn();
  }
  
  
  /* =========== Game main loop =========== */

  
  public void mainLoop() {
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
      currentPlayer.doAction(makeSelection(currentPlayer.actionCount()));
    }
  }
  
  
  /* =========== Other methods =========== */

  
  public void makeSuggestion(Player suggestor) {
    // prepare the suggestion
    List<String> playerNames = new ArrayList<String>();
    for (Player p : players) playerNames.add(p.name());
    String roomName = "some room";
    for (int i=0; i<Board.roomNames.size(); ++i)
      if (board.rooms.get(i) == suggestor.position()) // assumes a player is in a room
        roomName = Board.roomNames.get(i);
    
    // create the suggestion
    Suggestion s = new Suggestion(
      askQuestion("Who was the murderer?", playerNames),
      askQuestion("What was the murder weapon?", Board.weapons),
      roomName // since we're not allowed to choose anything else
    );
    
    // now check of any people who can refute this suggestion
    for (Player refutor : players) {
      String refuteReason = refutor.refute(s);
      if (! refuteReason.equals("")) {
        System.out.println(refutor.name() + " can refute the " + refuteReason);
        return;
      }
      System.out.println(refutor.name() + " can't refute this suggestion.");
    }
    System.out.println("No one can refute this suggestion.  [press enter]");
    new Scanner(System.in).nextLine(); // wait for a enter-press
  }
  
  public static String askQuestion(String Question, List<String> answers) {
    System.out.println(Question);
    int i=0;
    for (String answer : answers)
      System.out.println("[" + (i++) + "] " + answer);
    
    return answers.get(makeSelection(answers.size())); // note: calls the method below
  }
  
  /**
   * Utility: Reads lines from System.in until we get a number that is 0 <= n <= max
   * @return The selection
   */
  public static int makeSelection(int max) {
    Scanner lineScan = new Scanner(System.in);
    
    Scanner s = new Scanner(lineScan.nextLine());
    int ret = -1; // our return value
    if (s.hasNextInt()) ret = s.nextInt();
    while (ret<0 || ret>max) {
      System.out.println("Enter a valid selection. (0-" + max + ")");
      s = new Scanner(lineScan.nextLine());
      if (! s.hasNextInt()) continue;
      ret = s.nextInt();
    }
    
    return ret;
  }
}
