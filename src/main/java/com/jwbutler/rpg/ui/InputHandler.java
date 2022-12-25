package com.jwbutler.rpg.ui;

import java.awt.event.KeyEvent;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameController;
import com.jwbutler.rpg.geometry.Coordinates;
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

    public void handleKeyPress(@Nonnull KeyEvent e)
    {
        var unit = controller.getState().getPlayerUnit();
        int keyCode = e.getKeyCode();

        @CheckForNull Runnable runnable = switch (keyCode)
        {
            case KeyEvent.VK_W, KeyEvent.VK_UP    -> _tryMove(unit.getCoordinates().plus(0, -1));
            case KeyEvent.VK_A, KeyEvent.VK_LEFT  -> _tryMove(unit.getCoordinates().plus(-1, 0));
            case KeyEvent.VK_S, KeyEvent.VK_DOWN  -> _tryMove(unit.getCoordinates().plus(0, 1));
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> _tryMove(unit.getCoordinates().plus(1, 0));
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

    @CheckForNull
    private Runnable _tryMove(@Nonnull Coordinates coordinates)
    {
        var level = controller.getState().getCurrentLevel();
        if (level.containsCoordinates(coordinates) && level.getUnit(coordinates) == null)
        {
            var playerUnit = controller.getState().getPlayerUnit();
            return () -> playerUnit.setNextCommand(new MoveCommand(controller, coordinates));
            // return () -> engine.moveUnit(playerUnit, level, coordinates);
        }
        return null;
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
