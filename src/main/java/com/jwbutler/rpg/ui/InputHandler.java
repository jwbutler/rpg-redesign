package com.jwbutler.rpg.ui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.core.GameEngine;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.players.HumanPlayer;
import org.jspecify.annotations.NonNull;

import static com.jwbutler.rpg.core.GameStateUtils.getHumanPlayer;
import static com.jwbutler.rpg.ui.InputUtils.isLeftButton;
import static com.jwbutler.rpg.ui.InputUtils.isLeftButtonDown;
import static com.jwbutler.rpg.ui.InputUtils.isRightButton;

public final class InputHandler
{
    @NonNull
    private final GameController controller;
    @NonNull
    private final GameEngine engine;

    public InputHandler(
        @NonNull GameController controller,
        @NonNull GameEngine engine
    )
    {
        this.controller = controller;
        this.engine = engine;
    }

    public void handleKeyDown(@NonNull KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        
        switch (keyCode)
        {
            case KeyEvent.VK_W, KeyEvent.VK_UP    -> engine.moveCamera(Direction.NW);
            case KeyEvent.VK_A, KeyEvent.VK_LEFT  -> engine.moveCamera(Direction.SW);
            case KeyEvent.VK_S, KeyEvent.VK_DOWN  -> engine.moveCamera(Direction.SE);
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> engine.moveCamera(Direction.NE);
            case KeyEvent.VK_ENTER ->
            {
                if ((e.getModifiersEx() & KeyEvent.ALT_DOWN_MASK) > 0)
                {
                    engine.toggleScreenMaximized();
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
        var humanPlayer = getHumanPlayer(state);
        if (humanPlayer.getState() != HumanPlayer.State.GAME)
        {
            return;
        }
        var coordinates = humanPlayer.getCamera().pixelToCoordinates(pixel);

        engine.moveOrAttack(coordinates);
    }

    private void _handleLeftDown(@NonNull Pixel pixel)
    {
        var state = controller.getState();
        var humanPlayer = getHumanPlayer(state);
        if (humanPlayer.getState() != HumanPlayer.State.GAME)
        {
            return;
        }
        engine.startSelectionRect(pixel);
    }

    private void _handleLeftUp(@NonNull Pixel pixel)
    {
        var state = controller.getState();
        var humanPlayer = getHumanPlayer(state);
        if (humanPlayer.getState() != HumanPlayer.State.GAME)
        {
            return;
        }
        engine.finishSelectionRect(pixel);
    }

    public void handleMouseMove(@NonNull MouseEvent event)
    {
        var pixel = new Pixel(event.getX(), event.getY());
        var state = controller.getState();
        var humanPlayer = getHumanPlayer(state);
        if (humanPlayer.getState() != HumanPlayer.State.GAME)
        {
            return;
        }

        var level = state.getCurrentLevel();
        var coordinates = humanPlayer.getCamera().pixelToCoordinates(pixel);

        if (level.containsCoordinates(coordinates))
        {
            engine.setMouseCoordinates(coordinates);
        }

        if (isLeftButtonDown(event))
        {
            humanPlayer.setSelectionEnd(pixel);
        }
    }
}
