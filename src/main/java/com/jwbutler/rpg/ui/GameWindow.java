package com.jwbutler.rpg.ui;

import java.util.function.Consumer;
import javax.annotation.Nonnull;

import com.jwbutler.rpg.ui.swing.SwingGameWindow;

public interface GameWindow
{
    void render(@Nonnull Consumer<Graphics> consumer);
    void addKeyboardListener(@Nonnull Consumer<KeyEvent> keyListener);
    void addMouseDownListener(@Nonnull Consumer<MouseEvent> handler);
    void addMouseUpListener(@Nonnull Consumer<MouseEvent> handler);
    void addMouseMoveListener(@Nonnull Consumer<MouseEvent> handler);
    void toggleMaximized();

    @Nonnull
    static GameWindow create(@Nonnull ClientType clientType)
    {
        return switch (clientType)
        {
            case SWING -> new SwingGameWindow();
            case CANVAS -> throw new UnsupportedOperationException();
        };
    }
}
