
public class FloorTile extends Tile {
  Player player;
  
  protected boolean isOccupied() {
    return player != null;
  }

  protected void addPlayer(Player p) {  player = p;     }
  protected void remPlayer(Player p) {  player = null;  }
}
