package game;

import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Controller implements MouseListener {

  // SINGLETON PATTERN
  public static final Controller controller = new Controller();
  private Controller() {}
  

  
  public void mouseReleased(MouseEvent e) {
    Point p = e.getPoint();
    //System.out.println("mouseDown: " + p);
    
    // HELLO I AM A MOUSE LISTENER AND I HAVE NO POINT IN THIS DESIGN
    // I AM HERE TO STATISFY THE REQUIREMENTS
  }
  public void mouseClicked(MouseEvent e) {}
  public void mousePressed(MouseEvent e) {}
  public void mouseExited(MouseEvent e)  {}
  public void mouseEntered(MouseEvent e) {}


  // ============= functions for pop-up boxes from here ===============
  
  public static int makeGraphicalSelection(String prompt, int min, int max) {
    String title = prompt;
    if (title.contains("\n"))
      title = title.substring(0, title.indexOf('\n'));
    
    if (min < 0 || max<min) throw new IllegalArgumentException(); // we're not designed for this 
    int out = -1;
    while (out<min || out>max) {
      String in = JOptionPane.showInputDialog(null, prompt, title, 1);
      if (in==null) continue;
      try {
        out = Integer.parseInt(in);
      } catch (NumberFormatException e){continue;}
    }
    return out;
  }
  
  public static String makeGraphicalSelection(String prompt, List<String> answers) {
    JTextField firstName = new JTextField();
    JTextField lastName = new JTextField();
    JTextField password = new JTextField();

    ButtonGroup group = new ButtonGroup();
    List<JRadioButton> buttons = new ArrayList<JRadioButton>();
    for (String answer : answers) {
      JRadioButton button = new JRadioButton(answer);
      button.setActionCommand(answer);
      buttons.add(button);
      group.add(button);
    }
    buttons.get(0).setSelected(true);
    JComponent[] inputs = new JComponent[buttons.size()];
    buttons.toArray(inputs);
    JOptionPane.showMessageDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);

    return group.getSelection().getActionCommand();
    /*
    int i=0;
    for (String answer : answers)
      prompt += "\n[" + (i++) + "] " + answer;
    
    return answers.get(makeGraphicalSelection(prompt, 0, answers.size()-1)); // note: calls the method below
     */
  }
  
  public static void infoBox(String info) {
    JOptionPane.showMessageDialog(null, info);
  }
  
  public static boolean makeGraphicalYesNoSelection(String prompt) {
    int answer = -1;
    while (answer != JOptionPane.YES_OPTION && answer != JOptionPane.NO_OPTION) {
      answer = JOptionPane.showConfirmDialog(null, prompt, prompt, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
    return answer == JOptionPane.YES_OPTION;
  }
}
