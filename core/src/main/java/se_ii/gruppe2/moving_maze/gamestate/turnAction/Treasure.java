package se_ii.gruppe2.moving_maze.gamestate.turnAction;

//After you moved and u are on an Item, you come into the Treasure state. In this State you can say, if the Item
//u are on, is your own treasure, or lie about it.
public class Treasure implements TurnAction {
    @Override
    public void execute() {

    }

    @Override
    public boolean validate() {
        return false;
    }
}