package tests;

import static org.junit.Assert.*;
import game.FloorTile;
import game.Player;
import game.RoomEntrance;
import game.Tile;

import org.junit.Test;

public class TileTests {

  @Test
  public void test_canMoveHere() {
    test_canMoveHere_FloorTile(new FloorTile("."));
    test_canMoveHere_FloorTile(new RoomEntrance(3));
  }
  
  private void test_canMoveHere_FloorTile(FloorTile t) {
    Player a = new Player("alice", null);
    Player b = new Player("bob", null);
    assertTrue(t.canMoveHere(a));
    assertTrue(t.canMoveHere(b));
    a.spawn(t);  // move alice onto the tile
    assertTrue(t.canMoveHere(a));
    assertFalse(t.canMoveHere(b));
    t.leave(a); // move alice away
    assertTrue(t.canMoveHere(a));
    assertTrue(t.canMoveHere(b));
  }

}
