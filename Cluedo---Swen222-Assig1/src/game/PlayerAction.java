package game;

public interface PlayerAction {
  
  /**
   * Does the action that the class i supposed to do
   * note: in a functional language this would be wayy simpler
   * @throws InvalidAction 
   */  
  public void execute() throws InvalidAction;
  
  public class InvalidAction extends Error {} // these should never happen
}