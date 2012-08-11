package game;

import java.util.*;

public class Player {
  private Tile position;
  
  private String name;
  public String name() { return name; }
  
  private boolean selected;
  public void setSelected(boolean s) { selected = s; }
  public boolean selected() { return selected; }
  
  private List<PlayerAction> availableActions = new ArrayList<PlayerAction>();

  private int movesLeft = 0;
  public int movesLeft() { return movesLeft; }
  public void newTurn() {
    movesLeft = (int)(6.0 * Math.random()) + 1; // 6-sided dice roll
    setSelected(true);
  }
  
  
  public Player(String name) {
    this.name = name;
  }
  
  /**
   * Moves the player to a tile if possible
   * @param t The tile to move the player to
   * @return Whether the player was able to move there
   */
  public boolean moveToTile(Tile t) {
    if (! t.enter(this)) return false;
    if (position != null)
      position.leave(this);
    position = t;
    --movesLeft;
    return true;
  }
  
  public String toString() {
    return (selected) ? "P" : "p";
  }
  
  /**
   * Re-thinks all possible available PlayerActions
   */
  public void computeActions() {
    availableActions.clear();
    for (String description : position.connections().keySet()) {
      Tile neighbour = position.connections().get(description);
      if (neighbour.canMoveHere(this))
        availableActions.add(new PlayerAction_Move(this, neighbour, description));
    }
  }
  
  public void listActions() {
    int i=0;
    for (PlayerAction action : availableActions)
      System.out.println("[" + (i++) + "] " + action);
  }
  
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
}
