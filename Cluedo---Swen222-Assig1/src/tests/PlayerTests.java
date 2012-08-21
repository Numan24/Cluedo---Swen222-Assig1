package tests;

import static org.junit.Assert.*;
import game.FloorTile;
import game.Player;
import game.Tile;

import org.junit.Test;

public class PlayerTests {
  @Test
  public void testName() {
    Player p = new Player("bob", null);
    assertTrue(p.name().equals("bob"));
  }
  
  @Test
  public void testMovesLeft() {
    Player p = new Player("bob", null);
    for (int i=0; i<10; ++i) { // repeat since we're dealing with random numbers
      p.newTurn();
      assertTrue(p.movesLeft() > 0);
      assertTrue(p.movesLeft() <= 6);
    }
  }
    
  @Test
  public void testPosition() {
    Player p = new Player("bob", null);
    Tile t1 = new FloorTile(".");
    Tile t2 = new FloorTile(".");
    p.spawn(t1);
    assertTrue(p.position() == t1);
  }

}
