package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.geometry.Pixel;
import com.jwbutler.rpg.ui.GameRenderer;
import com.jwbutler.rpg.ui.GameWindow;
import com.jwbutler.rpg.ui.InputHandler;
import org.jspecify.annotations.NonNull;

/**
 * This class handles all direct actions triggered by an input action.
 * So while {@link InputHandler} handles the mechanical translation of keyboard or mouse actions,
 * it should delegate to this class for any actual logic.
 * In some cases it's a trivial translation to an underlying {@link GameWindow} or {@link Game} method.
 */
public interface GameEngine
{
    void update(@NonNull Game game);
    void render(@NonNull Game game);
    
    void moveCamera(@NonNull Direction direction);
    void toggleScreenMaximized();
    void moveOrAttack(@NonNull Coordinates coordinates);
    void setMouseCoordinates(@NonNull Coordinates coordinates);
    void startSelectionRect(@NonNull Pixel pixel);
    void updateSelectionRect(@NonNull Pixel pixel);
    void finishSelectionRect(@NonNull Pixel pixel);
    
    @NonNull
    static GameEngine create(
        @NonNull Game game,
        @NonNull Session session,
        @NonNull GameRenderer renderer,
        @NonNull GameWindow window
    )
    {
        return new GameEngineImpl(game, session, renderer, window);
    }
}
