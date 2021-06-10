package se_ii.gruppe2.moving_maze.gamestate.turnAction;

import com.badlogic.gdx.Gdx;
import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.item.Position;
import se_ii.gruppe2.moving_maze.player.Player;
import se_ii.gruppe2.moving_maze.tile.Tile;

//After you moved and u are on an Item, you come into the Treasure state. In this State you can say, if the Item
//u are on, is your own treasure, or lie about it.
public class TreasurePickupAction implements TurnAction {

    /*
        Gets the tile the player is currently residing on and checks if it is the searched item.
        if yes --> pick it up and go on to the next card
     */
    @Override
    public void execute() {
        MovingMazeGame game = MovingMazeGame.getGameInstance();
        Gdx.app.log("turnAction/treasure", "Entered treasure-pickup state");
        Player local = game.getGameState().getPlayerByName(game.getLocalPlayer().getName());
        Position p = local.getPos();

        Tile t = game.getGameState().getBoard().getBoard()[p.getY()][p.getX()];
        Gdx.app.log("turnAction/treasure", "Checking tile on position [" + p.getY() + "] ["+p.getX() +"]");

        if(t.hasItem()) {
            Gdx.app.log("turnAction/treasure", "Found item " + t.getItem().getName() + " on tile");

            if(local.getCurrentCard().equals(t.getItem())) {
                Gdx.app.log("turnAction/treasure", "Currently searched item found! Updating card ...");
                local.nextCard();
            }
        }

        game.getGameState().completePhase();
        game.getClient().sendGameStateUpdate(game.getGameState());

    }

    @Override
    public boolean validate() {
        return true;
    }
}