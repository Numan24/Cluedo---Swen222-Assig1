package game;

import java.io.*;
import java.util.*;

/** 
 * A simple container for the set of Tiles
 * @author dom
 *
 */
public class Board {


  List<List<Tile>> board = new ArrayList<List<Tile>>();
  
  Map<Integer, Room> rooms          = new HashMap<Integer, Room>();
  Map<Integer, Tile> roomEntrances  = new HashMap<Integer, Tile>();
  Map<Integer, Tile> playerSpawns   = new HashMap<Integer, Tile>();
  int roomCount;
  int entranceCount;
  int spawnCount;
  
  public Board() {

    try {
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
              case '?':
                newTile = new FloorTile(".");
                break;
              case 'E':
                newTile = new FloorTile("e");
                roomEntrances.put(entranceCount, newTile);
                ++entranceCount;
                break;
              case 'P':
                newTile = new FloorTile(".");
                playerSpawns.put(spawnCount, newTile);
                ++roomCount;
                break;
              case 'R':
                rooms.put(roomCount, new Room());
                newTile = rooms.get(roomCount);
                ++roomCount;
                break;
              default:
                System.out.println("Unknown Tile: " + token.charAt(0));
            }
            boardRow.add(newTile);
          }
        }

        System.out.println();
        board.add(boardRow);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  

  @Override
  public String toString() {
    String ret = "";
    for (List<Tile> row : board) {
      for (Tile t : row) {
        if (t == null)
          for (int i=0; i<Tile.reprSize; ++i)
            ret += " "; // YAY, scew java code
        else
          ret += t.toString();
      }
      ret += '\n';
    }
    return ret;
  }
}
