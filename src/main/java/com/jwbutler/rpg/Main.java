package com.jwbutler.rpg;

import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.Uninterruptibles;
import com.jwbutler.rpg.core.GameEngine;
import com.jwbutler.rpg.core.GameState;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Faction;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.ui.GameRenderer;
import com.jwbutler.rpg.ui.GameWindow;
import com.jwbutler.rpg.ui.InputHandler;
import com.jwbutler.rpg.units.Unit;

public class Main
{
    public static void main(String[] args)
    {
        var state = GameState.create();
        var engine = GameEngine.create(state);
        var window = new GameWindow();
        var renderer = new GameRenderer(window);
        var inputHandler = new InputHandler(engine);
        window.addKeyboardListener(inputHandler::handleKeyPress);

        var level = Level.create("first_level");
        engine.addLevel(level);
        state.setCurrentLevel(level);
        var player = Player.create("human_player", Faction.PLAYER);
        engine.addPlayer(player);
        var unit = Unit.create(
            "test_unit",
            100,
            player,
            level,
            Coordinates.zero()
        );
        engine.addUnit(unit);
        renderer.render(state);

        while (true)
        {
            var runnable = inputHandler.poll();
            runnable.run();
            renderer.render(state);
        }
    }
}
