package com.jwbutler.rpg.ui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.players.Faction;
import com.jwbutler.rpg.players.HumanPlayer;
import com.jwbutler.rpg.units.commands.AttackCommand;
import com.jwbutler.rpg.units.commands.MoveCommand;

public final class InputHandler
{
    @Nonnull
    private final GameController controller;
    @Nonnull
    private final GameWindow window;

    public InputHandler(@Nonnull GameController controller, @Nonnull GameWindow window)
    {
        this.controller = controller;
        this.window = window;
    }

    public void handleKeyDown(@Nonnull KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        var player = controller.getState().getHumanPlayer();
        var camera = player.getCamera();

        switch (keyCode)
        {
            case KeyEvent.VK_W, KeyEvent.VK_UP    -> camera.move(Direction.NW);
            case KeyEvent.VK_A, KeyEvent.VK_LEFT  -> camera.move(Direction.SW);
            case KeyEvent.VK_S, KeyEvent.VK_DOWN  -> camera.move(Direction.SE);
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> camera.move(Direction.NE);
            case KeyEvent.VK_ENTER ->
            {
                if ((e.getModifiersEx() & KeyEvent.ALT_DOWN_MASK) > 0)
                {
                    window.toggleMaximized();
                }
            }
        };
    }

    public void handleMouseDown(@Nonnull MouseEvent event)
    {
        var state = controller.getState();
        var humanPlayer = state.getHumanPlayer();
        if (humanPlayer.getState() != HumanPlayer.State.GAME)
        {
            return;
        }

        var pixel = new Pixel(event.getX(), event.getY());
        var playerUnit = state.getPlayerUnit();
        var level = state.getCurrentLevel();
        var coordinates = humanPlayer.getCamera().pixelToCoordinates(pixel);

        if (level.containsCoordinates(coordinates))
        {
            var unit = level.getUnit(coordinates);
            if (unit != null && unit.getPlayer().getFaction() == Faction.ENEMY)
            {
                playerUnit.setNextCommand(new AttackCommand(controller, unit));
            }
            else if (unit == null)
            {
                playerUnit.setNextCommand(new MoveCommand(controller, coordinates));
            }
        }
    }

    public void handleMouseMove(@Nonnull MouseEvent event)
    {
        var pixel = new Pixel(event.getX(), event.getY());
        var state = controller.getState();
        var humanPlayer = state.getHumanPlayer();
        if (humanPlayer.getState() != HumanPlayer.State.GAME)
        {
            return;
        }

        var level = state.getCurrentLevel();
        var coordinates = humanPlayer.getCamera().pixelToCoordinates(pixel);

        if (level.containsCoordinates(coordinates))
        {
            humanPlayer.setMouseCoordinates(coordinates);
        }
    }
}
