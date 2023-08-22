package com.jwbutler.rpg;

import java.time.Duration;

import com.jwbutler.rpg.core.Game;
import com.jwbutler.rpg.core.GameEngine;
import com.jwbutler.rpg.equipment.EquipmentFactory;
import com.jwbutler.rpg.geometry.Camera;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.LevelFactory;
import com.jwbutler.rpg.players.Faction;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.ui.GameRenderer;
import com.jwbutler.rpg.ui.GameWindow;
import com.jwbutler.rpg.ui.InputHandler;
import com.jwbutler.rpg.units.UnitFactory;

import static com.jwbutler.rpg.core.Session.SessionState;
import static com.jwbutler.rpg.core.Session.create;
import static com.jwbutler.rpg.ui.InputUtils.registerInputListeners;
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

        var humanPlayer = Player.create("human_player", Faction.PLAYER);
        var camera = new Camera(new Coordinates(5, 5));
        var session = create(humanPlayer, camera);

        var level = LevelFactory.LEVEL_ONE.get();
        game.addLevel(level);
        session.setCurrentLevel(level);
        game.addPlayer(humanPlayer);
        var renderer = GameRenderer.create(window, session);
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

        var enemyPlayer = Player.create("enemy_player", Faction.ENEMY);
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

        session.setState(SessionState.GAME);

        var engine = GameEngine.create(session, renderer, window);
        engine.render(game);

        var inputHandler = new InputHandler(session, engine);
        registerInputListeners(inputHandler, window);

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
