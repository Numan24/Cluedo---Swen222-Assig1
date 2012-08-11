package game;

public class FloorTile extends Tile {
  private Player player;
  
  private final String originalRepr;
  
  public FloorTile(String repr) {
    originalRepr = repr;
  }
  
  protected void addPlayer(Player p) {  player = p;    }
  protected void remPlayer(Player p) {  player = null; }
  
  public String toString() {
    if (player != null) return player.toString();
    else                return originalRepr;  
  }

  @Override
  public boolean canMoveHere(Player p) {
    if (p == player) return true; // a player can stand still
    return player == null;
  }
}
