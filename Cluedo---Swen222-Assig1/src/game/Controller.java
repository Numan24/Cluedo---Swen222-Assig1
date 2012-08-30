package game;

import java.awt.Point;
import java.awt.event.*;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Controller implements MouseListener {

  // SINGLETON PATTERN
  public static final Controller controller = new Controller();
  private Controller() {}
  

  
  public void mouseReleased(MouseEvent e) {
    Point p = e.getPoint();
    //System.out.println("mouseDown: " + p);
  }
  public void mouseClicked(MouseEvent e) {}
  public void mousePressed(MouseEvent e) {}
  public void mouseExited(MouseEvent e)  {}
  public void mouseEntered(MouseEvent e) {}


  // ============= functions for pop-up boxes from here ===============
  
  public static int makeGraphicalSelection(String prompt, int min, int max) {
    if (min < 0 || max<min) throw new IllegalArgumentException(); // we're not designed for this 
    int out = -1;
    while (out<min || out>max) {
      String in = JOptionPane.showInputDialog(null, prompt, prompt, 1);
      if (in==null) continue;
      try {
        out = Integer.parseInt(in);
      } catch (NumberFormatException e){continue;}
    }
    return out;
  }
  
  public static String makeGraphicalSelection(String prompt, List<String> answers) {
    int i=0;
    for (String answer : answers)
      prompt += "[" + (i++) + "] " + answer;
    
    return answers.get(makeGraphicalSelection(prompt, 0, answers.size()-1)); // note: calls the method below
  }
  
  public static boolean makeGraphicalYesNoSelection(String prompt) {
    System.out.println(JOptionPane.YES_OPTION + " " + JOptionPane.NO_OPTION);
    int answer = -1;
    while (answer != JOptionPane.YES_OPTION && answer != JOptionPane.NO_OPTION) {
      answer = JOptionPane.showConfirmDialog(null, prompt, prompt, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
    return answer == JOptionPane.YES_OPTION;
  }
}
