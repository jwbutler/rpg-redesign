package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.geometry.Direction;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.ui.GameRenderer;
import com.jwbutler.rpg.ui.GameWindow;
import com.jwbutler.rpg.ui.InputHandler;
import com.jwbutler.rpg.units.Unit;
import com.jwbutler.rpg.units.commands.Command;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

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
    
    void setMouseCoordinates(@NonNull Coordinates coordinates);
    
    void loadLevel(@NonNull Level level);
    
    void queueCommand(@NonNull Unit unit, @NonNull Command command);
    
    @Nullable
    Command getQueuedCommand(@NonNull Unit unit);

    void clearQueuedCommand(@NonNull Unit unit);
    
    @NonNull
    static GameEngine create(
        @NonNull Session session,
        @NonNull GameRenderer renderer,
        @NonNull GameWindow window
    )
    {
        return new GameEngineImpl(session, renderer, window);
    }
}
