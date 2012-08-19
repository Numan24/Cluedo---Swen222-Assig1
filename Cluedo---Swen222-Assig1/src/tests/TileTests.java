package tests;

import static org.junit.Assert.*;
import game.AccusationRoom;
import game.FloorTile;
import game.Player;
import game.Room;
import game.RoomEntrance;
import game.Tile;

import org.junit.Test;

public class TileTests {

  @Test
  public void test_canMoveHere() {
    test_canMoveHere_FloorTile(new FloorTile("."));
    test_canMoveHere_FloorTile(new RoomEntrance(3));

    // can not test easily as these require input when spawning
    //test_canMoveHere_Room(new Room());
    //test_canMoveHere_Room(new AccusationRoom());
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
  private void test_canMoveHere_Room(Room t) {
    Player a = new Player("alice", null);
    Player b = new Player("bob", null);
    assertTrue(t.canMoveHere(a));
    assertTrue(t.canMoveHere(b));
    a.spawn(t);  // move alice onto the tile
    assertTrue(t.canMoveHere(a));
    assertTrue(t.canMoveHere(b));
    t.leave(a); // move alice away
    assertTrue(t.canMoveHere(a));
    assertTrue(t.canMoveHere(b));
  }

  @Test
  public void test_toString() {
    test_toString_indiviual(new FloorTile("."));
    test_toString_indiviual(new RoomEntrance(0));
    
    // can not test easily as these require input when spawning
    //test_toString_indiviual(new Room());
    //test_toString_indiviual(new AccusationRoom());
  }
  
  private void test_toString_indiviual(Tile t) {
    Player p = new Player("bob", null);
    assertFalse(t.toString().equals(p.toString()));
    p.spawn(t);
    assertTrue(t.toString().equals(p.toString()));
  }
}
