package com.jwbutler.rpg;

import java.util.concurrent.TimeUnit;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.core.GameState;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.LevelFactory;
import com.jwbutler.rpg.players.EnemyPlayer;
import com.jwbutler.rpg.players.HumanPlayer;
import com.jwbutler.rpg.ui.GameRenderer;
import com.jwbutler.rpg.ui.GameWindow;
import com.jwbutler.rpg.ui.InputHandler;
import com.jwbutler.rpg.units.UnitFactory;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;

public class Main
{
    public static void main(String[] args)
    {
        var state = GameState.create();
        var controller = GameController.create(state);
        GameController.setInstance(controller);
        var window = new GameWindow();
        var renderer = new GameRenderer(window);
        var inputHandler = new InputHandler(controller, window);
        window.addKeyboardListener(inputHandler::handleKeyPress);

        var level = LevelFactory.LEVEL_ONE.get();

        controller.addLevel(level);
        state.setCurrentLevel(level);
        var humanPlayer = new HumanPlayer("human_player", new Coordinates(5, 5));
        controller.addPlayer(humanPlayer);
        var enemyPlayer = new EnemyPlayer("enemy_player");
        controller.addPlayer(enemyPlayer);
        var playerUnit = UnitFactory.createPlayerUnit(
            "test_unit",
            100,
            humanPlayer,
            level,
            Coordinates.zero()
        );
        controller.addUnit(playerUnit);
        var enemyUnit = UnitFactory.createPlayerUnit(
            "enemy_unit",
            100,
            enemyPlayer,
            level,
            new Coordinates(3, 3)
        );
        controller.addUnit(enemyUnit);
        renderer.render(state);

        while (true)
        {
            var runnable = inputHandler.poll();
            if (runnable != null)
            {
                runnable.run();
            }
            for (var unit : state.getCurrentLevel().getUnits())
            {
                unit.update();
            }
            renderer.render(state);
            sleepUninterruptibly(200, TimeUnit.MILLISECONDS);
        }
    }
}
