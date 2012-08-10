package game;


/**
 * The class that pulls the game elements together
 * 
 * @author dom
 *
 */
public class Cluedo {
  private Board board;
  
  boolean running = false; // TODO
  
  public Cluedo() {
    board = new Board();

    System.out.println(board.toString());
  }
  
  public void mainLoop() {
    while (running) {
      
    }
  }
}
