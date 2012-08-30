package game;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Just a class to say "GO GO GO!" to the project
 * @author dom
 */
public class Main {
  public static void main(String[] args) {
    Cluedo game = new Cluedo();
    //game.mainLoop();
    Resources.load();

    SwingUtilities.invokeLater(new GUI(game));
  }
}
