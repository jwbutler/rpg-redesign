package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.ui.GameRenderer;
import com.jwbutler.rpg.ui.GameWindow;
import com.jwbutler.rpg.ui.InputHandler_Warpath;
import org.jspecify.annotations.NonNull;

/**
 * This class handles all direct actions triggered by an input action.
 * So while {@link InputHandler_Warpath} handles the mechanical translation of keyboard or mouse actions,
 * it should delegate to this class for any actual logic.
 * In some cases it's a trivial translation to an underlying {@link GameWindow} or {@link Game} method.
 */
public interface GameEngine_Shining
{
    void update(@NonNull Game game);
    void render(@NonNull Game game);

    void moveCamera(@NonNull Direction direction);
    void toggleScreenMaximized();
    void setMouseCoordinates(@NonNull Coordinates coordinates);
    void loadLevel(@NonNull Level level);
    
    @NonNull
    static GameEngine_Shining create(
        @NonNull Session_Shining session,
        @NonNull GameRenderer renderer,
        @NonNull GameWindow window
    )
    {
        return new GameEngineImpl_Shining(session, renderer, window);
    }
}
