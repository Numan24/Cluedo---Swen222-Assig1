package game;

import javax.swing.SwingUtilities;

/**
 * Just a class to say "GO GO GO!" to the game
 * @author dom
 */
public class Main {
  public static void main(String[] args) {
    //Cluedo game = new Cluedo();
    //game.mainLoop();
    
    SwingUtilities.invokeLater(new GUI());
  }
}
