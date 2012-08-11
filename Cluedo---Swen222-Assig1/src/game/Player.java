package game;

import java.util.*;


public class Player {

  /* =========== Fields (with getters/setters) =========== */
  
  private Tile position;
  
  private String name;
  public String name() { return name; }
  
  private boolean selected;
  public void setSelected(boolean s) { selected = s; }
  public boolean selected() { return selected; }
  
  private List<Action> availableActions = new ArrayList<Action>();

  private int movesLeft = 0;
  public int movesLeft() { return movesLeft; }
  public void newTurn() {
    movesLeft = (int)(6.0 * Math.random()) + 1; // 6-sided dice roll
    setSelected(true);
  }
  
  
  /* =========== Constructor =========== */
  public Player(String name) {
    this.name = name;
  }
  

  /* =========== Methods =========== */
  
  /** puts the player at a position. Should only be used for initialisation! */
  public void spawn(Tile spawnPos) {
    position = spawnPos;
    spawnPos.enter(Player.this);
  }

  public String toString() {
    return (selected) ? "P" : "p";
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
  
  
  // =========== Player-Actions from here on ===========
  
  /**
   * Does the action that the class i supposed to do
   * note: in a functional language this would be wayy simpler
   */  
  private abstract class Action {
    public abstract void execute();
  }
  
  private class Action_Move extends Action {
    Tile to;
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
      to.enter(Player.this);
      if (position != null)
        position.leave(Player.this);
      position = to;
      --movesLeft;
    }
    
    public String toString() { return description;  }
  }
  
  /** Consumes a turn */
  private class Action_Idle extends Action{
    public void execute() {   movesLeft = 0;  }
    public String toString() { return "Idle  (ends turn)"; }
  } 
}
