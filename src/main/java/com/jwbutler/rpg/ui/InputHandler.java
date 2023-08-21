package com.jwbutler.rpg.ui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Set;
import java.util.stream.Collectors;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.geometry.Rect;
import com.jwbutler.rpg.players.Faction;
import com.jwbutler.rpg.players.HumanPlayer;
import com.jwbutler.rpg.units.Unit;
import com.jwbutler.rpg.units.commands.AttackCommand;
import com.jwbutler.rpg.units.commands.MoveCommand;
import org.jspecify.annotations.NonNull;

import static com.jwbutler.rpg.ui.InputUtils.isLeftButton;
import static com.jwbutler.rpg.ui.InputUtils.isLeftButtonDown;
import static com.jwbutler.rpg.ui.InputUtils.isRightButton;

public final class InputHandler
{
    @NonNull
    private final GameController controller;
    @NonNull
    private final GameWindow window;

    public InputHandler(@NonNull GameController controller, @NonNull GameWindow window)
    {
        this.controller = controller;
        this.window = window;
    }

    public void handleKeyDown(@NonNull KeyEvent e)
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

    public void handleMouseDown(@NonNull MouseEvent event)
    {
        var pixel = new Pixel(event.getX(), event.getY());
        if (isRightButton(event))
        {
            // _handleRightClick(pixel);
        }
        else if (isLeftButton(event))
        {
            _handleLeftDown(pixel);
        }
    }

    public void handleMouseUp(@NonNull MouseEvent event)
    {
        var pixel = new Pixel(event.getX(), event.getY());
        if (isRightButton(event))
        {
            _handleRightUp(pixel);
        }
        else if (isLeftButton(event))
        {
            _handleLeftUp(pixel);
        }
    }

    private void _handleRightUp(@NonNull Pixel pixel)
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

    private void _handleLeftDown(@NonNull Pixel pixel)
    {
        var state = controller.getState();
        var humanPlayer = state.getHumanPlayer();
        if (humanPlayer.getState() != HumanPlayer.State.GAME)
        {
            return;
        }
        humanPlayer.setSelectionStart(pixel);
    }

    private void _handleLeftUp(@NonNull Pixel pixel)
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

    public void handleMouseMove(@NonNull MouseEvent event)
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

        if (isLeftButtonDown(event))
        {
            humanPlayer.setSelectionEnd(pixel);
        }
    }
}
