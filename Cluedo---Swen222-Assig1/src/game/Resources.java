package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {

  public static BufferedImage board;

  
  public static void load() {
    try {
      board = ImageIO.read(new File("board_adjusted.png"));

    } catch (IOException e) { throw new Error(e); }
  }
}
