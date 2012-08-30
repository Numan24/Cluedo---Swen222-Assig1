package game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class GUI implements Runnable {
  
  private List<JButton> s = new ArrayList<JButton>();
  
  public final int tileSize = 20;
  
  public GUI() {
    // set system native look and feel
    try {
      //UIManager.setLookAndFeel(   UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {   throw new Error(e);  } // catching exception because otherwise it's really ugly.
  }
  
  public void run() {
    
    JFrame f = new JFrame("Cluedo");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setLayout(new BorderLayout());
    createMenuBar(f);
    createGameComponent(f);
    //f.add(new JLabel("Hello, world!"), BorderLayout.CENTER);
    //JButton b = new JButton("Press me!");
    //f.add(b, BorderLayout.SOUTH);
    f.pack();
    f.setVisible(true);
  }

  private void createGameComponent(JFrame f) {
    //JPanel gameView = new JPanel(new BorderLayout());
    ImagePanel p = new ImagePanel(Resources.board);
    p.setLayout(null);
    p.addMouseListener(Controller.controller);
    f.add(p, BorderLayout.CENTER);

    createGameButtons(p);
  }
  
  private void createGameButtons(JComponent c) {
    JButton b = new ActionButton(3); // TODO
    
    Insets insets = c.getInsets();
    c.setBounds(0 + insets.left, 0 + insets.top,  20, 20);
    c.add(b);
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
      super("!");
      
      addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
          System.out.println("hi: " + actionNumber);
        }
      });
      

      setOpaque(false);
      setContentAreaFilled(false);
      
      //setBorderPainted(false);
    }
    /*
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0));
        super.paint(g2);
        g2.dispose();
    }*/
  }
}