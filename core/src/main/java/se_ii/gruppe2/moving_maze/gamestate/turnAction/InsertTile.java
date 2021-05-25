package se_ii.gruppe2.moving_maze.gamestate.turnAction;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import se_ii.gruppe2.moving_maze.MovingMazeGame;
import se_ii.gruppe2.moving_maze.gameboard.GameBoard;
import se_ii.gruppe2.moving_maze.item.Position;
import se_ii.gruppe2.moving_maze.tile.Tile;

public class InsertTile implements TurnAction {

    MovingMazeGame game = MovingMazeGame.getGameInstance();
    Vector2 insertPosition;
    Vector2 lastInsertPosition;

    public InsertTile() {}

    public InsertTile(Vector2 insertPosition){
        this.insertPosition = insertPosition;
        this.lastInsertPosition = game.getGameState().getLastInsertPosition();
    }


    @Override
    public void execute() {

        GameBoard gameBoard = game.getGameState().getBoard();
        GameBoard newGameBoard = new GameBoard();
        newGameBoard.setExtraTile(gameBoard.getExtraTile());
        newGameBoard.setBoard(gameBoard.getBoard());

        int boardLength = game.getGameState().getBoard().getBoard().length;

        if (insertPosition.x == 0){
            Tile newExtraTile = newGameBoard.getBoard()[(int)insertPosition.y][boardLength-1];
            for (int i = boardLength-1; i > 0; i--){
                newGameBoard.getBoard()[(int)insertPosition.y][i] = newGameBoard.getBoard()[(int)insertPosition.y][i-1];
            }
            newGameBoard.getBoard()[(int)insertPosition.y][0] = newGameBoard.getExtraTile();
            newGameBoard.setExtraTile(newExtraTile);
            lastInsertPosition = new Vector2(6, insertPosition.y);
        }
        else if (insertPosition.x == 6){
            Tile newExtraTile = newGameBoard.getBoard()[(int)insertPosition.y][0];
            for (int i = 0; i < boardLength-1; i++){
                newGameBoard.getBoard()[(int)insertPosition.y][i] = newGameBoard.getBoard()[(int)insertPosition.y][i+1];
            }
            newGameBoard.getBoard()[(int)insertPosition.y][boardLength-1] = newGameBoard.getExtraTile();
            newGameBoard.setExtraTile(newExtraTile);
            lastInsertPosition = new Vector2(0, insertPosition.y);
        }
        else if (insertPosition.y == 0){
            Tile newExtraTile = newGameBoard.getBoard()[boardLength-1][(int)insertPosition.x];
            for (int i = boardLength-1; i > 0; i--){
                newGameBoard.getBoard()[i][(int)insertPosition.x] = newGameBoard.getBoard()[i-1][(int)insertPosition.x];
            }
            newGameBoard.getBoard()[0][(int)insertPosition.x] = newGameBoard.getExtraTile();
            newGameBoard.setExtraTile(newExtraTile);
            lastInsertPosition = new Vector2(insertPosition.x, 6);
        }
        else if (insertPosition.y == 6){
            Tile newExtraTile = newGameBoard.getBoard()[0][(int)insertPosition.x];
            for (int i = 0; i < boardLength-1; i++){
                newGameBoard.getBoard()[i][(int)insertPosition.x] = newGameBoard.getBoard()[i+1][(int)insertPosition.x];
            }
            newGameBoard.getBoard()[boardLength-1][(int)insertPosition.x] = newGameBoard.getExtraTile();
            newGameBoard.setExtraTile(newExtraTile);
            lastInsertPosition = new Vector2(insertPosition.x, 0);
        }

        game.getGameState().setLastInsertPosition(lastInsertPosition);
        game.getGameState().setBoard(newGameBoard);
        game.getClient().sendGameStateUpdate(game.getGameState());

    }

    @Override
    public boolean validate() {

        if (lastInsertPosition != null) {
            if (insertPosition.x == lastInsertPosition.x && insertPosition.y == lastInsertPosition.y) {
                return false;
            }
        }
        if ((insertPosition.x == 0.0f || insertPosition.x == 6.0f) && insertPosition.y % 2 != 0.0f){
            return true;
        } else if ((insertPosition.y == 0.0f || insertPosition.y == 6.0f) && insertPosition.x % 2 != 0.0f){
            return true;
        }
        else {
            return false;
        }
    }
}
