package game;

public class Player {
//  int movesLeft;
  private Tile position;
  
  /**
   * Moves the player to a tile if possible
   * @param t The tile to move the player to
   * @return Whether the player was able to move there
   */
  public boolean moveToTile(Tile t) {
    if (! t.enter(this)) return false;
    position.leave(this);
    position = t;
    return true;
  }
}
