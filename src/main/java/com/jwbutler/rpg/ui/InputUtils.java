package com.jwbutler.rpg.ui;

import org.jspecify.annotations.NonNull;

public final class InputUtils
{
    private InputUtils() {}
    
    public static void registerInputListeners(@NonNull InputHandler inputHandler, @NonNull GameWindow window)
    {
        window.addKeyboardListener(inputHandler::handleKeyDown);
        window.addMouseDownListener(inputHandler::handleMouseDown);
        window.addMouseUpListener(inputHandler::handleMouseUp);
        window.addMouseMoveListener(inputHandler::handleMouseMove);
    }
}
