package com.jwbutler.rpg;

import java.time.Duration;

import com.jwbutler.rpg.core.GameEngine;
import com.jwbutler.rpg.core.Game;
import com.jwbutler.rpg.equipment.EquipmentFactory;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.LevelFactory;
import com.jwbutler.rpg.players.EnemyPlayer;
import com.jwbutler.rpg.players.HumanPlayer;
import com.jwbutler.rpg.ui.GameRenderer;
import com.jwbutler.rpg.ui.GameWindow;
import com.jwbutler.rpg.ui.InputHandler;
import com.jwbutler.rpg.units.UnitFactory;

import static com.jwbutler.rpg.units.UnitUtils.addUnit;

public class Main
{
    public static final int FRAME_FREQUENCY_MILLIS = 100;
    public static final int RENDER_FREQUENCY_MILLIS = 20;

    public static void main(String[] args)
    {
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            System.out.println("Fatal exception occurred");
            e.printStackTrace();
            System.exit(0);
        });

        var game = Game.create();
        var window = new GameWindow();
        var renderer = GameRenderer.create(window);

        var level = LevelFactory.LEVEL_ONE.get();

        game.addLevel(level);
        game.setCurrentLevel(level);
        var humanPlayer = new HumanPlayer(game, "human_player", new Coordinates(5, 5));
        game.addPlayer(humanPlayer);
        for (int i = 1; i <= 10; i++)
        {
            var playerUnit = UnitFactory.createPlayerUnit(
                game,
                "test_unit_" + i,
                100,
                humanPlayer,
                level,
                new Coordinates(i - 1, 0)
            );
            addUnit(playerUnit, game);
            var sword = EquipmentFactory.createNoobSword(game, playerUnit);
            playerUnit.addEquipment(sword);
            var shield = EquipmentFactory.createShield(game, playerUnit);
            playerUnit.addEquipment(shield);
        }

        var enemyPlayer = new EnemyPlayer(game, "enemy_player");
        game.addPlayer(enemyPlayer);

        for (int i = 1; i <= 10; i++)
        {
            var enemyUnit = UnitFactory.createEvilPlayerUnit(
                game,
                "enemy_unit",
                100,
                enemyPlayer,
                level,
                new Coordinates(2 + i, 5)
            );
            addUnit(enemyUnit, game);
        }

        humanPlayer.setState(HumanPlayer.State.GAME);

        var engine = GameEngine.create(game, renderer, window);
        engine.render(game);

        var inputHandler = new InputHandler(game, engine);
        window.addKeyboardListener(inputHandler::handleKeyDown);
        window.addMouseDownListener(inputHandler::handleMouseDown);
        window.addMouseUpListener(inputHandler::handleMouseUp);
        window.addMouseMoveListener(inputHandler::handleMouseMove);

        while (true)
        {
            long startTime = System.nanoTime();
            engine.update(game);
            
            while (System.nanoTime() < startTime + (Duration.ofMillis(FRAME_FREQUENCY_MILLIS).toNanos()))
            {
                engine.render(game);
                try
                {
                    Thread.sleep(RENDER_FREQUENCY_MILLIS);
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
