package game;

import java.awt.Point;
import java.awt.event.*;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Controller implements MouseListener {

  // SINGLETON PATTERN
  private static Controller c;
  private Controller() {}
  
   
  public void mouseReleased(MouseEvent e) {
    Point p = e.getPoint();
    System.out.println("mouseDown: " + p);
  }
  public void mouseClicked(MouseEvent e) {}
  public void mousePressed(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}

  


  
  
  
  
  public static Controller controller() {
    // warning: ternary expression incoming
    return c = (c==null)? new Controller() : c;
  }
  
  
  
}
