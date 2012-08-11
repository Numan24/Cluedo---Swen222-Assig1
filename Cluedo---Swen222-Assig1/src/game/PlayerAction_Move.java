package game;

public class PlayerAction_Move implements PlayerAction {

  Player player;
  Tile target;
  String description;
  
  public PlayerAction_Move(Player player, Tile target, String description) {
    this.player = player;
    this.target = target;
    this.description = description;
  }
  
  @Override
  public void execute() {
    if (! player.moveToTile(target) )
      throw new PlayerAction.InvalidAction();
  }
  
  public String toString() {
    return description;
  }
}
