package game;

public class FloorTile extends Tile {
  private Player player;
  
  private final String originalRepr;
  
  public FloorTile(String repr) {
    representation = repr;
    originalRepr = repr;
  }
  
  protected boolean isOccupied() {
    return player != null;
  }

  protected void addPlayer(Player p) {  player = p;    representation = p.toString();  }
  protected void remPlayer(Player p) {  player = null; representation = originalRepr;  }
}
