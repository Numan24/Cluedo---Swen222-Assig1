package game;

import java.io.*;
import java.util.*;

/** 
 * Keeps information about what the players can interact with
 * @author dom
 *
 */
public class Board {
  
  /* =========== Static variables =========== */

  public static final Map<Integer, String> roomNames = new HashMap<Integer, String>();
  static {
    roomNames.put(0, "spa");
    roomNames.put(1, "theater");
    roomNames.put(2, "living room");
    roomNames.put(3, "observatory");
    roomNames.put(4, "patio");
    roomNames.put(5, "swimming pool"); // accusation room
    roomNames.put(6, "hall");
    roomNames.put(7, "kitchen");
    roomNames.put(8, "dining room");
    roomNames.put(9, "guest house");
  }
  
  public static final List<String> weapons = new ArrayList<String>();
  static {
    weapons.add("rope");
    weapons.add("candlestick");
    weapons.add("knife");
    weapons.add("pistol");
    weapons.add("baseball bat");
    weapons.add("dumbell");
    weapons.add("trophy");
    weapons.add("poison");
    weapons.add("axe");
    Collections.shuffle(weapons); // this will randomise their spawn locations
  }

  
  /* =========== Fields =========== */

  
  List<List<Tile>> board = new ArrayList<List<Tile>>();
  
  Map<Integer, Room> rooms          = new HashMap<Integer, Room>();
  Map<Integer, Tile> playerSpawns   = new HashMap<Integer, Tile>();
  Map<Integer, RoomEntrance> roomEntrances = new HashMap<Integer, RoomEntrance>();
  int entranceCount;
  int spawnCount;
  
  
  /* =========== Constructor =========== */

  
  public Board() {
    try {
      // parse the board
      Scanner lineScan = new Scanner(new File("board.tab"));
      while (lineScan.hasNextLine()) {
        List<Tile> boardRow = new ArrayList<Tile>();
        for (String token : lineScan.nextLine().split("\t")) {
          if (token.isEmpty())
            boardRow.add(null);
          else {
            Tile newTile = null;
            // construct the right Tile for the leading character
            // add it to a map if it is a special tile
            switch (token.charAt(0)) {
              case '.':
              case '?': // floor
                newTile = new FloorTile(".");
                break;
              case 'E': // room Entrance
                roomEntrances.put(entranceCount, new RoomEntrance(
                  Integer.parseInt(token.substring(1)) // the roomID that we're entering
                ));
                newTile = roomEntrances.get(entranceCount);
                ++entranceCount;
                break;
              case 'P': // Player Spawn
                newTile = new FloorTile(".");
                playerSpawns.put(spawnCount, newTile);
                ++spawnCount;
                break;
              case 'R': // Room
                int roomID = Integer.parseInt(token.substring(1));
                Room newRoom;
                if (roomID==5) newRoom = new AccusationRoom();
                else           newRoom = new Room();
                rooms.put(roomID, newRoom);
                newTile = newRoom;
                break;
              default:
                System.out.println("Unknown Tile: " + token.charAt(0));
            }
            boardRow.add(newTile);
          }
        }
        board.add(boardRow);
      }
    }
    catch (IOException e) { e.printStackTrace(); }
    
    // add connections between rooms/entrances
    for (RoomEntrance e : roomEntrances.values()) {
      Room r = rooms.get(e.getRoomID());
      e.addConnection("Enter the " + roomNames.get(e.getRoomID()), r);
      r.addConnection("Exit through door " + r.connections().size(), e);
    }
    
    // connections between corner rooms.
    // hard-coded but at least done in a nice way
    buildTunnel(0, 9);
    buildTunnel(9, 0);
    buildTunnel(3, 7);
    buildTunnel(7, 3);

    // connections between adjacent tiles
    for (int i=0; i<board.size(); ++i) {
      List<Tile> row = board.get(i);
      for (int j=0; j<row.size(); ++j) {
        addConnection(i, j, i-1, j+0, "Move up"   );
        addConnection(i, j, i+0, j+1, "Move right");
        addConnection(i, j, i+1, j+0, "Move down" );
        addConnection(i, j, i+0, j-1, "Move left" );
      }
    }
  }
  
  
  /* =========== Constructor helpers =========== */
  
  private void addConnection(int row1, int col1, int row2, int col2, String description) {
    if (!isInBounds(row1, col1)) return;
    if (!isInBounds(row2, col2)) return;
    Tile a = board.get(row1).get(col1);
    Tile b = board.get(row2).get(col2);
    if (a==null || b==null) return;
    a.addConnection(description, b);
  }
  
  /** A simple check to see whether a point is on our board */
  public boolean isInBounds(int row, int col) {
    if (row < 0 || col < 0)           return false;
    if (row >= board.size())          return false;
    if (col >= board.get(row).size()) return false;
    return true;
  }
  
  private void buildTunnel(int roomID_from, int roomID_to) {
    rooms.get(roomID_from).addConnection(
      "Use tunnel to the " + roomNames.get(roomID_to), 
      rooms.get(roomID_to)
    );
  }

  /* =========== Methods =========== */

  @Override
  public String toString() {
    String ret = "";
    for (List<Tile> row : board) {
      for (Tile t : row) {
        if (t == null)
          for (int i=0; i<Tile.reprSize; ++i)
            ret += " "; // YAY, scew code >.>
        else
          ret += t.representation();
      }
      ret += '\n';
    }
    return ret;
  }
}
