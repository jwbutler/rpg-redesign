package com.jwbutler.rpg.ui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jwbutler.rpg.core.GameEngine;
import com.jwbutler.rpg.core.Session;
import org.jspecify.annotations.NonNull;

public interface InputHandler
{
    void handleKeyDown(@NonNull KeyEvent e);

    void handleMouseDown(@NonNull MouseEvent event);

    void handleMouseUp(@NonNull MouseEvent event);

    void handleMouseMove(@NonNull MouseEvent event);

    @NonNull
    static InputHandler create(@NonNull Session session, @NonNull GameEngine engine)
    {
        return new InputHandlerImpl(session, engine);
    }
}
