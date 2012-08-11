package game;

public class Suggestion {
  private String player;
  private String weapon;
  private String room;

  Suggestion(String player, String weapon, String room) {
    this.player = player;
    this.weapon = weapon;
    this.room = room;
  }

  public String getPlayer() { return player; }
  public String getWeapon() { return weapon; }
  public String getRoom()   { return room;   }
  
}
