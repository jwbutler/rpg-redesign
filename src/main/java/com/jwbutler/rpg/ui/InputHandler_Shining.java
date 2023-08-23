package com.jwbutler.rpg.ui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jwbutler.rpg.core.GameEngine_Shining;
import com.jwbutler.rpg.core.Session_Shining;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.geometry.Pixel;
import org.jspecify.annotations.NonNull;

import static com.jwbutler.rpg.ui.SwingUtils.isLeftButton;
import static com.jwbutler.rpg.ui.SwingUtils.isRightButton;

public final class InputHandler_Shining implements InputHandler
{
    @NonNull
    private final Session_Shining session;
    @NonNull
    private final GameEngine_Shining engine;
    
    public InputHandler_Shining(
        @NonNull Session_Shining session,
        @NonNull GameEngine_Shining engine
    )
    {
        this.session = session;
        this.engine = engine;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public void handleMouseMove(@NonNull MouseEvent event)
    {
        var pixel = new Pixel(event.getX(), event.getY());
        if (session.getState() != Session_Shining.SessionState.GAME)
        {
            return;
        }

        var level = session.getCurrentLevel();
        var coordinates = session.getCamera().pixelToCoordinates(pixel);

        if (level.containsCoordinates(coordinates))
        {
            engine.setMouseCoordinates(coordinates);
        }
    }

    private void _handleRightUp(@NonNull Pixel pixel)
    {
        if (session.getState() != Session_Shining.SessionState.GAME)
        {
            return;
        }
        var coordinates = session.getCamera().pixelToCoordinates(pixel);
    }

    private void _handleLeftDown(@NonNull Pixel pixel)
    {
        if (session.getState() != Session_Shining.SessionState.GAME)
        {
            return;
        }
    }

    private void _handleLeftUp(@NonNull Pixel pixel)
    {
        if (session.getState() != Session_Shining.SessionState.GAME)
        {
            return;
        }
    }
}
