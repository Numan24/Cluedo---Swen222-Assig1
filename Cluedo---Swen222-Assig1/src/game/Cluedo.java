package game;

import java.util.*;

import javax.swing.JOptionPane;


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
  
  private Suggestion solution;
  
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

    /*
    playerCount = 0;
    while (playerCount < 3) {
      System.out.println("how many players? (3-6)");
      playerCount = makeSelection(6);
    }
    */
    playerCount = Controller.makeGraphicalSelection("How many players? (3-6)", 3, 6);
    while (players.size() > playerCount) // remove all unused characters
      players.remove(players.size()-1);
    
    // deal out cards
    List<String> murderers = playerNames();
    List<String> weapons   = new LinkedList<String>(Board.weapons);
    List<String> roomNames = new LinkedList<String>(Board.roomNames.values());
    Collections.shuffle(murderers);
    Collections.shuffle(weapons  );
    Collections.shuffle(roomNames);
    
    // create the solution
    solution = new Suggestion(
      murderers.remove(0),
      weapons.remove(0),
      roomNames.remove(0)
    );
    
    // DEBUG: show the solution at the start
    //System.out.println(solution);
    
    // deal out all other cards to your players
    List<String> remainingCards = new ArrayList<String>();
    remainingCards.addAll(murderers);
    remainingCards.addAll(weapons);
    remainingCards.addAll(roomNames);
    int playerID = 0;
    for (String card : remainingCards) { 
      players.get(playerID).addCard(card);
      playerID = (playerID+1)%playerCount; 
    }
    
    // spawn our players
    for (int i=0; i<playerCount; ++i)
      players.get(i).spawn(board.playerSpawns.get(i));
    
    currentPlayer = players.get(0);
    currentPlayer.newTurn();
  }
  
  
  /* =========== Game main loop (for text-based version) =========== */

  
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
      currentPlayer.doAction(makeSelection(currentPlayer.actionCount()-1));
    }
  }
  
  
  /* =========== Other methods =========== */

  private List<String> playerNames() {
    List<String> playerNames = new ArrayList<String>();
    for (Player p : players) playerNames.add(p.name());
    return playerNames;
  }
  
  public void makeAccusation(Player accuser) {
    Suggestion accusation = new Suggestion(
      /* // non-graphical code
      askQuestion("Who was the murderer?", playerNames()),
      askQuestion("What was the murder weapon?", Board.weapons),
      askQuestion("In which room was the murder in?", new LinkedList<String>(Board.roomNames.values()))
      */
      Controller.makeGraphicalSelection("Who was the murderer?", playerNames()),
      Controller.makeGraphicalSelection("What was the murder weapon?", Board.weapons),
      Controller.makeGraphicalSelection("In which room was the murder in?", new LinkedList<String>(Board.roomNames.values()))
    );
    
    /*
    System.out.print("You were ...");
    // build suspense
    for (int i=0; i<5; ++i) {
      System.out.print(".");
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) { }
    }
    */

    Controller.infoBox("You were ...");
    Controller.infoBox("..");
    Controller.infoBox("...");
    Controller.infoBox("SUSPENSE!  IMMERSION!"); // some silliness allowed
    Controller.infoBox("...");
    
    // result
    if (accusation.equals(solution)) {
      // accuser wins 
      //System.out.println(" right!");
      //System.out.println(accuser.name()+ " wins the game!");

      Controller.infoBox("..RIGHT!\n" +accuser.name()+ " wins the game!");
      running = false;
    }
    else {
      // Accuser dies! :o
      //System.out.println(" wrong!");
      //System.out.println(accuser.name()+ " dies of shame.");
      Controller.infoBox("..WRONG!\n" +accuser.name()+ " dies of shame.");
      currentPlayer.doAction(0); // consume the rest of the turn
      players.remove(accuser);
      --playerCount;
      
      if (playerCount == 0) { // if everybody died, end the game
        //System.out.println("Nobody could figure that it was " + solution);
        Controller.infoBox("Nobody could figure that it was " + solution);
        running = false;
      }
    }
  }
  
  public void makeSuggestion(Player suggestor) {
    // prepare the suggestion
    String roomName = "some room"; // this one should never happen
    for (int i=0; i<Board.roomNames.size(); ++i)
      if (board.rooms.get(i) == suggestor.position()) // assumes a player is in a room
        roomName = Board.roomNames.get(i);
    
    // create the suggestion
    Suggestion s = new Suggestion(
      //askQuestion("Who was the murderer?", playerNames()),
      //askQuestion("What was the murder weapon?", Board.weapons),
      Controller.makeGraphicalSelection("Who was the murderer?", playerNames()),
      Controller.makeGraphicalSelection("What was the murder weapon?", Board.weapons),
      roomName // since we're not allowed to choose anything else
    );
    
    // now check of any people who can refute this suggestion
    for (Player refutor : players) {
      String refuteReason = refutor.refute(s);
      if (! refuteReason.equals("")) {
        //System.out.println(refutor.name() + " can refute the murder " + refuteReason);
        //waitForNewLine();
        Controller.infoBox(refutor.name() + " can refute the murder " + refuteReason);
        return;
      }
      //System.out.println(refutor.name() + " can't refute this suggestion.");
    }
//    System.out.println("No one can refute this suggestion.");
//    waitForNewLine();
    Controller.infoBox("No one can refute this suggestion.");
  }
  
  public static String askQuestion(String Question, List<String> answers) {
    System.out.println(Question);
    int i=0;
    for (String answer : answers)
      System.out.println("[" + (i++) + "] " + answer);
    
    return answers.get(makeSelection(answers.size()-1)); // note: calls the method below
  }
  
  /** Utility: pauses the game until enter is pressed */
  public static void waitForNewLine() {
    System.out.println("[press enter]");
    new Scanner(System.in).nextLine(); // wait for new line
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
  
  
  // ============= things for the GUI from here on =================
  
  // getters
  public Player currentPlayer() {  return currentPlayer; }
  public Board board() { return board; }
  
  public void nextTurn() {
  //if it's the next players turn
    if (currentPlayer.movesLeft() <= 0) {
      currentPlayer.setSelected(false);
      currentPlayerID = (currentPlayerID + 1) % playerCount;
      currentPlayer = players.get(currentPlayerID);
      currentPlayer.newTurn();
    }
    /*
    System.out.print("\n\n\n\n\n");
    System.out.print(board.toString());
    System.out.println("It's " +currentPlayer.name()+ " turn. " +currentPlayer.movesLeft()+ " moves left.");
    */
    
    currentPlayer.computeActions();
  }
}
