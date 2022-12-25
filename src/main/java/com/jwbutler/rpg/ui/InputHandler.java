package com.jwbutler.rpg.ui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.units.commands.MoveCommand;
import com.jwbutler.rpg.util.Blocking;
import com.jwbutler.rpg.util.SingletonBlockingQueue;

public final class InputHandler
{
    @Nonnull
    private final GameController controller;
    @Nonnull
    private final GameWindow window;
    @Nonnull
    private final SingletonBlockingQueue<Runnable> queue;

    public InputHandler(@Nonnull GameController controller, @Nonnull GameWindow window)
    {
        this.controller = controller;
        this.window = window;
        this.queue = new SingletonBlockingQueue<>();
    }

    public void handleKeyDown(@Nonnull KeyEvent e)
    {
        int keyCode = e.getKeyCode();

        @CheckForNull Runnable runnable = switch (keyCode)
        {
            case KeyEvent.VK_W, KeyEvent.VK_UP    -> _tryMove(Direction.N);
            case KeyEvent.VK_A, KeyEvent.VK_LEFT  -> _tryMove(Direction.W);
            case KeyEvent.VK_S, KeyEvent.VK_DOWN  -> _tryMove(Direction.S);
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> _tryMove(Direction.E);
            case KeyEvent.VK_ENTER ->
            {
                if ((e.getModifiersEx() & KeyEvent.ALT_DOWN_MASK) > 0)
                {
                    window.toggleMaximized();
                }
                yield null;
            }
            default -> null;
        };

        if (runnable != null)
        {
            queue.offer(runnable);
        }
    }

    public void handleMouseDown(@Nonnull MouseEvent event)
    {
        var pixel = new Pixel(event.getX(), event.getY());
        var state = controller.getState();
        var humanPlayer = state.getHumanPlayer();
        var playerUnit = state.getPlayerUnit();
        var level = state.getCurrentLevel();
        var cameraCoordinates = humanPlayer.getCameraCoordinates();
        var coordinates = pixel.toCoordinates(cameraCoordinates);

        if (level.containsCoordinates(coordinates) && level.getUnit(coordinates) == null)
        {
            playerUnit.setNextCommand(new MoveCommand(controller, coordinates));
        }
    }

    @Nonnull
    private Runnable _tryMove(@Nonnull Direction direction)
    {
        return () ->
        {
            var playerUnit = controller.getState().getPlayerUnit();
            var level = controller.getState().getCurrentLevel();
            var coordinates = playerUnit.getCoordinates().plus(direction);
            if (level.containsCoordinates(coordinates) && level.getUnit(coordinates) == null)
            {
                playerUnit.setNextCommand(new MoveCommand(controller, coordinates));
            }
        };
    }

    @Nonnull
    @Blocking
    public Runnable pollBlocking()
    {
        return queue.take();
    }

    @CheckForNull
    @Blocking
    public Runnable poll()
    {
        return queue.poll();
    }
}
