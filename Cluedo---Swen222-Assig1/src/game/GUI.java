package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Control;
import javax.swing.*;

public class GUI implements Runnable {
  public GUI() {
    // set system lative look and feel
    try {
      UIManager.setLookAndFeel(   UIManager.getSystemLookAndFeelClassName());
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
    p.addMouseListener(Controller.controller());
    f.add(p, BorderLayout.CENTER);
    
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
    JMenuItem hey = new JMenuItem("hey");
    
    newGame.addActionListener(new ActionListener() {  public void actionPerformed(ActionEvent arg0) {
      System.out.println("new Game");
    }});
    
    quit.addActionListener(new ActionListener() {  public void actionPerformed(ActionEvent arg0) {
      System.exit(0);
    }});
    
    hey.addActionListener(new ActionListener() {  public void actionPerformed(ActionEvent arg0) {
      System.out.println("SAY HEY");
    }});
    
    m.add(newGame);
    m.add(quit);
    m.add(hey);
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

}