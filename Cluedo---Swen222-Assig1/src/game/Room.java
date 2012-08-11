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
  
  public boolean enter(Player p) {
    System.out.println("do you want to make a suggestion?");
    System.out.println("[0] No");
    System.out.println("[1] Yes");

    int wantsTo = Cluedo.makeSelection(1); // TODO

    return movePlayer(p);
  }
  
  public String toString() {
    if (players.size() == 0) return "r";
    
    // return the selected 
    String lastPlayerRepr = "";
    for (Player p : players) {
      lastPlayerRepr = p.toString();
      if (p.selected()) // if uppercase
        return lastPlayerRepr;
    }
    return lastPlayerRepr;
  }

  @Override
  public boolean canMoveHere(Player p) { return true; } 
}
