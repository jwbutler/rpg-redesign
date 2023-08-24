package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Camera;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;
import com.jwbutler.rpg.units.commands.Command;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * With an eye toward multiplayer support - this is a container for player-specific data
 */
public interface Session
{
    enum SessionState
    {
        TITLE_SCREEN,
        GAME
    }

    @NonNull
    Player getPlayer();

    @NonNull Level getCurrentLevel();

    void setCurrentLevel(@NonNull Level currentLevel);
    
    @NonNull
    SessionState getState();
    
    void setState(@NonNull SessionState state);
    
    @NonNull
    Camera getCamera();

    @Nullable
    Coordinates getMouseCoordinates();

    void setMouseCoordinates(@Nullable Coordinates mouseCoordinates);

    /**
     * null if state != GAME, I guess
     */
    @Nullable
    Unit getActiveUnit();
    
    void selectNextUnit();

    void queueCommand(@NonNull Unit unit, @NonNull Command command);

    @Nullable
    Command getQueuedCommand(@NonNull Unit unit);

    void clearQueuedCommand(@NonNull Unit unit);
    
    @NonNull
    static Session create(@NonNull Player player, @NonNull Camera camera)
    {
        return new SessionImpl(player, camera);
    }
}
