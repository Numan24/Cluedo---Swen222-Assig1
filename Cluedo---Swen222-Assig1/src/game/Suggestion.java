package game;

public class Suggestion {


  private String player;
  private String weapon;
  private String room;

  public Suggestion(String player, String weapon, String room) {
    this.player = player;
    this.weapon = weapon;
    this.room = room;
  }

  public String getPlayer() { return player; }
  public String getWeapon() { return weapon; }
  public String getRoom()   { return room;   }


  @Override
  public boolean equals(Object obj) {
    if (this == obj)   return true;
    if (obj == null)   return false;
    if (getClass() != obj.getClass())   return false;
    Suggestion other = (Suggestion) obj;
    return (
      player.equals(other.player) &&
      weapon.equals(other.weapon) &&
      room.equals(  other.room  )
    );
  }
  
  public String toString() {
    return player + " wtih " + weapon + " in the " + room;
  }
}
