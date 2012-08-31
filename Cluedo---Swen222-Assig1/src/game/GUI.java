package game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class GUI implements Runnable {
  
  JFrame frame;
  
  private JComponent gameArea;
  private List<JButton> moveButtons = new ArrayList<JButton>();
  
  public final int tileSize = 16;
  
  private Cluedo game;
  
  public GUI(Cluedo game) {
    this.game = game;
    // set system native look and feel
    try {
      //UIManager.setLookAndFeel(   UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {   throw new Error(e);  } // catching exception because otherwise it's really ugly.
  }
  
  public void run() {
    game.nextTurn();

    frame = new JFrame("Cluedo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    createMenuBar(frame);
    createGameComponent(frame);
    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);
    redraw();
  }

  private void redraw() {
    createTileButtons(game.currentPlayer().getAvailableTiles());
    frame.repaint();
  }
  
  private void createGameComponent(JFrame f) {
    //JPanel gameView = new JPanel(new BorderLayout());
    ImagePanel p = new ImagePanel(Resources.board);
    gameArea = p;
    p.setLayout(null);
    p.addMouseListener(Controller.controller);
    f.add(p, BorderLayout.CENTER);
  }
  
  private void createTileButtons(Map<Integer, Tile> tiles) {
    // remove all buttons
    for (JButton b : moveButtons)
      gameArea.remove(b);
    moveButtons.clear();
    
    // create the new ones
    for (Integer i : tiles.keySet()) {
      Point coords = game.board().getTileCoordinates(tiles.get(i));

      JButton b = new ActionButton(i);
      Insets insets = gameArea.getInsets();
      b.setBounds(insets.left + coords.x*tileSize, insets.top + coords.y*tileSize,  tileSize, tileSize);
      gameArea.add(b);
      moveButtons.add(b);
    }
  }

  private void createMenuBar(JFrame f) {
    JMenuBar bar = new JMenuBar();
    createGameMenu(bar);
    f.add(bar, BorderLayout.NORTH);
  }
  private void createGameMenu(JMenuBar bar) {
    JMenu m = new JMenu("Game");
    bar.add(m);
    
    JMenuItem newGame = new JMenuItem("New Game");
    JMenuItem quit = new JMenuItem("Quit");
    
    newGame.addActionListener(new ActionListener() {  public void actionPerformed(ActionEvent arg0) {
      System.out.println("new Game");
    }});
    
    quit.addActionListener(new ActionListener() {  public void actionPerformed(ActionEvent arg0) {
      System.exit(0);
    }});
    
    m.add(newGame);
    m.add(quit);
  }
  
  
  /**
   * Java doesn't have simple sprites, so i made my own >.>
   * @author dom
   *
   */
  public class ImagePanel extends JPanel{

    private BufferedImage image;
    
    public ImagePanel(BufferedImage image) {
      this.image = image;
    }
  
    public Dimension getPreferredSize() {
      return new Dimension(image.getWidth(), image.getHeight());
    }
    
    @Override
    public void paintComponent(Graphics g) {
      g.drawImage(image, 0, 0, null); 
      // see javadoc for more info on the parameters
    }
  }

  
  public class ActionButton extends JButton {
    
    
    public ActionButton(final int actionNumber) {
      super();
      
      addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
          game.currentPlayer().doAction(actionNumber);
          game.nextTurn();
          game.currentPlayer().position();
          redraw();
        }
      });
      

      //setOpaque(false);
      //setContentAreaFilled(false);
      //setBorderPainted(false);

      setBackground(new Color(0, 0, 0, 100)); // semi-transparent

    }
  }
}