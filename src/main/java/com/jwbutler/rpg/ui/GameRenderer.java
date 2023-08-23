package com.jwbutler.rpg.ui;

import com.jwbutler.rpg.core.Game;
import com.jwbutler.rpg.core.Session;
import org.jspecify.annotations.NonNull;

public interface GameRenderer
{
    void render(@NonNull Game game);
    
    @NonNull
    static GameRenderer create(@NonNull GameWindow window, @NonNull Session session)
    {
        return new GameRendererImpl(window, session);
    }
}
