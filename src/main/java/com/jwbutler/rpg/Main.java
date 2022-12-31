package com.jwbutler.rpg;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.core.GameState;
import com.jwbutler.rpg.equipment.EquipmentFactory;
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
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            System.out.println("Fatal exception occurred");
            e.printStackTrace();
            System.exit(0);
        });

        var state = GameState.create();
        var controller = GameController.create(state);
        var window = new GameWindow();
        var renderer = new GameRenderer(window);

        var level = LevelFactory.LEVEL_ONE.get();

        controller.addLevel(level);
        state.setCurrentLevel(level);
        var humanPlayer = new HumanPlayer(controller, "human_player", new Coordinates(5, 5));
        controller.addPlayer(humanPlayer);
        var enemyPlayer = new EnemyPlayer(controller, "enemy_player");
        controller.addPlayer(enemyPlayer);
        for (int i = 1; i <= 3; i++)
        {
            var playerUnit = UnitFactory.createPlayerUnit(
                controller,
                "test_unit_" + i,
                100,
                humanPlayer,
                level,
                new Coordinates(i - 1, 0)
            );
            controller.addUnit(playerUnit);
            var sword = EquipmentFactory.createNoobSword(controller, playerUnit);
            playerUnit.addEquipment(sword);
            var shield = EquipmentFactory.createShield(controller, playerUnit);
            playerUnit.addEquipment(shield);
        }

        var enemyUnit = UnitFactory.createEvilPlayerUnit(
            controller,
            "enemy_unit",
            100,
            enemyPlayer,
            level,
            new Coordinates(3, 3)
        );
        controller.addUnit(enemyUnit);
        humanPlayer.setState(HumanPlayer.State.GAME);
        renderer.render(state);

        var inputHandler = new InputHandler(controller, window);
        window.addKeyboardListener(inputHandler::handleKeyDown);
        window.addMouseDownListener(inputHandler::handleMouseDown);
        window.addMouseUpListener(inputHandler::handleMouseUp);
        window.addMouseMoveListener(inputHandler::handleMouseMove);

        while (true)
        {
            long startTime = System.nanoTime();
            for (var unit : state.getCurrentLevel().getUnits())
            {
                unit.update();
            }
            while (System.nanoTime() < startTime + (Duration.ofMillis(200).toNanos()))
            {
                renderer.render(state);
                sleepUninterruptibly(10, TimeUnit.MILLISECONDS);
            }
        }
    }
}
