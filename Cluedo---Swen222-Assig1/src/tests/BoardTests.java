package tests;

import static org.junit.Assert.*;
import game.Board;

import org.junit.Test;

public class BoardTests {

  @Test
  public void test_isInBounds() {
    Board board = new Board();
    
    assertTrue(board.isInBounds(0,0));
    assertTrue(board.isInBounds(1,0));
    assertTrue(board.isInBounds(0,1));
    assertTrue(board.isInBounds(1,1));
    assertTrue(board.isInBounds(6,7));
    
    assertFalse(board.isInBounds(-1,0));
    assertFalse(board.isInBounds(0,-1));
    assertFalse(board.isInBounds(-1,-1));
    assertFalse(board.isInBounds(1000,0));
    assertFalse(board.isInBounds(0,135));
    assertFalse(board.isInBounds(325,452));
    assertFalse(board.isInBounds(-32,2345));
  }

}
