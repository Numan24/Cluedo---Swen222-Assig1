package game;

import java.util.*;


public class Room extends Tile {

  List<Player> players = new ArrayList<Player>();
  
  public Room() { }
  
  protected boolean isOccupied() {
    return false; // we can have as many player as we want in a room
  }

  protected void addPlayer(Player p) {  players.add(p);     }
  protected void remPlayer(Player p) {  players.remove(p);  }
  
  public boolean enter(Player p, Cluedo game) {
    if (! movePlayer(p)) return false;
    
    if (
      Cluedo.askQuestion("do you want to make a murder suggestion?", Arrays.asList("No", "Yes"))
      .equals("Yes")
    ) {
      game.makeSuggestion(p);
    }
    
    return true;
  }
  
  public String toString() {
    if (players.size() == 0) return "r";
    
    // return the selected 
    String lastPlayerRepr = "";
    for (Player p : players) {
      lastPlayerRepr = p.toString();
      if (p.selected())
        return lastPlayerRepr;
    }
    return lastPlayerRepr;
  }

  @Override
  public boolean canMoveHere(Player p) { return true; } 
}
