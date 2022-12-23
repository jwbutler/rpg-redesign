package com.jwbutler.rpg.ui;

import java.awt.event.KeyEvent;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.core.GameEngine;
import com.jwbutler.rpg.util.Blocking;
import com.jwbutler.rpg.util.SingletonBlockingQueue;

public final class InputHandler
{
    @Nonnull
    private final GameEngine engine;
    @Nonnull
    private final SingletonBlockingQueue<Runnable> queue;

    public InputHandler(@Nonnull GameEngine engine)
    {
        this.engine = engine;
        this.queue = new SingletonBlockingQueue<>();
    }

    public void handleKeyPress(@Nonnull KeyEvent e)
    {
        var unit = engine.getState().getPlayerUnit();
        int keyCode = e.getKeyCode();

        @CheckForNull Runnable runnable = switch (keyCode)
        {
            case KeyEvent.VK_W, KeyEvent.VK_UP    -> () -> engine.moveUnit(unit, unit.getLevel(), unit.getCoordinates().plus(0, -1));
            case KeyEvent.VK_A, KeyEvent.VK_LEFT  -> () -> engine.moveUnit(unit, unit.getLevel(), unit.getCoordinates().plus(-1, 0));
            case KeyEvent.VK_S, KeyEvent.VK_DOWN  -> () -> engine.moveUnit(unit, unit.getLevel(), unit.getCoordinates().plus(0, 1));
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> () -> engine.moveUnit(unit, unit.getLevel(), unit.getCoordinates().plus(1, 0));
            default -> null;
        };

        if (runnable != null)
        {
            queue.offer(runnable);
        }
    }

    @Nonnull
    @Blocking
    public Runnable poll()
    {
        return queue.take();
    }
}
