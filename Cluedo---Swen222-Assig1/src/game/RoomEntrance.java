package game;

public class RoomEntrance extends FloorTile {
  private int roomID;
  public int getRoomID() { return roomID; }

  public RoomEntrance(int roomID) {
    super("e");
    this.roomID = roomID;
  }
}
