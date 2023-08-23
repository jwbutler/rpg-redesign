package com.jwbutler.rpg.ui;

import com.jwbutler.rpg.core.Game;
import com.jwbutler.rpg.core.Session_Shining;
import com.jwbutler.rpg.core.Session_Warpath;
import org.jspecify.annotations.NonNull;

public interface GameRenderer
{
    void render(@NonNull Game game);
    
    @NonNull
    static GameRenderer create(@NonNull GameWindow window, @NonNull Session_Shining session)
    {
        return new GameRendererImpl(window, session);
    }
}
