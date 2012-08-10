package game;
import java.util.*;


public abstract class Tile {
  private Set<Tile> connections;
  
  public void addConnection(Tile t) {
    connections.add(t);
  }
  public Set<Tile> getConnections() {
    return Collections.unmodifiableSet(connections);
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
