package game;

import java.awt.Color;
import java.util.*;

import org.hamcrest.core.IsInstanceOf;


public class Player {

  /* =========== Fields (with getters/setters) =========== */
  
  private Tile position;
  public Tile position() { return position; }
  
  private String name;
  public String name() { return name; }
  
  private boolean selected;
  public void setSelected(boolean s) { selected = s; }
  public boolean selected() { return selected; }
  
  private List<Action> availableActions = new ArrayList<Action>();

  public Color colour = new Color(255, 0, 0);
  
  private List<String> cards = new ArrayList<String>();
  public void addCard(String card) { cards.add(card); }
  
  private Cluedo game;
  
  private int movesLeft = 0;
  public int movesLeft() { return movesLeft; }
  public void newTurn() {
    movesLeft = (int)(60.0 * Math.random()) + 1; // 6-sided dice roll
    setSelected(true);
  }
  
  
  /* =========== Constructor =========== */

  public Player(String name, Cluedo game) {
    this.name = name;
    this.game = game;
  }

  /* =========== Methods =========== */
  
  /** puts the player at a position. Should only be used for initialisation! */
  public void spawn(Tile spawnPos) {
    position = spawnPos;
    spawnPos.enter(Player.this, null);
  }

  public String toString() {
    return (selected) ? "P" : "p";
  }
  
  /**
   * Asks the player whether he can refute any part of the suggestion
   * @param s The suggestion that could be refuted
   * @return The empty string (iff can't refute) or what has been refuted
   */
  public String refute(Suggestion s) {
    if (canRefute(s.getPlayer()))  return "person";
    if (canRefute(s.getWeapon()))  return "weapon";
    if (canRefute(s.getRoom()  ))  return "room";
    return "";
  }
  
  /** if he have the said card we can refute */
  private boolean canRefute(String card) {
    for (String myCard : cards)
      if (myCard == card)
        return true;
    return false;
  }
  
  /**
   * Re-thinks all possible available PlayerActions
   */
  public void computeActions() {
    availableActions.clear();
    
    availableActions.add(new Action_Idle()); // idle
    
    // movement actions
    for (String description : position.connections().keySet()) {
      Tile neighbour = position.connections().get(description);
      if (neighbour.canMoveHere(this))
        availableActions.add(new Action_Move(neighbour, description));
    }
    
    // TODO: room actions
  }
  
  /** Prints out all possible actions in a nice user-readable way */
  public void listActions() {
    int i=0;
    for (Action action : availableActions)
      System.out.println("[" + (i++) + "] " + action);
  }
  
  /** @return The number of current possible actions  */
  public int actionCount() { return availableActions.size(); }
  
  /**
   * Executes the action with the specified index
   * @param i The index of the action
   * @return Whether the move was successful
   */
  public boolean doAction(int i) {
    if (i<0 || i>=availableActions.size()) return false;
    availableActions.get(i).execute();
    return true;
  }
  
  /** returns the tiles that the player can move to (associated by their action number) */
  public Map<Integer, Tile> getAvailableTiles() {
    Map<Integer, Tile> ret = new HashMap<Integer, Tile>();
    for (int i=0; i<availableActions.size(); ++i) {
      if (availableActions.get(i) instanceof Action_Move) { // fuuuuuuuuuuu
        Action_Move a = (Action_Move)availableActions.get(i);
        ret.put(i, a.to);
      }
    }
    return ret;
  }
  
  // =========== Player-Actions from here on ===========
  
  /**
   * Does the action that the class i supposed to do
   * note: in a functional language this would be wayy simpler
   */  
  private abstract class Action {
    public abstract void execute();
  }
  
  private class Action_Move extends Action {
    public Tile to;
    String description;
    
    Action_Move(Tile to, String description) {
      this.to = to;
      this.description = description;
    }
    
    /**
     * Moves the player to a tile
     * @param t The tile to move the player to
     * @return Whether the player was able to move there
     */
    public void execute() {
      to.enter(Player.this, game);
      if (position != null)
        position.leave(Player.this);
      position = to;
      --movesLeft;
    }
    
    public String toString() { return description;  }
  }
  
  /** Consumes a turn */
  class Action_Idle extends Action {
    public void execute() {   movesLeft = 0;  }
    public String toString() { return "Idle  (ends turn)"; }
  } 
}