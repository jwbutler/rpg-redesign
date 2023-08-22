package com.jwbutler.rpg.ui;

import com.jwbutler.rpg.core.GameState;
import org.jspecify.annotations.NonNull;

public interface GameRenderer
{
    void render(@NonNull GameState state);
    
    @NonNull
    static GameRenderer create(@NonNull GameWindow window)
    {
        return new GameRendererImpl(window);
    }
}
