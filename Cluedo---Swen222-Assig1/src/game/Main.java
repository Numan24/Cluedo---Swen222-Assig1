package game;

import java.util.Arrays;

import javax.swing.JOptionPane;
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

    GUI gui = new GUI(game);
    SwingUtilities.invokeLater(gui);
    gui.animate();
  }
}
