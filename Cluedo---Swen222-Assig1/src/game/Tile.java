package game;
import java.util.*;


public abstract class Tile {
  public static final int reprSize = 2; 
  
  protected Map<String, Tile> connections;
  protected String representation = "!";
  
  public String toString() {
    String ret = representation;
    while (ret.length() < reprSize)
      ret += " ";
    return ret;
  }
  
  public void addConnection(String description, Tile t) {
    connections.put(description, t);
  }
  public Map<String, Tile> getConnections() {
    return Collections.unmodifiableMap(connections);
  }

  
  /**
   * Helper for movePlayer(). Checks whether the
   * tile is "full". if a tile is occupied another
   * player can't move on it
   * @return
   */
  abstract protected boolean isOccupied();
  
  
  
  /** Adds player p to internal state. assumes !isOccupied() */ 
  abstract protected void addPlayer(Player p);
  /** Removes player p */
  abstract protected void remPlayer(Player p);
  
  /**
   * Helper for enter(). Moves the player in here
   * unless we are already occupied
   * @param p
   * @return
   */
  protected boolean movePlayer(Player p) {
    if (isOccupied()) return false;
    addPlayer(p);
    return true;
  }
  
  /**
   * Checks whether a player is allowed to come here
   * this can also do something if we are a special tile 
   * such as a room entrance  
   * @return Whether entering was successful
   */
  public boolean enter(Player p) {
    return movePlayer(p);
  }
  
  /**
   * Removes the player from this tile
   * @param p The player that leaves
   */
  public void leave(Player p) {
    remPlayer(p);
  }
}
