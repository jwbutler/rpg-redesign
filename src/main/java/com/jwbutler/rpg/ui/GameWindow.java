package com.jwbutler.rpg.ui;

import java.awt.Graphics2D;
import java.util.function.Consumer;
import javax.annotation.Nonnull;

public interface GameWindow
{
    void render(@Nonnull Consumer<Graphics2D> consumer);
    void addKeyboardListener(@Nonnull Consumer<KeyEvent> keyListener);
    void addMouseDownListener(@Nonnull Consumer<MouseEvent> handler);
    void addMouseUpListener(@Nonnull Consumer<MouseEvent> handler);
    void addMouseMoveListener(@Nonnull Consumer<MouseEvent> handler);
    void toggleMaximized();
}
