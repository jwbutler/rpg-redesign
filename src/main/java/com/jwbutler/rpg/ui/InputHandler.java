package com.jwbutler.rpg.ui;

import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.geometry.Rect;
import com.jwbutler.rpg.players.Faction;
import com.jwbutler.rpg.players.HumanPlayer;
import com.jwbutler.rpg.units.Unit;
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
        var key = e.key();
        var player = controller.getState().getHumanPlayer();
        var camera = player.getCamera();

        switch (key)
        {
            case W, UP    -> camera.move(Direction.NW);
            case A, LEFT  -> camera.move(Direction.SW);
            case S, DOWN  -> camera.move(Direction.SE);
            case D, RIGHT -> camera.move(Direction.NE);
            case ENTER ->
            {
                if (e.modifiers().contains(KeyEvent.Modifier.ALT))
                {
                    window.toggleMaximized();
                }
            }
        };
    }

    public void handleMouseDown(@Nonnull MouseEvent event)
    {
        switch (event.button())
        {
            case null, default -> {}
            case LEFT -> _handleLeftDown(event.pixel());
        }
    }

    public void handleMouseUp(@Nonnull MouseEvent event)
    {
        switch (event.button())
        {
            case null, default -> {}
            case LEFT -> _handleLeftUp(event.pixel());
            case RIGHT -> _handleRightUp(event.pixel());
        }
    }

    private void _handleRightUp(@Nonnull Pixel pixel)
    {
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
            var unit = level.getUnit(coordinates);
            var selectedUnits = humanPlayer.getSelectedUnits();
            if (unit != null && unit.getPlayer().getFaction() == Faction.ENEMY)
            {
                for (var playerUnit : selectedUnits)
                {
                    playerUnit.setNextCommand(new AttackCommand(unit));
                }
            }
            else if (unit == null)
            {
                for (var playerUnit : selectedUnits)
                {
                    playerUnit.setNextCommand(new MoveCommand(coordinates));
                }
            }
        }
    }

    private void _handleLeftDown(@Nonnull Pixel pixel)
    {
        var state = controller.getState();
        var humanPlayer = state.getHumanPlayer();
        if (humanPlayer.getState() != HumanPlayer.State.GAME)
        {
            return;
        }
        humanPlayer.setSelectionStart(pixel);
    }

    private void _handleLeftUp(@Nonnull Pixel pixel)
    {
        var state = controller.getState();
        var humanPlayer = state.getHumanPlayer();
        if (humanPlayer.getState() != HumanPlayer.State.GAME)
        {
            return;
        }
        var selectionStart = humanPlayer.getSelectionStart();
        if (selectionStart == null)
        {
            // don't think this is possible
            return;
        }
        var rect = Rect.between(selectionStart, pixel);
        var camera = humanPlayer.getCamera();
        Set<Unit> selectedUnits = state.getCurrentLevel()
            .getUnits()
            .stream()
            .filter(u -> u.getPlayer() == humanPlayer)
            .filter(u -> rect.getIntersection(camera.coordinatesToPixelRect(u.getCoordinates())).area() >= 100) // arbitrary threshold
            .collect(Collectors.toSet());
        humanPlayer.setSelectionStart(null);
        humanPlayer.setSelectionEnd(null);
        humanPlayer.setSelectedUnits(selectedUnits);
    }

    public void handleMouseMove(@Nonnull MouseEvent event)
    {
        var pixel = event.pixel();
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

        if (event.heldButtons().contains(MouseEvent.Button.LEFT))
        {
            humanPlayer.setSelectionEnd(pixel);
        }
    }
}
