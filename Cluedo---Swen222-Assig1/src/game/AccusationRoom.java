package game;

import java.util.Arrays;

public class AccusationRoom extends Room {
  public boolean enter(Player p, Cluedo game) {
    if (! movePlayer(p)) return false;
    
    if (
      // (yes/no question) == yes
      //Cluedo.askQuestion("do you want to make a murder ACCUSATION?", Arrays.asList("No", "Yes"))
      //.equals("Yes")
      Controller.makeGraphicalYesNoSelection("do you want to make a murder suggestion?")
    ) {
      game.makeAccusation(p);
    }
    
    return true;
  }
}
