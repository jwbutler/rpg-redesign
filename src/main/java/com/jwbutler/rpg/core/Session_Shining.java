package com.jwbutler.rpg.core;

import com.jwbutler.rpg.geometry.Camera;
import com.jwbutler.rpg.geometry.Coordinates;
import com.jwbutler.rpg.levels.Level;
import com.jwbutler.rpg.players.Player;
import com.jwbutler.rpg.units.Unit;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * With an eye toward multiplayer support - this is a container for player-specific data
 */
public interface Session_Shining
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
    
    @NonNull
    static Session_Shining create(@NonNull Game game, @NonNull Player player, @NonNull Camera camera)
    {
        return new SessionImpl_Shining(game, player, camera);
    }
}
