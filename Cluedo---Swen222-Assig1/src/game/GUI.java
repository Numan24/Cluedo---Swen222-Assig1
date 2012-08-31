package game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class GUI implements Runnable {
  
  JFrame frame;
  
  JButton playerAnimation = new JButton();
  
  private JComponent gameArea;
  private List<JButton> moveButtons = new ArrayList<JButton>();
  
  public final int tileSize = 16;
  
  private boolean animating = false;
  
  private Cluedo game;
  
  public GUI(Cluedo game) {
    this.game = game;
  }
  
  public void run() {
    game.nextTurn();

    frame = new JFrame("Cluedo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    createMenuBar(frame);
    createGameComponent(frame);
    createActionBar(frame);
    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);
    redraw();
    animating = true;
  }

  private synchronized void redraw() {
    createTileButtons(game.currentPlayer().getAvailableTiles());
    createPlayerLabel();
    frame.repaint();
  }
  
  private void createPlayerLabel() {
    Point coords = game.board().getTileCoordinates(game.currentPlayer().position());
    Insets insets = gameArea.getInsets();
    playerAnimation.setBounds(insets.left + coords.x*tileSize, insets.top + coords.y*tileSize,  tileSize, tileSize);
    playerAnimation.setEnabled(false);
    playerAnimation.setToolTipText(game.currentPlayer().name());
  }
  
  
  private void createActionBar(JFrame f) {
    JButton idleButton = new JButton("Idle \n(ends turn)");
    idleButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doPlayerTurn(0);
      }
    });
    f.add(idleButton, BorderLayout.SOUTH);
  }
  
  private void createGameComponent(JFrame f) {
    ImagePanel p = new ImagePanel(Resources.board);
    gameArea = p;
    p.setLayout(null);
    p.addMouseListener(Controller.controller);
    p.add(playerAnimation);
    f.add(p, BorderLayout.CENTER);
  }
  
  private void createTileButtons(Map<Integer, Tile> tiles) {
    // remove all buttons
    for (JButton b : moveButtons)
      gameArea.remove(b);
    moveButtons.clear();
    
    // create the new ones
    for (Integer i : tiles.keySet()) {
      JButton b = new ActionButton(i);
      b.setToolTipText(game.currentPlayer().getActionDescription(i));

      Point coords = game.board().getTileCoordinates(tiles.get(i));
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
      game = new Cluedo();
      game.nextTurn();
      redraw();
    }});
    
    quit.addActionListener(new ActionListener() {  public void actionPerformed(ActionEvent arg0) {
      System.exit(0);
    }});
    
    m.add(newGame);
    m.add(quit);
  }
  
  
  protected void doPlayerTurn(int actionNumber) {
    game.currentPlayer().doAction(actionNumber);
    game.nextTurn();
    redraw();
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
  
  
  /** A special buttona that causes a specific player-action to happen */
  public class ActionButton extends JButton {
    
    public ActionButton(final int actionNumber) {
      super();
      
      addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
          doPlayerTurn(actionNumber);
        }
      });
      
      //setOpaque(false);
      //setContentAreaFilled(false);
      //setBorderPainted(false);
      setBackground(new Color(0, 0, 0, 100)); // semi-transparent
    }

  }

  public void animate() {
    try {
      while (!animating) // wait for the GUI to load up
        Thread.sleep(50);
      while (animating) {
        playerAnimation.setBackground(new Color(200,200,200,250));
        redraw();
        Thread.sleep(500);
        playerAnimation.setBackground(new Color(0,0,0,0));
        redraw();
        Thread.sleep(500);
      }
    }catch (InterruptedException e) {}
  }
}