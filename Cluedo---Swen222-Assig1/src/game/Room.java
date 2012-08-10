package game;

import java.util.*;


public class Room extends RoomEntrance {


  List<Player> players;
  
  protected boolean isOccupied() {
    return false; // we can have as many player as we want in a room
  }

  protected void addPlayer(Player p) {  players.add(p);     }
  protected void remPlayer(Player p) {  players.remove(p);  }
  
  public boolean enter(Player p) {
    System.out.println("do you want to make a suggestion?"); // TODO 
    return movePlayer(p);
  }
}
